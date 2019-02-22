package com.inetBanking.testCases;

import java.io.IOException;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.inetBanking.pageObjectFactory.AddCustomerPage;
import com.inetBanking.pageObjectFactory.LoginPage;
import com.inetBanking.utilities.SeleniumMethods;

public class TC_003_AddCustomerTest extends BaseClass {
	
	LoginPage lp;  // Instantiating object of the LoginPage.
	AddCustomerPage ac;  // Instantiating object of the LoginPage.
	
	private SoftAssert softAssertion = new SoftAssert();
	
	// Instantiating Selenium Custom Methods with JS and Explicit waits 
	private SeleniumMethods sm = new SeleniumMethods();
	
	@Test
	public void addCustomerTest() throws IOException {
		
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
		
		// We want the page to login and wait for the button to be clickable
		sm.waitAndClickElement(ac.getNewCustomerBtn());
		log.info("driver clicks on the New Customer button");
		
		ac.getCustomerNameTxt().sendKeys("Pavan");
		log.info("Entering Customer's name");
		
		ac.getGenderRd().sendKeys("male");
		log.info("Entering Customer's gender");
		
		ac.sendKeysDOB("10", "25", "1985");
		log.info("Entering Customer's DOB");
	
		// Wait for the DOB element to save data
//		sm.WaitUntilWebElementIsVisible(ac.getDobTxt());
		
		ac.getAddressTxt().sendKeys("Market Street 123");
		log.info("Entering Customer's address");
			
		ac.getCityTxt().sendKeys("San Francisco");
		log.info("Entering Customer's city");
		
		ac.getStateTxt().sendKeys("CA");
		log.info("Entering Customer's state");
		
		ac.getPinTxt().sendKeys("123456");
		log.info("Entering Customer's PIN");
		
		ac.getMobileTxt().sendKeys("1234567");
		log.info("Entering Customer's mobile number");
		
		// Generating unique email ID
		String email = sm.getRandomAlphabeticString(5) + "@mail.com";			
		ac.getEmailidTxt().sendKeys(email);
		log.info("Entering Customer's email: " + email);
		
		ac.getPasswordTxt().sendKeys("passw123");
		log.info("Entering Customer's password");
		
		ac.getSubmitBtn().click();
		log.info("Clicking on Submit button");	
		
		// We want to wait until the confirmation header becomes visible
		sm.WaitUntilWebElementIsVisible(ac.getConfirmationHeader());
		
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
			
			softAssertion.assertEquals(ac.getConfirmationHeader().getText(), AddCustomerPage.Confirmation);
		}
		softAssertion.assertAll(); 
	}
}
