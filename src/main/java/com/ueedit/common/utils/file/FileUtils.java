package com.ueedit.common.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtils {

	/**
	 * 写文件
	 * 
	 * @author Frank
	 * @param fileName
	 * @param context
	 * @throws Exception
	 */
	public static void write(String fileName, String context) throws Exception {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		br.write(context);
		br.flush();
		br.close();
	}

	/**
	 * 读文件
	 * 
	 * @author Frank
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(String file) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();

		return buffer.toString();
	}

}
