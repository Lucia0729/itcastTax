package cn.itcast.core.util;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.nsfw.user.entity.User;


public class ExcelUtil {
	/**
	 * 导出用户的所有列表到excel
	 * @param userList 用户列表
	 * @param outputStream 输出流
	 */
	public static void exportUserExcel(List<User> userList, ServletOutputStream outputStream) {
		try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);//起始行号，结束行号，起始列号，结束列号
			
			//1.2、头标题样式
			HSSFCellStyle style1 = createCellStyle(workbook, (short)16);
			
			//1.3、列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short)13);
			
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("用户列表");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			//加载单元格样式
			cell1.setCellStyle(style1);
			cell1.setCellValue("用户列表");
			
			//3.2、创建列标题行；并且设置列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"用户名","帐号", "所属部门", "性别", "电子邮箱"};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
				//加载单元格样式
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			
			//4、操作单元格；将用户列表写入excel
			if(userList != null){
				for(int j = 0; j < userList.size(); j++){
					HSSFRow row = sheet.createRow(j+2);
					HSSFCell cell11 = row.createCell(0);
					cell11.setCellValue(userList.get(j).getName());
					HSSFCell cell12 = row.createCell(1);
					cell12.setCellValue(userList.get(j).getAccount());
					HSSFCell cell13 = row.createCell(2);
					cell13.setCellValue(userList.get(j).getDept());
					HSSFCell cell14 = row.createCell(3);
					cell14.setCellValue(userList.get(j).isGender()?"男":"女");
					HSSFCell cell15 = row.createCell(4);
					cell15.setCellValue(userList.get(j).getEmail());
				}
			}
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出excel
	 * @param clazz		获取导出实体类	
	 * @param sheetName	传入sheetName
	 * @param map		传入显示字段
	 * @param list	传入显示值
	 * @param outputStream	
	 */

//	public static void exportExcel(Class<?> clazz, String sheetName, HashMap<String, String> map, List<?> list, ServletOutputStream outputStream){
//		try {
//			HSSFWorkbook workbook = new HSSFWorkbook();
//			HSSFSheet sheet = workbook.createSheet(sheetName);
//			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, map.size()-1);
//			sheet.addMergedRegion(cellRangeAddress);
//			sheet.setDefaultColumnWidth(25);
//			HSSFCellStyle style1 = createCellStyle(workbook, (short) 20);
//			HSSFCellStyle style2 = createCellStyle(workbook, (short) 16);
//			HSSFRow row = sheet.createRow(0);
//			HSSFCell cell = row.createCell(0);
//			cell.setCellStyle(style1);
//			cell.setCellValue(sheetName);
//			Set<String> set = map.keySet();
//			int size = list.size();
//			Iterator<String> it = set.iterator();
//			int cellindex = 0;
//			HSSFRow row1 = sheet.createRow(1);
//			HSSFCell cell1 = null;
//			String value = null;
//			while (it.hasNext()) {
//				String key = it.next();
//				value = map.get(key);
//				cell1 = row1.createCell(cellindex);
//				cell1.setCellStyle(style2);
//				cell1.setCellValue(key);
//				if (list != null && size > 0) {
//					HSSFRow row2 = null;
//					String methodName = null;
//					Method method = null;
//					Object cellContent = null;
//					for (int j = 0; j < size; j++) {
//						row2 = sheet.createRow(j + 2);
//						HSSFCell cell2 = row2.createCell(cellindex);
//						if (("gender".equals(value)))
//							methodName = "is" + value.substring(0, 1).toUpperCase() + value.substring(1);
//						else
//							methodName = "get" + value.substring(0, 1).toUpperCase() + value.substring(1);
//						method = clazz.getMethod(methodName);
//						cellContent = method.invoke(list.get(j));
//						if (("gender".equals(value)))
//							cell2.setCellValue(cellContent.equals(true) ? "男" : "女");
//						else
//							cell2.setCellValue(String.valueOf(cellContent));
//					}
//				}
//				cellindex++;
//			}
//			workbook.write(outputStream);
//			workbook.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//	}
	
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short size) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(size);
		style.setFont(font);
		return style;
	}
}
