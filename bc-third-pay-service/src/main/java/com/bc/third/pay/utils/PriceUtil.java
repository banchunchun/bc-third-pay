package com.bc.third.pay.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 价格转换
 * @author banchun
 *
 */
public class PriceUtil {
	
	public static int _ratio = 100;
	
	/**
	 * 将元转换为分
	 * @param price
	 * @return
	 */
	public static double parseFen4Yuan(Integer price){
		if(price == null){
			return 0;
		}
		return parseDecimalFormat((double)price*_ratio);
	}
	

	
	/**
	 * 将元转换为分
	 * @param price
	 * @return
	 */
	public static double parseFen4Yuan(Double price){
		if(price == null){
			return 0;
		}
		return parseDecimalFormat(price*_ratio);
	}
	
	/**
	 * 将元转换为分
	 * @param price
	 * @return
	 */
	public static double parseFen4Yuan(String price){
		return parseDecimalFormat(parseDouble(price)*_ratio);
	}
	
	/**
	 * 将分转换为元
	 * @param price
	 * @return
	 */
	public static double parseYuan4Fen(Integer price){
		BigDecimal actPrice = Arith.div(new BigDecimal(price), new BigDecimal(100));
		actPrice.setScale(2);
		return actPrice.doubleValue();
	}

	public static double parseYuan4Fen(Long price){
		if(price == null){
			return 0;
		}
		BigDecimal actPrice = Arith.div(new BigDecimal(price), new BigDecimal(100));
		actPrice.setScale(2);
		return actPrice.doubleValue();
	}
	
	public static double parseFen2Yuan(Integer price){
		if(price == null){
			return 0;
		}
		return parseDecimalFormat(parseDouble(price)/_ratio);
	}
	
	/**
	 * 将分转换为元
	 * @param price
	 * @return
	 */
	public static double parseYuan4Fen(Double price){
		if(price == null){
			return 0;
		}
		return parseDecimalFormat(price/_ratio);
	}
	
	/**
	 * 将分转换为元
	 * @param price
	 * @return
	 */
	public static double parseYuan4Fen(String price){
		if(price == null){
			return 0;
		}
		return parseDecimalFormat(parseDouble(price)/_ratio);
	}
	
	/**
	 * 将分转元不够2位右边补零,注意返回值为字符串
	 * @author zhoushu   
	 * @date 2015年7月31日 下午8:30:27 
	 * @version V1.0 
	 * @Description: TODO
	 */
	public static String parseYuan4FenScale(String price){
		String formatPirce = "0.00";
		if(price != null && !"".equals(price) && !"null".equals(price)){
			DecimalFormat df = new DecimalFormat("0.00");
			formatPirce = df.format(parseDouble(price)/_ratio);
		}
		
		return formatPirce;
	}
	
	/**
	 * 分转成元，不保留小数
	 * @param fen
	 * @return
	 */
	public static String parseFen2YuanInt(Integer fen){
		String formatPirce = null;
		if(fen != null && !"null".equals(fen)){
			DecimalFormat df = new DecimalFormat("0");
			formatPirce = df.format(parseDouble(fen)/_ratio);
		}
		
		return formatPirce;
	}
	public static String parseFen2YuanInt(Long fen){
		String formatPirce = null;
		if(fen != null && !"null".equals(fen)){
			DecimalFormat df = new DecimalFormat("0");
			formatPirce = df.format(parseDouble(fen)/_ratio);
		}
		
		return formatPirce;
	}
	
	/**
	 * 分转成元，保留2位小数
	 * @param fen
	 * @return
	 */
	public static String parseFen2YuanDouble(Integer fen){
		String formatPirce = null;
		if(fen != null && !"null".equals(fen)){
			DecimalFormat df = new DecimalFormat("0.00");
			formatPirce = df.format(parseDouble(fen)/_ratio);
		}
		return formatPirce;
	}
	
	/**
	 * 分转成元，保留2位小数
	 * @param fen
	 * @return
	 */
	public static String parseFen2YuanDouble(Long fen){
		String formatPirce = null;
		if(fen != null && !"null".equals(fen)){
			DecimalFormat df = new DecimalFormat("0.00");
			formatPirce = df.format(parseDouble(fen)/_ratio);
		}
		
		return formatPirce;
	}
	
//	public static String parseYuan2Fen(double yuan){
//		double fen = parseBigDecimal(yuan*_ratio);
//		return String.valueOf(fen);
//	}
	
	/**
	 * 整型价格转换为浮点
	 * @param price
	 * @return
	 */
	public static double parseDouble(Integer price){
		return parseDecimalFormat((double) price);
	}
	
	/**
	 * 字符价格转换为浮点
	 * @param price
	 * @return
	 */
	public static double parseDouble(String price){
		return Double.parseDouble(price);
	}
	
	/**
	 * 浮点价格转换为浮点，常用于元
	 * @param price
	 * @return
	 */
	public static double parseDouble(double price){
		return parseDecimalFormat(price);
	}
	
	/**
	 * 浮点价格转换为浮点,0位小数，常用于分
	 * @param price
	 * @return
	 */
	public static double parseDouble0Point(Object price){
		DecimalFormat df=new DecimalFormat(".");// 精度处理
		return PriceUtil.parseDouble(df.format(price));
	}
	
	/**
	 * 转换为BigDecimal，精度2
	 * @param price
	 * @return
	 */
	private static double parseDecimalFormat(Double price){
		DecimalFormat df = new DecimalFormat("#.00");
		return parseDouble(df.format(price));
	}
	
	/**
	 * 价格转换为int类型
	 * @param price
	 * @return
	 */
	public static int parseInt(Object price){
		DecimalFormat df=new DecimalFormat("0");// 精度处理
		return Integer.parseInt(df.format(price));
	}
	/**
	 * 将Float类型的数据转换为两位精度的Float类型
	 * @author banchun	
	 * @date 2015年11月17日 上午10:12:29 
	 * @param price
	 * @return
	 */
	public static Float parseFloat(Float price){
		DecimalFormat df = new DecimalFormat(".##");
		return Float.parseFloat(df.format(price));
	}
	/**
	 * 转换BigDecimal 金额为两位小数点（默认四舍五入）字符串
	 * @author banchun	
	 * @date 2015年11月20日 上午11:09:03 
	 * @param price
	 * @return
	 */
	public static String parsePrice(BigDecimal price){
		if(price.floatValue() <=0)
			return price.toString();
		DecimalFormat df = new DecimalFormat(".##");
		String temp = df.format(price);
		return temp;
	}
	public static String parseFloatToString(Float price) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		return fnum.format(price);
	}
	
	public static String removePointZreo(double obj){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(obj);
	}
	
	public static void main(String[] args) {
		Double a = null;
		System.out.println(PriceUtil.parseInt(a));
		//double reducePrice = 200666.83814; // 此商品的优惠金额

		//System.out.print(parseYuan4Fen(3));

		//DecimalFormat df=new DecimalFormat(".");// 精度处理
		//reducePrice = PriceUtil.parseDouble0Point("125.2");
		//System.out.println(reducePrice);
//		tmpPrice = disPrice - reducePrice;
//		
//		DecimalFormat df1=new DecimalFormat(".");// 精度处理
//		tmpPrice = PriceUtil.parseDouble(df1.format(tmpPrice));
	}
}
