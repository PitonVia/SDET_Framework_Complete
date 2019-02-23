package com.inetBanking.pageObjectFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage {

	WebDriver driver;
	
	// This will be used to verify that a new customer has been created
	public static final String Confirmation = "Customer Registered Successfully!!!";
	
	// Path to the Customer Data Excel
	public static final String NewCustFilePath = "./src/test/java/com/inetBanking/testData/NewCustomerData.xlsx";
	
	// Creating a constructor so the WebDriver can be passed from other classes:
	public AddCustomerPage(WebDriver driver) {
		this.driver = driver;
		// For the POF need to initialize the WebElements using PageFactory Selenium class:
        PageFactory.initElements(driver, this);
	}
	
	// Creating locators for elements using how - another way to declare locators
	@FindBy(how = How.LINK_TEXT, using ="New Customer") 
	@CacheLookup // <-- loads element to cache to save time (used for stable non AJAX elements only)
	public WebElement newCustomerBtn; // can even call elements from other classes directly
	// However, the better practice is to call them using getters
	
	@FindBy(how = How.NAME, using="name") @CacheLookup WebElement customerNameTxt;		
	@FindBy(name="rad1") @CacheLookup WebElement genderRd;
	@FindBy(id="dob") @CacheLookup WebElement dobTxt;
	@FindBy(name="addr") @CacheLookup WebElement addressTxt;
	@FindBy(name="city") @CacheLookup WebElement cityTxt;
	@FindBy(name="state") @CacheLookup WebElement stateTxt;
	@FindBy(name="pinno") @CacheLookup WebElement pinTxt;
	@FindBy(name="telephoneno") @CacheLookup WebElement mobileTxt;
	@FindBy(name="emailid") @CacheLookup WebElement emailidTxt;
	@FindBy(name="password") @CacheLookup WebElement passwordTxt;
	@FindBy(name="sub") @CacheLookup WebElement submitBtn;
	@FindBy(css=".heading3") WebElement confirmationHeader;
	@FindBy(className="heading3") WebElement confirmationHeader2;
	@FindBy(linkText="Home") @CacheLookup WebElement homeBtn;
	
	// Special method for handling DOB, since data has to be entered in 3 parts
	public void sendKeysDOB(String mm, String dd, String yy) {
		dobTxt.sendKeys(mm);
		dobTxt.sendKeys(dd);
		dobTxt.sendKeys(yy);
	}
	
	// Auto-generated getters for WebElements
	public WebElement getNewCustomerBtn() {
		return newCustomerBtn;
	}
	public WebElement getCustomerNameTxt() {
		return customerNameTxt;
	}
	public WebElement getGenderRd() {
		return genderRd;
	}
	public WebElement getDobTxt() {
		return dobTxt; // we have a separate method for DOB
	}
	public WebElement getAddressTxt() {
		return addressTxt;
	}
	public WebElement getCityTxt() {
		return cityTxt;
	}
	public WebElement getStateTxt() {
		return stateTxt;
	}
	public WebElement getPinTxt() {
		return pinTxt;
	}
	public WebElement getMobileTxt() {
		return mobileTxt;
	}
	public WebElement getEmailidTxt() {
		return emailidTxt;
	}
	public WebElement getPasswordTxt() {
		return passwordTxt;
	}
	public WebElement getSubmitBtn() {
		return submitBtn;
	}
	public WebElement getConfirmationHeader() {
		return confirmationHeader;
	}
	public WebElement getConfirmationHeader2() {
		return confirmationHeader2;
	}
	public WebElement getHomeBtn() {
		return homeBtn;
	}
}
