package com.bc.third.pay.utils;

import java.math.BigDecimal;

public class Amount {

		private BigDecimal value;

	    /**
	     * 提供默认精度10
	     */
	    private static int scale = 10;

	    /**
	     * double类型构造函数
	     * 
	     * @param value
	     */
	    private Amount(double value) {
	        this.value = new BigDecimal(Double.toString(value));
	    }

	    /**
	     * String类型构造函数
	     * 
	     * @param value
	     */
	    private Amount(String value) {
	        this.value = new BigDecimal(value);
	    }

	    /**
	     * 取得BigDecimal的值
	     * 
	     * @return
	     */
	    public BigDecimal getValue() {
	        return this.value;
	    }

	    /**
	     * 两个double类型的数值相加
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double add(double v1, double v2) {
	        Amount a1 = new Amount(v1);
	        Amount a2 = new Amount(v2);
	        return add(a1, a2);
	    }

	    /**
	     * 两数相除
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double div(double v1, double v2) {
	        Amount a1 = new Amount(v1);
	        Amount a2 = new Amount(v2);
	        return divide(a1, a2);
	    }

	    /**
	     * 相减
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double sub(double v1, double v2) {
	        Amount a1 = new Amount(v1);
	        Amount a2 = new Amount(v2);
	        return subtract(a1, a2);
	    }

	    /**
	     * 相乘
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double mul(double v1, double v2) {
	        Amount a1 = new Amount(v1);
	        Amount a2 = new Amount(v2);
	        return multiply(a1, a2);
	    }
	    
	    /**
	     * 相乘
	     * @param v1 被乘数
	     * @param v2 乘数
	     * @return        
	     */
	    public static double mul(double v1,int v2){
	    	Amount a1 = new Amount(v1);
	    	Amount a2 = new Amount(Double.valueOf(v2));
	    	return multiply(a1, a2);
	    }

	    /**
	     * 两个Amount类型的数据进行相加
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double add(Amount v1, Amount v2) {
	        return v1.getValue().add(v2.getValue()).doubleValue();
	    }

	    /**
	     * 两个Amount类型变量相除
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double divide(Amount v1, Amount v2) {
	        if (scale < 0) {
	            throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
	        }
	        return v1.getValue().divide(v2.getValue(), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	    }

	    /**
	     * 两数相乘
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double multiply(Amount v1, Amount v2) {
	        return v1.getValue().multiply(v2.getValue()).doubleValue();
	    }

	    /**
	     * 两数相减
	     * 
	     * @param v1
	     * @param v2
	     * @return
	     */
	    public static double subtract(Amount v1, Amount v2) {
	        return v1.getValue().subtract(v2.getValue()).doubleValue();
	    }

	    /**
	     * 返回value的浮点数值
	     * 
	     * @return
	     */
	    public double doubleValue() {
	        return this.getValue().doubleValue();
	    }
	
	    /** 
	     * 使用java正则表达式去掉小数点后多余的0，如果小数点后全是0，则去掉小数点
	     * @param num  
	     * @return  
	     */ 
		public static String subZero(double num){
			String str = String.valueOf(num);
			if(str.indexOf(".") > 0){  
	            str = str.replaceAll("0+?$", "");//去掉多余的0  
	            str = str.replaceAll("[.]$", "");//如最后一位是.则去掉  
	        }  
	        return str;   
		}
		
	    /** 
	     * 使用java正则表达式去掉小数点后多余的0，如果小数点后全是0，则去掉小数点
	     * @param num  
	     * @return  
	     */ 
		public static String subZero(String num){
			if(num.indexOf(".") > 0){  
				num = num.replaceAll("0+?$", "");//去掉多余的0  
				num = num.replaceAll("[.]$", "");//如最后一位是.则去掉  
	        }  
	        return num;   
		}
}
