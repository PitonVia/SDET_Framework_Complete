package com.inetBanking.testCases;

import java.io.IOException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.inetBanking.pageObjectFactory.LoginPage;
import com.inetBanking.utilities.SeleniumMethods;

public class TC_001_LoginTest extends BaseClass {
	
	LoginPage lp; // Instantiating object of the LoginPage.
	
	private SoftAssert softAssertion = new SoftAssert();
	
	// Instantiating Selenium Custom Methods with JS and Explicit waits 
	private SeleniumMethods sm = new SeleniumMethods();
	

	@Test(priority=0)
	public void test_001_Login() throws IOException, InterruptedException {
			
		// Assigning static WebDriver to the instance of the LoginPage class inside the method!
		lp = new LoginPage(driver);
		
		// 'usrName' and'password' values are also coming from the BaseClass
		lp.setUsername(usrName);
		log.info("driver enters username");
		
		lp.setPassword(passWord);
		log.info("driver enters password");
		
		sm.jsClick(lp.getLoginBtn()); // JS click method
		log.info("driver clicks on the login button");
		
		System.out.println(driver.getTitle()); // print page title
				
		// Validating the page title
		if (driver.getTitle().equals(LoginPage.ExpectedPageTitle)) {
			softAssertion.assertTrue(true);
			log.info("The title of the LoginPage matched.");
			
		} else {
			softAssertion.assertEquals(driver.getTitle(), LoginPage.ExpectedPageTitle);
			log.error("The title of the LoginPage did NOT match.");
			
			// 2-nd parameter is capturing the name of this method 
			captureScreenshot(driver, Thread.currentThread().getStackTrace()[1].getMethodName());
			log.info("Capturing the screenshot for the Extent Report");
		}		
		
		// logout from the web site to allow next login test
		sm.jsClick(lp.getLogoutLink());
		log.info("Clicking on Log out link.");
		
		// Logout alert should appear
		sm.waitForAlert(); // want to make sure alert is present

		driver.switchTo().alert().accept(); // Dismissing the log out alert
		
		log.info("Completed test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		softAssertion.assertAll(); 
	}
	
	@Test(priority=0)
	public void test_002_SecondLogin() throws IOException {
			
		// Assigning static WebDriver to the instance of the LoginPage class inside the method!
		lp = new LoginPage(driver);
				
		// 'usrName' and'password' values are also coming from the BaseClass
		lp.setUsername(usrName);
		log.info("driver enters username");
		
		lp.setPassword(passWord);
		log.info("driver enters password");
		
		sm.jsClick(lp.getLoginBtn()); // JS click method
		log.info("driver clicks on the login button");
		
		System.out.println(driver.getTitle()); // print page title
				
		// Validating the page title
		if (driver.getTitle().equals(LoginPage.ExpectedPageTitle)) {
			softAssertion.assertTrue(true);
			log.info("The title of the LoginPage matched.");
			
		} else {
			softAssertion.assertEquals(driver.getTitle(), LoginPage.ExpectedPageTitle);
			log.error("The title of the LoginPage did NOT match.");
			
			// 2-nd parameter is capturing the name of this method 
			captureScreenshot(driver, Thread.currentThread().getStackTrace()[1].getMethodName());
			log.info("Capturing the screenshot for the Extent Report");
		}	
		log.info("Completed test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		softAssertion.assertAll(); 
	}
}
