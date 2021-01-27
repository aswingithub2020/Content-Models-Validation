package PageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.Utilities;

public class NavigateToManageModelsPage extends Utilities{
	WebDriver ldriver=null;
	JavascriptExecutor executor;
	
	
	
	public NavigateToManageModelsPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		executor  = (JavascriptExecutor) ldriver;
		logger=Logger.getLogger(NavigateToManageModelsPage.class);
	}
	
	
	@FindBy(xpath="//a[@title='Navigator']")
	WebElement Navigator_Link;
	
	@FindBy(xpath="//a[@title='Expand Risk Management']")
	WebElement Expand_RiskMgmt;
	
	@FindBy(xpath="//a[@title='Advanced Controls']")
	WebElement AdvControls_Via_Navigator;
	
	@FindBy(xpath="//div[@title='Models']/a")
	WebElement ModelsIcon;
	
	@FindBy(xpath="//div[@title='Models']/h1")
	WebElement ModelHeading;
	
	public void click_Navigator_Link()
	{
		Navigator_Link.click();
		
	}
	
	public void expand_Risk_Mgmt()
	{
		try {
			Expand_RiskMgmt.click();
			logger.info("Clicked on Expand Risk Management");
		  } catch (Exception e) {			
		     executor.executeScript("arguments[0].click();",Expand_RiskMgmt );
		     logger.info("Clicked on Expand Risk Management");		     
		  }
	}
	
	public void click_Adv_Controls()
	{
		AdvControls_Via_Navigator.click();
		logger.info("Click on Advanced Controls via Navigator");
	}
	
	public void click_ModelsIcon() throws InterruptedException
	{		
		ModelsIcon.click();
		waitUntilModelsIsOpened();
		logger.info("Clicked on Advanced Models Icon");
	}
	
	public void waitUntilModelsIsOpened()
	{
		wait.until(ExpectedConditions.visibilityOf(ModelHeading));
	}

}
