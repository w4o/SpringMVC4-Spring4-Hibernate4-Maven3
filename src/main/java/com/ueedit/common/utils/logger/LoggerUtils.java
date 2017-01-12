package com.ueedit.common.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	private static final Logger INFO = LoggerFactory.getLogger("info");
	private static final Logger ERROR = LoggerFactory.getLogger("error");
	private static final Logger DEBUG = LoggerFactory.getLogger("debug");
	private static final Logger WARN = LoggerFactory.getLogger("warn");

	/**
	 * 通用 info 日志
	 * 
	 * @author Frank
	 * @param message
	 */
	public static void info(String message) {
		INFO.info(message);
	}

	/**
	 * 通用 error 日志
	 * 
	 * @author Frank
	 * @param message
	 * @param e
	 */
	public static void error(String message, Throwable e) {
		ERROR.error(message, e);
	}

	/**
	 * 通用 debug 日志
	 * 
	 * @author Frank
	 * @param message
	 */
	public static void debug(String message) {
		DEBUG.debug(message);
	}

	/**
	 * 通用 warn 日志
	 * 
	 * @author Frank
	 * @param message
	 */
	public static void warn(String message) {
		WARN.warn(message);
	}

}
