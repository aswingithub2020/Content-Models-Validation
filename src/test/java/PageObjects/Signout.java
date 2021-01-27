package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Signout {
	
	WebDriver ldriver=null;
	
	public Signout(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		
	}
	
	@FindBy(xpath="//a[@title='Settings and Actions Menu']")
	WebElement SettingsActionsDropdown;
	
	@FindBy(xpath="//a[text()='Sign Out']")
	WebElement SignOutLink;
	
	public void Click_SettingsDropdown()
	{
		SettingsActionsDropdown.click();
	}
	
	public void Click_SignoutLink()
	{
		SignOutLink.click();
	}
	
	

}
