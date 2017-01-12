package com.ueedit.common.utils.net;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;

import com.ueedit.common.utils.logger.LoggerUtils;

public class HttpUtils {

	/**
	 * post 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url) {
		return post(url, "");
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String post(String url, String data) {
		return httpPost(url, data);
	}

	/**
	 * 发送http post请求
	 * 
	 * @param url url
	 * @param instream post数据的字节流
	 * @return
	 */
	public static String post(String url, InputStream instream) {
		try {
			HttpEntity entity = Request.Post(url).bodyStream(instream, ContentType.create("text/html", Consts.UTF_8))
					.execute().returnResponse().getEntity();
			return entity != null ? EntityUtils.toString(entity) : null;
		} catch (Exception e) {
			LoggerUtils.error("post请求异常，postUrl:" + url, e);
		}
		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		return httpGet(url);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	private static String httpPost(String url, String data) {
		try {
			HttpEntity entity = Request.Post(url).bodyString(data, ContentType.create("text/html", Consts.UTF_8))
					.execute().returnResponse().getEntity();
			return entity != null ? EntityUtils.toString(entity, Consts.UTF_8) : null;
		} catch (Exception e) {
			LoggerUtils.error("post请求异常，postUrl:" + url, e);
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param url URL
	 * @param file 需要上传的文件
	 * @return
	 */
	public static String postFile(String url, File file) {
		return postFile(url, null, file);
	}

	/**
	 * 上传文件
	 * 
	 * @param url URL
	 * @param name 文件的post参数名称
	 * @param file 上传的文件
	 * @return
	 */
	public static String postFile(String url, String name, File file) {
		try {
			HttpEntity reqEntity = MultipartEntityBuilder.create().addBinaryBody(name, file).build();
			Request request = Request.Post(url);
			request.body(reqEntity);
			HttpEntity resEntity = request.execute().returnResponse().getEntity();
			return resEntity != null ? EntityUtils.toString(resEntity) : null;
		} catch (Exception e) {
			LoggerUtils.error("post请求异常，postUrl:" + url, e);
		}
		return null;
	}

	/**
	 * 下载文件
	 * 
	 * @param url URL
	 * @return 文件的二进制流，客户端使用outputStream输出为文件
	 */
	public static byte[] getFile(String url) {
		try {
			Request request = Request.Get(url);
			HttpEntity resEntity = request.execute().returnResponse().getEntity();
			return EntityUtils.toByteArray(resEntity);
		} catch (Exception e) {
			LoggerUtils.error("post请求异常，postUrl:" + url, e);
		}
		return null;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 */
	private static String httpGet(String url) {
		try {
			HttpEntity entity = Request.Get(url).execute().returnResponse().getEntity();
			return entity != null ? EntityUtils.toString(entity, Consts.UTF_8) : null;
		} catch (Exception e) {
			LoggerUtils.error("post请求异常，gettUrl:" + url, e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * map 转 url参数字符串
	 * 
	 * @author Frank
	 * @param map
	 * @return
	 */
	public static String mapToUrlParam(Map<String, Object> map) {
		StringBuffer postDataBuffer = new StringBuffer();
		for (Entry<String, Object> e : map.entrySet()) {
			postDataBuffer.append(e.getKey());
			postDataBuffer.append("=");
			postDataBuffer.append(e.getValue());
			postDataBuffer.append("&");
		}
		postDataBuffer.substring(0, postDataBuffer.length() - 1);

		return postDataBuffer.toString();
	}

}
