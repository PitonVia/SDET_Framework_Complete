package com.inetBanking.pageObjectFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;
	
	// Creating a constructor so the WebDriver can be passed from other classes:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		// For the POF need to initialize the WebElements using PageFactory Selenium class:
        PageFactory.initElements(driver, this);
	}
	
	// String for the title of the page to verify login should be part of this class so it can easily be updated! 
	// Remember that all static fields have to be accessed in a static way, i.e., by specifying complete class name.
	// For example: LoginPage.ExpectedPageTitle.
	public static final String ExpectedPageTitle = "Guru99 Bank Manager HomePage";
	
	// Creating locators for elements of the login page
	// Note how @FindBy annotation is used compared to By objects in POM
	
	@FindBy(name="uid") 
	@CacheLookup  // <-- loads element to cache to save time (used for stable non AJAX elements only)
	WebElement username;		
	
	@FindBy(name="password") 
	@CacheLookup 
	WebElement password;
	
	@FindBy(name="btnLogin") 
	@CacheLookup 
	WebElement loginBtn;
	
	@FindBy(linkText="Log out") 
	WebElement logoutLink;
	
	// Simple action methods for the above elements
	public void setUsername(String user) {
		username.sendKeys(user);
	}
	public void setPassword(String passw) {
		password.sendKeys(passw);
	}
	public void clickLoginBtn() {
		loginBtn.click();
	}
	public void clickLogoutLink() {
		logoutLink.click();
	}

	// Getters for fields and WebElements 
	public WebElement getUsername() {
		return username;
	}
	public WebElement getPassword() {
		return password;
	}
	public WebElement getLoginBtn() {
		return loginBtn;
	}
	public WebElement getLogoutLink() {
		return logoutLink;
	}
}
