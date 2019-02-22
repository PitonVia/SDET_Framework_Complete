package com.inetBanking.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// The methods are useful for working with TenstNG @DataProvider multidimensional array.
// Our xlsx spreadsheet will contain two columns only for username and password.

// Note that all fields and methods are static --> we won't be creating an object of the class, 
// instead, will be calling the class name to access the static methods . 

public class XLUtils {

	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	
	public static int getRowCount(String xlFile, String xlSheet) throws IOException {
		
		fis = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(xlSheet);
		int rowCount = ws.getLastRowNum();
		wb.close();
		fis.close();
		return rowCount;
	}
	
	public static int getCellCount(String xlFile, String xlSheet, int rowNum) throws IOException {
		
		fis = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		wb.close();
		fis.close();
		return cellCount;
	}
	
	public static String getCellData(String xlFile, String xlSheet, int rowNum, int colNum) throws IOException {
		
		fis = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.getCell(colNum);
		String value;
		try {
			DataFormatter formatter = new DataFormatter();
			String cellValue = formatter.formatCellValue(cell);
			return cellValue;
		} catch (Exception e) {
			value="";
			System.out.println(e.getMessage());
		}
		wb.close();
		fis.close();
		return value;
	}
	
	public static void setCellData(String xlFile, String xlSheet, int rowNum, int colNum, String cellValue) throws IOException {
		
		fis = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.createCell(colNum);
		cell.setCellValue(cellValue);
		fos = new FileOutputStream(xlFile);
		wb.write(fos);
		wb.close();
		fis.close();
		fos.close();
	}
}
