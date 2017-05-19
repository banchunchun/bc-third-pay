/**
 * 
 */
package com.bc.third.pay.utils;



import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;


import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel 大文件导出
 */
public class LargeExcelUtil {
	

	
	/**
	 * 时间格式默认为yyyy-MM-dd
	 * 
	 * @param outs
	 * @param headers
	 * @param columnName
	 * @param sheetName
	 * @param collections
	 */
	public static void writeExcel(OutputStream outs, String[] headers,
			String[] columnName, String sheetName,
			List<Map<String, Object>> collections) {
		writeExcel(outs, headers, columnName, sheetName, collections, "");
	}

	/**
	 * 导出可以合并单元格的excel
	 * @param outs
	 * @param headers
	 * @param columnName
	 * @param sheetName
	 * @param collections
	 * @param isMerge
     * @param mergeCount
     */
	public static SXSSFWorkbook writeExcel(SXSSFWorkbook wb,OutputStream outs, String[] headers,
								  String[] columnName, String sheetName,
								  List<Map<String, Object>> collections,boolean isMerge,int mergeCount) {
		return writeExcel(wb,outs, headers, columnName, sheetName, collections, "yyyy-MM-dd HH:mm:ss",isMerge,mergeCount);
	}



	/**
	 * 直接写入excel，单个文件单个sheet
	 * @param outputStream 输出流
	 * @param sheetName 表名
	 * @param excelHeader 表头
	 * @param columnName 数据字段
	 * @param list 数据源
	 * @param pattern 日期格式
	 * @param isMerge 是否需要合并相同单元格
     * @param mergeCount 合并的起始位置 比如合并前3列的相同单元格，则mergeCount为2，如果需要合并全表的同行数的相同单元格，则为headers.length()-1
     */
	public static synchronized void writeExcel(OutputStream outputStream,String sheetName,String excelHeader[] ,String [] columnName,List<Map<String,Object>> list,String pattern,boolean isMerge,Integer mergeCount){
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);
		try{
			Font font = wb.createFont();
			font.setFontName("微软雅黑");
			font.setCharSet(Font.DEFAULT_CHARSET);
			font.setFontHeightInPoints((short) 12);
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直
			Sheet sheet = wb.createSheet(sheetName);
			//循环写入表头
			Row firstrow = sheet.createRow(0);
			for (int j = 0; j < excelHeader.length; j++) {
				Cell cell = firstrow.createCell(j);
				cell.setCellValue(new XSSFRichTextString(excelHeader[j]));
				cell.setCellStyle(cellStyle);
				cellStyle.setWrapText(true);
				sheet.autoSizeColumn((short)j);
			}
			//循环写入内容，从第二行开始，第一行被表头所占用
			for(int k= 0 ;k < list.size() ;k ++) {
				Row row = sheet.createRow(k+1);
				Map<String, Object> tempMap = list.get(k);
				for (int m = 0;m < excelHeader.length; m++){
					Object object = tempMap.get(excelHeader[m]);
					Cell cell = row.createCell((short)m);
					XSSFRichTextString content = new XSSFRichTextString(transObj(object, pattern));
					cell.setCellValue(content);
					cell.setCellStyle(cellStyle);
					sheet.autoSizeColumn((short)m);
				}
			}
			//下面开始合并逻辑
			if (isMerge){ //需要合并
				int totalRow = sheet.getPhysicalNumberOfRows();  //获取该sheet表物理行数
				for (int m = 0; m < mergeCount ; m++){ //循环需要合并的列
					mergeCells(m,sheet,totalRow,cellStyle);
				}
			}
			//写入文件
			wb.write(outputStream);

		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			wb.dispose();
		}

	}

	/**
	 * 写入excel，是否合并相同单元格
	 * @param outputStream 输出流
	 * @param excelHeader 表头数组
	 * @param columnName 表头数组
	 * @param sheetName sheet表名称
	 * @param list 输出数据
	 * @param pattern 格式
	 * @param isMerge 是否需要合并
     * @param mergeCount 合并的起始位置 比如合并前3列的相同单元格，则mergeCount为2，如果需要合并全表的同行数的相同单元格，则为headers.length()-1
     */
	public static synchronized SXSSFWorkbook writeExcel(SXSSFWorkbook wb,OutputStream outputStream,String excelHeader[] ,String [] columnName,String sheetName,List<Map<String,Object>> list,String pattern,boolean isMerge,Integer mergeCount){

		try{
//			Font font = wb.createFont();
//			font.setFontName("微软雅黑");
//			font.setCharSet(Font.DEFAULT_CHARSET);
//			font.setFontHeightInPoints((short) 12);
//			CellStyle cellStyle = wb.createCellStyle();
//			cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
//			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直
			Sheet sheet = wb.createSheet(sheetName);
			//循环写入表头
			Row firstrow = sheet.createRow(0);
			for (int j = 0; j < excelHeader.length; j++) {
				Cell cell = firstrow.createCell(j);
				cell.setCellValue(new XSSFRichTextString(excelHeader[j]));
//				cell.setCellStyle(cellStyle);
//				cellStyle.setWrapText(true);
//				sheet.autoSizeColumn((short)j);
			}
			//循环写入内容，从第二行开始，第一行被表头所占用
			for(int k= 0 ;k < list.size() ;k ++) {
				Row row = sheet.createRow(k+1);
				Map<String, Object> tempMap = list.get(k);
				for (int m = 0;m < excelHeader.length; m++){
					Object object = tempMap.get(excelHeader[m]);
					Cell cell = row.createCell((short)m);
					XSSFRichTextString content = new XSSFRichTextString(transObj(object, pattern));
					cell.setCellValue(content);
//					cell.setCellStyle(cellStyle);
//					sheet.autoSizeColumn((short)m);
				}
			}
			((SXSSFSheet) sheet).flushRows(1000);
			//下面开始合并逻辑
			if (isMerge){ //需要合并
				int totalRow = sheet.getPhysicalNumberOfRows();  //获取该sheet表物理行数
				for (int m = 0; m < mergeCount ; m++){ //循环需要合并的列
					mergeCells(m,sheet,totalRow,null);
				}
			}


		}catch (Exception ex){
			ex.printStackTrace();
		}

		return wb;

	}

	/**
	 * 合并单元格
	 * @param m 单元格所在列数
	 * @param sheet sheet表
	 * @param totalRow 总行数
	 * @param cellStyle 样式
     */
	public static void mergeCells(int m,Sheet sheet,int totalRow,CellStyle cellStyle){

		int currentRow = 1; //起始行数
		for (int p = 1; p < totalRow; p++) {//totalRow 总行数
		//1、首先合并第一行，这时候才能确定第一行第一列的值，否则合并的会把不同行数的单元格合并掉
			if (m ==0){
				Cell currentCell = sheet.getRow(p).getCell(m);
				String current = getStringCellValue(currentCell); //当前单元格的值
				if ("".equals(current)){
					continue;
				}
				Cell nextCell = null; //下一个单元格
				String next = ""; //下一个单元格值
				if(p < totalRow + 1){
					Row nowRow = sheet.getRow(p+1);
					if(nowRow != null){
						nextCell = nowRow.getCell(m);
						next = getStringCellValue(nextCell);
					}
				}
				if(current.equals(next)){//比对是否相同
					currentCell.setCellValue(current);
					continue;
				}
				else{
					sheet.addMergedRegion(new CellRangeAddress(currentRow, p, m, m));//合并单元格
					Cell nowCell = sheet.getRow(currentRow).getCell(m);
					nowCell.setCellValue(current);
//					nowCell.setCellStyle(cellStyle);
					currentRow = p + 1;
				}
			}else { //合并列的相同单元格，以第一行的值比对，如果当前行和下一行的第一个值不想等，则不需要合并
				Cell currentFirstCell = sheet.getRow(p).getCell(0);
				Cell currentCell = sheet.getRow(p).getCell(m);
				String current = getStringCellValue(currentCell);
				String currentFirst = getStringCellValue(currentFirstCell);
				if ("".equals(currentFirst)){
					continue;
				}
				Cell nextCell = null;
				Cell nextFirstCell = null;
				String next = "";
				String nextFirst = "";
				if(p < totalRow + 1){
					Row nowRow = sheet.getRow(p+1);
					if(nowRow != null){
						nextCell = nowRow.getCell(m);
						next = getStringCellValue(nextCell);
						nextFirstCell = nowRow.getCell(0);
						nextFirst = getStringCellValue(nextFirstCell);
					}
				}
				if(current.equals(next) && nextFirst.equals(currentFirst) ){//比对是否相同
					currentCell.setCellValue(current);
					continue;
				}
				else{
					sheet.addMergedRegion(new CellRangeAddress(currentRow, p, m, m));//合并单元格
					Cell nowCell = sheet.getRow(currentRow).getCell(m);
					nowCell.setCellValue(current);
//					nowCell.setCellStyle(cellStyle);
					currentRow = p + 1 ;
				}
			}
		}
	}

	/**
	 * 根据输入的数据生成一个XSSFWorkbook
	 * @param sheetName：sheet名称
	 * @param propertyHeaderMap：<property, header>（<T中的property名称、有getter就行, 对应显示在Excel sheet中的列标题>）
	 * 								用LinkedHashMap保证读取的顺序和put的顺序一样
	 * @param dataSet：实体类集合
	 * @return：XSSFWorkbook
	 */
	public static <T> XSSFWorkbook generateXlsxWorkbook(String sheetName, LinkedHashMap<String, String> propertyHeaderMap, Collection<T> dataSet) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((int) 15);

		XSSFCellStyle headerStyle = getHeaderStyle(workbook);
		XSSFCellStyle contentStyle = getContentStyle(workbook);

		// 生成表格标题行
		XSSFRow row = sheet.createRow(0);
		int i = 0;
		for(String key : propertyHeaderMap.keySet()){
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			XSSFRichTextString text = new XSSFRichTextString(propertyHeaderMap.get(key));
			cell.setCellValue(text);
			i++;
		}

		//循环dataSet，每一条对应一行
		int index = 0;
		for(T data : dataSet){
			index ++;
			row = sheet.createRow(index);

			int j = 0;
			for(String property : propertyHeaderMap.keySet()){
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(contentStyle);

				//拼装getter方法名
				String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);

				try {
					//利用反射机制获取dataSet中的属性值，填进cell中
					Class<? extends Object> tCls = data.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(data, new Object[] {}); //调用getter从data中获取数据

					// 判断值的类型后进行类型转换
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						textValue = sdf.format(date);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					XSSFRichTextString richString = new XSSFRichTextString(textValue);
					cell.setCellValue(richString);

				} catch (Exception e) {
					e.printStackTrace();
				}
				j++;
			}
		}

		return workbook;

	}

	/**
	 * 生成一个标题style
	 * @return style
	 */
	public static XSSFCellStyle getHeaderStyle(Workbook workbook){
		return getHeaderStyle(workbook, java.awt.Color.BLUE, IndexedColors.WHITE.getIndex());
	}

	/**
	 * 生成一个指定颜色的标题style
	 * @param workbook
	 * @param foregroundColor
	 * @param fontColor
	 * @return
	 */
	public static XSSFCellStyle getHeaderStyle(Workbook workbook, java.awt.Color foregroundColor, short fontColor){

		// 生成一个样式（用于标题）
		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(new XSSFColor(foregroundColor));
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setColor(fontColor);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		return style;
	}

	/**
	 * 生成一个用于内容的style
	 * @param workbook
	 * @return
	 */
	public static XSSFCellStyle getContentStyle(Workbook workbook){
		// 生成并设置另一个样式（用于内容）
		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		//style.setFillForegroundColor(new XSSFColor(Color.YELLOW));
		//style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font);

		return style;
	}
	/**
	 * 获取某个单元格的值
	 * @param cell
	 * @return
     */
	public static String getStringCellValue(Cell cell) {
		String strCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					strCell = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					strCell = String.valueOf(cell.getNumericCellValue());
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					strCell = String.valueOf(cell.getBooleanCellValue());
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					strCell = "";
					break;
				default:
					strCell = "";
					break;
			}
			if (strCell.equals("") || strCell == null) {
				return "";
			}
			if (cell == null) {
				return "";
			}
		}
		return strCell;
	}
	/**
	 * 
	 * @param outs
	 * @param headers
	 *            写入时 表头列名
	 * @param columnName
	 *            collections中hashmap中对应的key，顺序应与headers中列名顺序保持一致
	 * @param sheetName
	 *            sheetName名
	 * @param collections
	 *            要写入的数据集合
	 * @param pattern
	 *            日期类型中format 默认为yyyy-MM-dd
	 */
	public static synchronized void writeExcel(OutputStream outs,
			String[] headers, String[] columnName, String sheetName,
			List<Map<String, Object>> collections, String pattern) {
		SXSSFWorkbook wb = new SXSSFWorkbook(10000); // keep 100 rows in memory, exceeding rows will be flushed to disk
		try {
			Sheet sheet = wb.createSheet(sheetName);
			Row firstrow = sheet.createRow(0);
			for (int j = 0; j < headers.length; j++) {
				Cell cell = firstrow.createCell(j);
                cell.setCellValue(new XSSFRichTextString(headers[j]));
			}
			for (int i = 0; i < collections.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Map<String, Object> map = collections.get(i);
				for (int j = 0; j < columnName.length; j++) {
					Cell cell = row.createCell(j);
					Object object = map.get(columnName[j]);
					cell.setCellValue(transObj(object, pattern));
				}
			}
			wb.write(outs);
		} catch (IOException e) {
//			logger.error("writeExcel error", e);
		} finally {

			if (outs != null) {
				try {
					outs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        wb.dispose();

		}
	}

	public static synchronized void writeExcel(OutputStream outs,
			String[] headers, String[] columnName, String sheetName,
			List<Map<String, Object>> collections,String[] headers2, String[] columnName2, String sheetName2,
			List<Map<String, Object>> collections2, String pattern) {
		SXSSFWorkbook wb = new SXSSFWorkbook(10000); // keep 100 rows in memory, exceeding rows will be flushed to disk
		try {
			Sheet sheet = wb.createSheet(sheetName);
			Row firstrow = sheet.createRow(0);
			for (int j = 0; j < headers.length; j++) {
				Cell cell = firstrow.createCell(j);
				cell.setCellValue(new XSSFRichTextString(headers[j]));
			}
			for (int i = 0; i < collections.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Map<String, Object> map = collections.get(i);
				for (int j = 0; j < columnName.length; j++) {
					Cell cell = row.createCell(j);
					Object object = map.get(columnName[j]);
					cell.setCellValue(transObj(object, pattern));
				}
			}

			Sheet sheet2 = wb.createSheet(sheetName2);
			Row firstrow2 = sheet2.createRow(0);
			for (int j = 0; j < headers2.length; j++) {
				Cell cell = firstrow2.createCell(j);
				cell.setCellValue(new XSSFRichTextString(headers2[j]));
			}
			for (int i = 0; i < collections2.size(); i++) {
				Row row = sheet2.createRow(i + 1);
				Map<String, Object> map = collections2.get(i);
				for (int j = 0; j < columnName2.length; j++) {
					Cell cell = row.createCell(j);
					Object object = map.get(columnName2[j]);
					cell.setCellValue(transObj(object, pattern));
				}
			}
			wb.write(outs);
		} catch (IOException e) {
//			logger.error("writeExcel error", e);
		} finally {

			if (outs != null) {
				try {
					outs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// dispose of temporary files backing this workbook on disk
			wb.dispose();

		}
//		logger.info("write excel finish.");
	}

	/**
	 * 转换单元格的值
	 * @param obj
	 * @param pattern
     * @return
     */
	public static String transObj(Object obj, String pattern) {
		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd";
		}

		if (obj == null) {
			return "";
		}
		String textValue = "";
		if (obj instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			textValue = sdf.format(obj);
		} else if (obj instanceof Boolean) {
			if ((Boolean) obj == false) {
				textValue = "0";
			} else {
				textValue = "1";
			}
		}else {
			textValue = obj.toString();
		}
		return textValue;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * @param sheet    要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow   结束行
	 * @param firstCol 开始列
	 * @param endCol   结束列
	 * @return 设置好的sheet.
	 */
	public static XSSFSheet setXSSFValidation(XSSFSheet sheet, String[] textlist, int firstRow, int endRow,
			int firstCol, int endCol) {
		DataValidationHelper dvHelper = sheet.getDataValidationHelper();
		DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
		validation.setSuppressDropDownArrow(true);
		validation.setShowErrorBox(true);
		validation.setSuppressDropDownArrow(true);
		sheet.addValidationData(validation);
		return sheet;
	}

}
