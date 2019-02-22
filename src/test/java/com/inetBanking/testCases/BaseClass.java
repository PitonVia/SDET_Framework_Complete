package com.inetBanking.testCases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetBanking.utilities.ReadConfig;


public class BaseClass {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static JavascriptExecutor jse;
	
	// log4J 2 field is protected so that it can be used by all subclasses.
	protected final Logger log = LogManager.getLogger(getClass());
	
	// Creating an instance of the ReadConfig class to work with the properties file. 
	ReadConfig readConfig = new ReadConfig();
	
	public String baseURL = readConfig.getPropertyValue("baseURL");
	public String usrName = readConfig.getPropertyValue("userName");
	public String passWord = readConfig.getPropertyValue("passWord");
	
	@Parameters("browser") // browser will be specified in testng.xml
	@BeforeClass
	public void setup(String browser) {
		
		switch(browser) {
		
		case "chrome":			
			ChromeOptions options = new ChromeOptions();
	        options.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
	        options.addArguments("--always-authorize-plugins=true");
	        System.setProperty("webdriver.chrome.driver", readConfig.getPropertyValue("chromePath"));
	        driver = new ChromeDriver(options);
	        break;
	        
		case "firefox":
			System.setProperty("webdriver.gecko.driver", readConfig.getPropertyValue("firefoxPath"));
			FirefoxOptions fOptions = new FirefoxOptions();
			fOptions.setCapability("marionette", true);
			driver = new FirefoxDriver(fOptions);
			break;
			
		case "ie":
			System.setProperty("webdriver.ie.driver", readConfig.getPropertyValue("iePath"));
			InternetExplorerOptions iOptions = new InternetExplorerOptions();
//			iOptions.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver(iOptions);
		}
        
		// Casting jse to use JS and instantiating Explicit waits!!
		jse = ((JavascriptExecutor) driver);
        wait = new WebDriverWait(driver, 15);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		log.info("BaseClass: setup() completed for " +  browser + " browser");
		
		driver.get(baseURL);
		log.info("driver navigates to the base URL ");
	}
	
	@AfterClass
	public void tearDown() {
		
		driver.quit();
		log.info("BaseClass: driver quits");
		log.trace("********** END OF TEST **********");
		
	}
	
	// Takes screenshots when methods fail - applies to all tests
	protected void captureScreenshot(WebDriver driver, String testName) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File("./Screenshots/" + testName + ".png");
		
		FileUtils.copyFile(source, target);
		log.error("Screenshot was captured for " + testName);			
	}
}
