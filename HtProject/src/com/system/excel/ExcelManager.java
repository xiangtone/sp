package com.system.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sun.istack.internal.logging.Logger;
import com.system.model.SettleAccountModel;

public class ExcelManager
{
	Logger log = Logger.getLogger(ExcelManager.class);
	
	private Map<String, HSSFCellStyle> createStyles (HSSFWorkbook workbook)
	{
		Map<String, HSSFCellStyle> map = new HashMap<String, HSSFCellStyle>();
		
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		map.put("BASE_STYLE", style);
		
		HSSFCellStyle styleFormat = workbook.createCellStyle();
		styleFormat.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleFormat.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleFormat.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleFormat.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleFormat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		styleFormat.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleFormat.setDataFormat(workbook.createDataFormat().getFormat("0.00")); 
		
		map.put("FORMAT_STYLE", styleFormat);
		
		return map;
	}
	
	public void writeSettleAccountToExcel(String dateType, String date,
			String channelName, List<SettleAccountModel> list, String demoPath,OutputStream os)
	{
		InputStream is = null;
		
		try
		{
			is = new FileInputStream(demoPath);
			HSSFWorkbook book =  new HSSFWorkbook(is);
			HSSFSheet sheet = book.getSheetAt(0);
			
			int tempSpTroneNameLength = 0;
			int maxSpTroneNameLength = 0;
			
			Map<String, HSSFCellStyle> mapStyle = createStyles(book);
			
			SettleAccountModel model = null;
			
			for(int i=0; i<list.size(); i++)
			{
				model = list.get(i);
				
				HSSFRow row = sheet.createRow(i+3);
				row.createCell(1).setCellStyle(mapStyle.get("BASE_STYLE"));
				row.createCell(2).setCellStyle(mapStyle.get("BASE_STYLE"));
				row.createCell(3).setCellStyle(mapStyle.get("BASE_STYLE"));
				
				HSSFCell cell = row.createCell(4);
				cell.setCellStyle(mapStyle.get("BASE_STYLE"));
				cell.setCellValue(model.getOperatorName());
				
				cell = row.createCell(5);
				cell.setCellStyle(mapStyle.get("BASE_STYLE"));
				cell.setCellValue(model.getSpTroneName());
				
				cell = row.createCell(6);
				cell.setCellStyle(mapStyle.get("FORMAT_STYLE"));
				cell.setCellValue(model.getAmount());
				
				cell = row.createCell(7);
				cell.setCellStyle(mapStyle.get("FORMAT_STYLE"));
				cell.setCellValue(model.getJiesuanlv());
				
				cell = row.createCell(8);
				cell.setCellStyle(mapStyle.get("FORMAT_STYLE"));
				cell.setCellFormula("G"+ (4+i) +"*H"+ (4+i));
				
				tempSpTroneNameLength = model.getSpTroneName().getBytes("GBK").length;
				
				if(tempSpTroneNameLength > maxSpTroneNameLength)
					maxSpTroneNameLength = tempSpTroneNameLength;
			}
			
			//结算方式宽度
			sheet.setColumnWidth(1, 9*256);
			//结算期间宽度
			sheet.setColumnWidth(2, 18*256);
			//渠道名称宽度
			sheet.setColumnWidth(3, (channelName.getBytes("GBK").length+1)*256);
			//运营商宽度
			sheet.setColumnWidth(4, 9*256);
			//产品名称宽度
			sheet.setColumnWidth(5, (maxSpTroneNameLength+1)*256);
			//信息费宽度
			sheet.setColumnWidth(6, 13*256);
			//结算价宽度
			sheet.setColumnWidth(7, 12*256);
			//渠道酬金宽度
			sheet.setColumnWidth(8, 13*256);
			
			sheet.addMergedRegion(new CellRangeAddress(3,2+list.size(),1,1));
			sheet.getRow(3).getCell(1).setCellValue(dateType);
			sheet.getRow(3).getCell(1).setCellStyle(mapStyle.get("BASE_STYLE"));
			
			sheet.addMergedRegion(new CellRangeAddress(3,2+list.size(),2,2));
			sheet.getRow(3).getCell(2).setCellValue(date);
			sheet.getRow(3).getCell(2).setCellStyle(mapStyle.get("BASE_STYLE"));
			
			sheet.addMergedRegion(new CellRangeAddress(3,2+list.size(),3,3));
			sheet.getRow(3).getCell(3).setCellValue(channelName);
			sheet.getRow(3).getCell(3).setCellStyle(mapStyle.get("BASE_STYLE"));
			
			HSSFRow row = sheet.createRow(3+list.size());
			for(int i=0; i<8; i++)
				row.createCell(i+1).setCellStyle(mapStyle.get("BASE_STYLE"));
			
			sheet.addMergedRegion(new CellRangeAddress(3+list.size(),3+list.size(),1,7));
			row.getCell(1).setCellValue("合计");
			row.getCell(8).setCellFormula("SUM(I" + 3 + ":I" + (list.size() + 3) + ")");
			row.getCell(8).setCellStyle(mapStyle.get("FORMAT_STYLE"));
			
			sheet.setForceFormulaRecalculation(true);
			
			book.write(os);
			
			log.info("Export Excel " + channelName + "," + date + " finish" );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{ if(is!=null)is.close(); }catch(Exception ex){}
		}
	}
}
