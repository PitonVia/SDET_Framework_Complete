package com.inetBanking.testCases;

import java.io.IOException;
import java.util.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.inetBanking.pageObjectFactory.AddCustomerPage;
import com.inetBanking.pageObjectFactory.LoginPage;
import com.inetBanking.utilities.SeleniumMethods;
import com.inetBanking.utilities.XLUtils_NewCustomerData;

public class TC_004_AddCustomerDDT extends BaseClass {
	
	LoginPage lp;  // Instantiating object of the LoginPage.
	AddCustomerPage ac;  // Instantiating object of the AddCustomer Page.
	
	private SoftAssert softAssertion = new SoftAssert();
	
	// Instantiating Selenium Custom Methods with JS and Explicit waits 
	private SeleniumMethods sm = new SeleniumMethods();
	
	@Test
	public void addCustomerTest() throws IOException, InterruptedException {
		
		// Assigning static WebDriver to the instance of the LoginPage class
		lp = new LoginPage(driver);
		
		// 'usrName' and'password' values are coming from the BaseClass
		lp.setUsername(usrName);
		log.info("driver enters username");
		
		lp.setPassword(passWord);
		log.info("driver enters password");
		
		sm.jsClick(lp.getLoginBtn()); // JS click method
		log.info("driver clicks on the login button");
		
		// Checking if login was successful
		if (driver.getTitle().equals(LoginPage.ExpectedPageTitle)) {
			log.info("Login was successful.");
		} else {
			log.error("Login failed!");
		}
		
		// Assigning static WebDriver to the instance of the LoginPage class
		ac = new AddCustomerPage(driver);	
		
		// FROM HERE we start working with the XLUtils_NewCustomerData class and create a loop
		
		// Populates the ArrayList with customer data
		XLUtils_NewCustomerData.populateListWithCustData();
		
		// Creating a new Array list from the static list of Customer data in XLUtils_NewCustomerData calss:
		List<String> custData = new ArrayList<>(XLUtils_NewCustomerData.cellValues);
		
		// Creating an iterator to access the values of the ArrayList
		Iterator<String> custIter = custData.iterator();
	
		while (custIter.hasNext()) {  // looping through the List
			
//			System.out.println(custIter.next());
			
			// We want the page to login and wait for the button to be clickable
			sm.waitAndClickElement(ac.getNewCustomerBtn());
			log.info("driver clicks on the New Customer button");
				
			ac.getCustomerNameTxt().sendKeys(custIter.next());
			log.info("Entering Customer's name");
			
			ac.getGenderRd().sendKeys(custIter.next());
			log.info("Entering Customer's gender");
			
			ac.sendKeysDOB(custIter.next(), custIter.next(), custIter.next());
			log.info("Entering Customer's DOB");
		
			// Wait for the DOB element to save data
//			sm.WaitUntilWebElementIsVisible(ac.getDobTxt());
			
			ac.getAddressTxt().sendKeys(custIter.next());
			log.info("Entering Customer's address");
				
			ac.getCityTxt().sendKeys(custIter.next());
			log.info("Entering Customer's city");
			
			ac.getStateTxt().sendKeys(custIter.next());
			log.info("Entering Customer's state");
			
			ac.getPinTxt().sendKeys(custIter.next());
			log.info("Entering Customer's PIN");
			
			ac.getMobileTxt().sendKeys(custIter.next());
			log.info("Entering Customer's mobile number");
			
			// Generating unique email ID
			String email = sm.getRandomAlphabeticString(5) + "@mail.com";			
			ac.getEmailidTxt().sendKeys(email);
			log.info("Entering Customer's email: " + email);
			
			ac.getPasswordTxt().sendKeys(custIter.next());
			log.info("Entering Customer's password");
			
			ac.getSubmitBtn().click();
			log.info("Clicking on Submit button");	
			
			// We want to wait until the confirmation header becomes visible
			sm.WaitUntilWebElementIsVisible(ac.getConfirmationHeader2());
			
			// Check if after submitting customer details, the page contains  
			boolean response = driver.getPageSource().contains(AddCustomerPage.Confirmation);
			
			if (response) {
				log.info("Customer Registered Successfully");
				softAssertion.assertTrue(true);
				log.info("Completed test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + " - PASSED");
			} else {
				log.error("Customer was NOT registered.");
				
				captureScreenshot(driver, Thread.currentThread().getStackTrace()[1].getMethodName());
				log.info("Capturing the screenshot for the Extent Report");
				log.info("Completed test: " + Thread.currentThread().getStackTrace()[1].getMethodName() + " - FAILED");
				
				softAssertion.assertEquals(ac.getConfirmationHeader2().getText(), AddCustomerPage.Confirmation);
			}
		
			// Clicking on the Home button to return to the main screen
			ac.getHomeBtn().click();
			log.info("driver clicks on the Home button");
			
			// Switching to the Add Customer page again to avoid stale element reference
			ac = new AddCustomerPage(driver); 
			
			// Waiting for the NEw Customer button to become available 
			sm.WaitUntilWebElementIsVisible(ac.getNewCustomerBtn());
			
			// Clicking on the new Customer button to allow for the next cycle
			ac.getNewCustomerBtn().click();
			log.info("driver clicks on the New Customer button");				
		}
		softAssertion.assertAll(); 
	}
}
