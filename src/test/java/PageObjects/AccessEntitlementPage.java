package PageObjects;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class AccessEntitlementPage extends Utilities {
	
	WebDriver ldriver=null;
	
	public  AccessEntitlementPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="//div[@class='af_panelHeader_title-text-cell']/h1") //AE_heading
	WebElement AccessEntitlement_Heading;
	
	@FindBy(xpath="//table[@summary='Access Points']/tbody/tr") //Find number of AP's
	WebElement AccessPoints_count;	
	
	@FindBy(xpath="//a[@title='Go To Page 1']")
	WebElement NavigateToPage1;
	
	@FindBy(xpath="//a[@title='Go To Page 2']")
	WebElement NavigateToPage2;
	
	@FindBy(xpath="//div[@class='af_table_navbar']/table/tbody/tr/td/table/tbody/tr/td[4]")
	WebElement PaginationDetails;
	
	@FindBy(xpath="//a[@title='Done']")
	WebElement Done_Link;
	
	public void click_DoneLink()
	{
		Done_Link.click();
		
	}
	
	public boolean verify_AE_heading(String EntitlementName)
	{
		String RO_AccessEntitlement_Heading=AccessEntitlement_Heading.getText();
		String arr[]=null;
		if(RO_AccessEntitlement_Heading.endsWith(")"))
		{
			arr=RO_AccessEntitlement_Heading.split("\\(");						
			RO_AccessEntitlement_Heading=arr[0];
		}
		else
		{
			
		}		
		
		System.out.println("EntitlementName "+EntitlementName);
		System.out.println("RO_AccessEntitlement_Heading "+RO_AccessEntitlement_Heading);
		if(RO_AccessEntitlement_Heading.equalsIgnoreCase(EntitlementName))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public int Accesspoints_Count()
	{		
		int AP_Count=ldriver.findElements(By.xpath("//table[@summary='Access Points']/tbody/tr")).size();
		return AP_Count;
	}
	
	public String get_AccessPointName(int rownumber)
	{		
		String AP_Name=ldriver.findElement(By.xpath("//table[@summary='Access Points']/tbody/tr["+rownumber+"]")).getText();		
		return AP_Name;
	}
	
	public void click_Page2Link_Pagination()
	{
		NavigateToPage2.click();
	}
	
	public void click_Page1Link_Pagination()
	{
		NavigateToPage1.click();
	}
	
	public boolean isDisplayed_Page2Link_Pagination()
	{
		if(NavigateToPage2.isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isDisplayed_Page1Link_Pagination()
	{
		if(NavigateToPage1.isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isDisplayed_PaginationDetails()
	{
	
		try
		{
			PaginationDetails.isDisplayed();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		/*
		if(!PaginationDetails.isDisplayed())
		{
			return true;
		}		
		else
		{
			return false;
		}
		*/
		
		
		
	}
	
	public int AccessPointsNumber_In_Pagination()
	{
		int NumberofAccessPoints_Pagination=0;		
		String PaginationDetails1=PaginationDetails.getText();		
		String arr[]=PaginationDetails1.split(" ");
		NumberofAccessPoints_Pagination=Integer.parseInt(arr[2]);		
		
		return NumberofAccessPoints_Pagination;
	}
	
	

}
