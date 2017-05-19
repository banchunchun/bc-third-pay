package com.bc.third.pay.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常错误日志工具
 * Created by banchun on 2016/5/17.
 */
public class StackTraceLogUtil {

	//获取具体的堆栈信息
	public static String getStackTraceAsString(Exception e) {
		try {
			//StringWriter将包含堆栈信息
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			//获取堆栈信息
			e.printStackTrace(printWriter);
			//转换成String，并返回该String
			StringBuffer error = stringWriter.getBuffer();
			return error.toString();
		} catch (Exception e2) {
			return "获取堆栈信息异常";
		}
	}
}
