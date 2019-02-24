package com.inetBanking.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.inetBanking.pageObjectFactory.AddCustomerPage;

// The methods will be reading cell values from rows of NewCustomerData.xlsx into a List.

// Note that all fields and methods are static --> we won't be creating an object of the class, 
// instead, will be calling the class name to access the static methods. 

public class XLUtils_NewCustomerData {

	// Declaring an array list to be populated with the customer data
	public static List<String> cellValues = new ArrayList<>();
	
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	// Reads from the Excel and populates the list with String values from each row
	public static void populateListWithCustData() {

		File file = new File(AddCustomerPage.NewCustFilePath);
		
		try (FileInputStream fis = new FileInputStream(file)) {

			// Create Workbook instance holding reference to .xlsx file
			wb = new XSSFWorkbook(fis);
			// Get first sheet from the workbook
			sheet = wb.getSheetAt(0);
			
			Iterator<Row> rowIter = sheet.iterator(); // Iterate through each rows one by one
			
			Row row = rowIter.next(); // Skip the top row with table header!

			while (rowIter.hasNext()) {
				
				row = rowIter.next(); // Proceed to the 2-nd Excel row

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
//				System.out.println(); // Separating each row for visibility for testing

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next(); // Proceed to the 1-st Excel cell
					
					cell.setCellType(CellType.STRING); // format each cell as a String

//					System.out.print(cell.getStringCellValue() + " "); // Printing value for testing
					
					cellValues.add(cell.getStringCellValue());
				}
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
//		System.out.println("\n"); 
/*		for (String cellValue : cellValues) {  // Printing all the list values for testing
			System.out.print(cellValue + ",");
		}*/
	}
}
