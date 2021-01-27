package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.Utilities;

public class AccessModelPage extends Utilities {
	WebDriver ldriver=null;
	JavascriptExecutor executor;
	
	public AccessModelPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		executor  = (JavascriptExecutor) ldriver;
	}

	
	@FindBy(xpath="//div[@class='af_panelHeader_title-text-cell']/h1")
	WebElement verfiy_HeadingName_Modelpage;
	
	@FindBy(xpath="(//span[@class='af_inputText_content'])[1]")
	WebElement verify_ModelName_Modelpage;
	
	@FindBy(xpath="(//span[text()='More Details'])[1]")
	WebElement click_MoreDetails_Btn;
	
	@FindBy(xpath="(//*[@dominant-baseline])[1]")  //Entitlement 1 heading
	WebElement Entitlement1_Heading;
	
	@FindBy(xpath="(//*[@dominant-baseline])[7]")  //Entitlement 2 heading
	WebElement Entitlement2_Heading;
	
	//@FindBy(xpath="(//*[@dominant-baseline])[2]")  //AccessPoint link_En1
	@FindBy(xpath="(((((((//*[name()='svg'])[10]/*[name()='g']/*[name()='g']/*[name()='g'])[1]/*[name()='g'])[1]/*[name()='g'])[2]/*[name()='g'])[1]/*[name()='g']/*[name()='g'])[1]/*[name()='text'])[4]")
	WebElement AccessPoint_Entitlement1;
	
	
	@FindBy(xpath="(((((((//*[name()='svg'])[10]/*[name()='g']/*[name()='g']/*[name()='g'])[1]/*[name()='g'])[1]/*[name()='g'])[2]/*[name()='g'])[2]/*[name()='g']/*[name()='g'])[1]/*[name()='text'])[4]")  //AccessPoint link_En2
	WebElement AccessPoint_Entitlement2;
	
	@FindBy(xpath="(//*[@dominant-baseline])[6]")  //AccessPoint name En1
	WebElement Name_AccessEntitlement1;
	
	@FindBy(xpath="(//*[@dominant-baseline])[12]")  //AccessPoint name En2
	WebElement Name_AccessEntitlement2;
	
	@FindBy(xpath="//a[@title='Done']")
	WebElement Done_Link;
	
	public void click_AccessPointLink(int countvalue)
	{
		//executor.executeScript("arguments[0].click();",AccessPoint_Entitlement1 );
		if(countvalue==0)
		{
			AccessPoint_Entitlement1.click();
		}
		else
		{
			AccessPoint_Entitlement2.click();
		}
				
	}
	
	public void click_DoneLink() throws InterruptedException
	{
		Thread.sleep(2000);	
		boolean staleelement=true;
		while(staleelement)
		{
			try {
				Done_Link.click();
				staleelement=false;
			}
			catch(StaleElementReferenceException e)
			{
				staleelement=true;
			}
		}
		
		//executor.executeScript("arguments[0].click();",Done_Link );
	}
	
	public boolean DoneLink_isDisplayed()
	{
		if(Done_Link.isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void waitUntil_DoneBtn_isDisplayed()
	{
		wait.until(ExpectedConditions.visibilityOf(Done_Link));
	}
	
	public void waitUntil_MoreDetailsBtn_isDisplayed()
	{
		wait.until(ExpectedConditions.visibilityOf(click_MoreDetails_Btn));
	}	
	
	public boolean verfiy_Heading_ModelPage(String Modelname)
	{
		String RO_Modelheading=verfiy_HeadingName_Modelpage.getText();
		if (RO_Modelheading.equalsIgnoreCase(Modelname)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean verify_ModelName_ModelPage(String Modelname)
	{
		String RO_ModelName=verify_ModelName_Modelpage.getText();

		if (RO_ModelName.equalsIgnoreCase(Modelname)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void click_moredetails_button()
	{
		click_MoreDetails_Btn.click();
	}
	
	public boolean verfiy_Entitlement_Heading(String EntitlementName,int countvalue)
	{
		String RO_Entitlement=null;	
		boolean true_flag=true;
		boolean false_flag=false;
		
		if(countvalue==0)
		{
			RO_Entitlement=Entitlement1_Heading.getText();
		}
		else
		{
			RO_Entitlement=Entitlement2_Heading.getText();
		}
		
		if(RO_Entitlement.equalsIgnoreCase(EntitlementName))
		{			
			return true_flag;
		}
		else
		{			
			return false_flag;
		}
				
	}
	
	public String verify_ActualEntitlements(String EntitlementName)
	{
		String RO_Entitlement1=Name_AccessEntitlement1.getText();
		String RO_Entitlement2=Name_AccessEntitlement2.getText();
		
		if(RO_Entitlement1.equalsIgnoreCase(EntitlementName))
		{
			return "Entitlment1";
		}
		else if(RO_Entitlement2.equalsIgnoreCase(EntitlementName))
		{
			return "Entitlement2";
		}
		else
		{
			return "Entitlement not matched";
		}
	}
	
	
}
