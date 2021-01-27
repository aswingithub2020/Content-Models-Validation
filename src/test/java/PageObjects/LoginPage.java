package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class LoginPage extends Utilities{
	WebDriver ldriver=null;
	
	public LoginPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(ldriver, this);
	}
	
	
	@FindBy(id="userid")
	WebElement txtUsername;
	
	@FindBy(id="password")
	WebElement txtPassword;
	
	@FindBy(name="btnActive")
	WebElement btn_SignIN;
	
	public void enterusername(String Username)
	{
		txtUsername.sendKeys(Username);
	}
	
	public void enterpassword(String Password)
	{
		txtPassword.sendKeys(Password);
	}
	
	public void clicksigninbtn()
	{
		btn_SignIN.click();
	}
	

}
