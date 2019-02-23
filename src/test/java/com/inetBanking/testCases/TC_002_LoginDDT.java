package com.inetBanking.testCases;

import java.io.IOException;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.inetBanking.pageObjectFactory.LoginPage;
import com.inetBanking.utilities.SeleniumMethods;
import com.inetBanking.utilities.XLUtils_LoginData;

public class TC_002_LoginDDT extends BaseClass {

	// Will be not be using SoftAsserts for parameterized validation. 
	// Will use hard Assets instead. 
	
	// Instantiating Selenium Custom Methods with JS and Explicit waits 
	private SeleniumMethods sm = new SeleniumMethods();

	@Test(dataProvider = "LoginData") // need to specify the dataProvider method's name
	public void loginDDT(String username, String password) throws InterruptedException {

		LoginPage lp = new LoginPage(driver);

		// 'usrName' and'password' values are also coming from the @DataProvider
		lp.setUsername(username);
		log.info("driver enters username from Excel");

		lp.setPassword(password);
		log.info("driver enters password from Excel");

		// Calling wait and click method with JS 
		sm.waitAndclickElementUsingJS(lp.getLoginBtn()); 
		log.info("driver clicks on the login button");

		sm.waitForAlert(); // want to wait for an alert
		
		// If login fails, an alert message will pop up
		if (isAlertPresent() == true) {

			log.error("Login failed --> alert was displayed: " + driver.switchTo().alert().getText());
			
			// Dismissing the alert by clicking on OK button
			driver.switchTo().alert().accept();
			log.info("Dismissing the alert.");

			driver.switchTo().defaultContent();
			log.trace("*** Parameterized test completed: FAILED ***");
			
			Assert.assertTrue(false, driver.switchTo().alert().getText());	
			
		} else {
			
			log.info("Login was successful.");
			Assert.assertTrue(true);
			
			sm.jsClick(lp.getLogoutLink());
			log.info("Clicking on Log out link.");
			
			// Logout alert should appear
			sm.waitForAlert(); // want to make sure alert is present
			
			log.info("Checking the log out alert text: " + driver.switchTo().alert().getText());
			
			driver.switchTo().alert().accept(); // Dismissing the log out alert
			
			driver.switchTo().defaultContent();
			log.trace("*** Parameterized test completed: PASSED ***");			
		}	
	}

	// Checks if Alert is displayed (happens when login is unsuccessful)
	private boolean isAlertPresent() {

		try {
			driver.switchTo().alert();
			return true;
			
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	// This method returns @DataProvider array with username and password data from
	// Excel
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {

		String xlFilepath = "./src/test/java/com/inetBanking/testData/LoginData.xlsx";

		// Identifying the row and column for the 2 dimensional array
		int rowCount = XLUtils_LoginData.getRowCount(xlFilepath, "Sheet1");
		int columnCount = XLUtils_LoginData.getCellCount(xlFilepath, "Sheet1", 1);

		System.out.println("rowCount is: " + rowCount + ", columnCount is: " + columnCount);

		// Passing the row and column numbers to the array
		String[][] loginData = new String[rowCount][columnCount];

		// Rows and columns in excel start from 0. Since we have a table header that
		// uses the 1-st row, we have to start the rows from 1.
		for (int i = 1; i <= rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				// Load the cell values from Excel into the array:
				// [i-1] since i = 1 for row in Excel, then 1-1=0 corresponds to a row in the
				// array
				// [j] since 0 for column number in Excel corresponds to 0 in the array
				loginData[i-1][j] = XLUtils_LoginData.getCellData(xlFilepath, "Sheet1", i, j);
			}
		}
		return loginData;
	}

}
