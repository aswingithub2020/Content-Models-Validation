package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class ManageAccessModelPage extends Utilities {
	WebDriver ldriver = null;
	

	public ManageAccessModelPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	@FindBy(xpath = "//input[@class='af_inputText_content']")
	WebElement txtSearchBox;
	
	@FindBy(xpath="//button[@class='af_commandButton']")
	WebElement Search_btn;
	
	
	public void enterModelNameIntoSearchBox(String ModelName)
	{	
		boolean staleelement=true;
		while(staleelement)
		{
			try {
			txtSearchBox.clear();
			txtSearchBox.sendKeys(ModelName);
			staleelement=false;
			}
			catch(StaleElementReferenceException e)
			{
			staleelement=true;
			}
			
		}
		
	}
	
	public void click_SearchBtn()
	{
		Search_btn.click();
	}
	
	public boolean ModelTobeDisplayed(String ModelName)
	{
		if(driver.findElement(By.xpath("//a[text()='"+ModelName+"']")).isDisplayed())
		{			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void click_ModelToOpen(String ModelName)
	{
		driver.findElement(By.xpath("//a[text()='"+ModelName+"']")).click();		
	}

}
