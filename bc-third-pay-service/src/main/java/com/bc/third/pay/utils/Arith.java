package com.bc.third.pay.utils;

import java.math.BigDecimal;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 * @author pangpeijie
 */
public class Arith {

	// 默认除法运算精度(10位小数)
	public static final int DEF_DIV_SCALE = 10;
	// 2位小数精度
	public static final int NORMAL_SCALE = 2;
	// 整数精度
	public static final int INT_SCALE = 0;
	
	private Arith() {
	}
	

	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		return v1.add(v2);
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2);
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		return v1.multiply(v2);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位四舍五入处理。   
     * @param v   需要四舍五入的数字   
     * @param scale   小数点后保留几位   
     * @return 四舍五入后的结果   
	 */
	public static BigDecimal round(BigDecimal v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal one = new BigDecimal("1");
		return v.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 向上取整
     * @param v   需要取整的数字   
     * @return 取证后的结果   
	 */
	public static BigDecimal roundHalfUp(BigDecimal v) {
		if (v.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("The bigDecimal must be a positive integer or zero");
		}
		BigDecimal one = new BigDecimal("1");
		return v.divide(one, INT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	public static void main(String[] args) {
		BigDecimal d1 = new BigDecimal(3);
		BigDecimal d2 = new BigDecimal(11);
		BigDecimal result = Arith.mul(Arith.div(d1, d2), d2);
		System.out.println(result);
		System.out.println(Arith.roundHalfUp(result));
	}
	
}
