package Utilities;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import PageObjects.AccessEntitlementPage;
import PageObjects.AccessModelPage;
import PageObjects.LoginPage;
import PageObjects.ManageAccessModelPage;
import PageObjects.NavigateToManageModelsPage;
import PageObjects.Signout;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


public class Utilities {
	public static WebDriver driver;
	String ModelName;
	String AE1_Name;
	String AE2_Name;
	//String Xl_Path="./TestData\\TestData.xlsx";
	public Logger logger;
	public static WebDriverWait wait;
	public ExtentHtmlReporter htmlreporter;
	public ExtentTest test;
	public ExtentReports extent;
	public Actions actions;
	public JavascriptExecutor executor ;	
	public XLUtility xlutils=new XLUtility();
	public String ModelName_XL=null;
	public String EntitlementName1_XL=null;
	public String EntitlementName2_XL=null;
	HashSet<String> AP_XL=new HashSet<String>();
	
	/*************Environment URL's********************/
	//public String URL="https://fuscdrmsmc336-fa-ext.us.oracle.com/fscmUI/faces/FuseWelcome?";
	//public String URL="https://fuscdrmsmc454-fa-ext.us.oracle.com/fscmUI/faces/FuseWelcome?";
	//public String URL="https://fusovmsmc104-fa-ext.us.oracle.com/homePage/faces/AtkHomePageWelcome";
	public String URL="https://fuscdrmsmc205-fa-ext.us.oracle.com/fscmUI/faces/FuseWelcome?";
	
	/**************************************************/
	
	/**************Test Data Path***********************/
	//public String TestData_Path="C:\\Users\\asuvanam.ORADEV\\eclipse-workspace 2\\ContentValidations\\ContentModelsvalidation\\TestData\\Excels\\TestData - HCM.xlsx";
	//public String TestData_Path="C:\\Users\\asuvanam.ORADEV\\eclipse-workspace 2\\ContentValidations\\ContentModelsvalidation\\TestData\\Excels\\TestData - SCM.xlsx";
	//public String TestData_Path="C:\\Users\\asuvanam.ORADEV\\eclipse-workspace 2\\ContentValidations\\ContentModelsvalidation\\TestData\\Excels\\TestData - Common Setup.xlsx";
	public String TestData_Path="C:\\Users\\asuvanam.ORADEV\\eclipse-workspace 2\\ContentValidations\\ContentModelsvalidation\\TestData\\Excels\\TestData - ERP.xlsx";
	
	/****************************************************/
	@BeforeTest
	public void setup() throws IOException, AWTException
	{
		htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\test-output\\myoutput.html");
		htmlreporter.config().setDocumentTitle("Automation report");
		htmlreporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		extent.setSystemInfo("Envionment", "QA");
		extent.setSystemInfo("Tester", "Aswin");
		extent.setSystemInfo("OS", "WIN 10");
		extent.setSystemInfo("Browser", "Chrome");	
		logger=Logger.getLogger(Utilities.class);
		System.out.println(System.getProperty("user.dir")+"\\Config Files\\Log4j.Properties");
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\Config Files\\Log4j.Properties");		
		
		System.setProperty("webdriver.chrome.driver", "./Drivers\\chromedriver_86version.exe");			
		
		driver=new ChromeDriver();		
		driver.manage().window().maximize();
		executor=(JavascriptExecutor)driver;
		
		
		actions=new Actions(driver);		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait=new WebDriverWait(driver,30);
		
	}
	
	@AfterMethod
	public void aftermethod(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " is FAILED", ExtentColor.RED));			
			test.fail(result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " is PASSED", ExtentColor.GREEN));
						
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " is Skipped", ExtentColor.YELLOW));
		}
	}	
	
	@AfterTest
	public void tearDown()
	{
		//driver.quit();
		extent.flush();
	}
	
	public void Login()
	{
		LoginPage lp=new LoginPage(driver);
		driver.get(URL);
		
		logger.info("Login Started");
		
		lp.enterusername("AACADMIN");
		lp.enterpassword("Welcome1");
		lp.clicksigninbtn();
		
		logger.info("Login Successful");
	}
	
	public void NavigateToAdvancedModels() throws InterruptedException
	{
		NavigateToManageModelsPage nav=new NavigateToManageModelsPage(driver);
		nav.click_Navigator_Link();
		Thread.sleep(1000);
		nav.expand_Risk_Mgmt();
		nav.click_Adv_Controls();
		nav.click_ModelsIcon();
	}
	
	public void Verify_Heading_ModelName(String ModelName,String ScreenshotsPath) throws InterruptedException, IOException, AWTException
	{
		AccessModelPage verify_AM=new AccessModelPage(driver);		
		verify_AM.waitUntil_MoreDetailsBtn_isDisplayed();
		verify_AM.click_moredetails_button();
		Thread.sleep(1000);
		String Model_withoutColon=ModelName.replace(":", "");
		
				
		if(verify_AM.verfiy_Heading_ModelPage(ModelName))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@dominant-baseline])[6]")));
			takescreenshot_AShot(driver,Model_withoutColon+"_VerificationDetails", ScreenshotsPath);
			logger.info("Run Time Model Heading Name :"+ModelName+" and Test Model Heading Name are Matched");
			Assert.assertTrue(true);
		}
		else
		{
			logger.info("Run Time Model Heading Name :"+ModelName+" and Test Model Heading Name are not Matched");
			Assert.assertTrue(false);
		}		
		
	}	
	
	public String create_DirectoryFolder()
	{
		String timeStamp=new SimpleDateFormat("dd.MM.YYYY_HH.mm").format(new java.util.Date());		
		String DirectoryPath=null;		
		String ModelResultsScreenshots_Folder="C:\\Users\\asuvanam.ORADEV\\eclipse-workspace 2\\ContentValidations\\ContentModelsvalidation\\Results\\ModelResults_Screenshots_"+timeStamp;
		
		File file=new File(ModelResultsScreenshots_Folder);
		if(!file.exists())
		{
			
			if(file.mkdir())
			{
				System.out.println("Directory created");
			}
			else
			{
				System.out.println("Directory not created");
			}	

		}
		
		DirectoryPath=file.getPath();
		return DirectoryPath;		
		
	}
	
	public String create_ModelsFolder(String Modelname,String Dir_Path)
	{
		String Modelname_removeColon=Modelname.replace(":", "");
		String FolderPath=null;
		
		String Subfolder=Dir_Path+File.separator+Modelname_removeColon;
		System.out.println(Subfolder);
		File file1=new File(Subfolder);
		
		
		if(!file1.exists())
		{
			if(file1.mkdir())
			{
				FolderPath=file1.getPath();
				logger.info("Model Folder Got Created "+FolderPath);
				
			}
			else
			{
				logger.info("Model Folder not Created");
			}
		}
		return FolderPath;
	}
	
	public String create_ScreenshotsFolder(String Modelname)
	{
		String timeStamp=new SimpleDateFormat("dd.MM.YYYY_HH.mm").format(new java.util.Date());		
		String FolderPath=null;	
		String Modelname_removeColon=Modelname.replace(":", "");
		
		/********************************************************************/
		String ModelResultsScreenshots_Folder="C:\\Users\\asuvanam.ORADEV\\eclipse-workspace 2\\ContentValidations\\ContentModelsvalidation\\Results\\ModelResults_Screenshots_"+timeStamp;
		File file=new File(ModelResultsScreenshots_Folder);
		if(!file.exists())
		{
			
			if(file.mkdir())
			{
				System.out.println("Directory created");
			}
			else
			{
				System.out.println("Directory not created");
			}	

		}
			
		String Subfolder=ModelResultsScreenshots_Folder+File.separator+Modelname_removeColon;
		System.out.println(Subfolder);
		File file1=new File(Subfolder);
		
		
		if(!file1.exists())
		{
			if(file1.mkdir())
			{
				FolderPath=file1.getPath();
				logger.info("Model Folder Got Created "+FolderPath);
				
			}
			else
			{
				logger.info("Model Folder not Created");
			}
		}
		return FolderPath;
	}
	
	
	public void Verify_Modelname(String ModelName,int row) throws IOException
	{
		AccessModelPage verify_AM=new AccessModelPage(driver);
		
		if(verify_AM.verify_ModelName_ModelPage(ModelName))
		{
			logger.info("Run Time Model Name :"+ModelName+" and Test Model Name are Matched");			
			//xlutils.insertCellData(TestData_Path, "Modeldetails", row, 5, "PASSED");
		}
		else
		{
			logger.info("Run Time Model Name :"+ModelName+" and Test Model Name are not Matched");
			//xlutils.insertCellData(TestData_Path, "Modeldetails", row, 5, "FAILED");
		}
		
	}
	
	public void VerifyEntitlement_ModelPage(String EntitlementName,int countvalue,int row) throws InterruptedException, IOException
	{
		AccessModelPage ModelPg=new AccessModelPage(driver);		
		
		if(ModelPg.verfiy_Entitlement_Heading(EntitlementName,countvalue))
		{
			logger.info(EntitlementName+" entitlement heading name is matched");
			switch(countvalue)
			{
				case 0:
					xlutils.insertCellData(TestData_Path, "Modeldetails", row, 6, "PASSED");
					break;
				case 1:
					xlutils.insertCellData(TestData_Path, "Modeldetails", row, 7, "PASSED");
					break;
			}
			
			click_AccessPointLink(countvalue);	
			Thread.sleep(1000);
		}
		else
		{
			logger.info(EntitlementName+" entitlement heading name is not matched");
			
			switch(countvalue)
			{
				case 0:
					xlutils.insertCellData(TestData_Path, "Modeldetails", row, 6, "FAILED");
					break;
				case 1:
					xlutils.insertCellData(TestData_Path, "Modeldetails", row, 7, "FAILED");
					break;
			}
			Thread.sleep(1000);
			//ModelPg.click_DoneLink();
		}
		
	}
	
	public void VerifyAccessPoints_AEPage(String EntitlementName,String ScreenshotsPath,int row,int countvalue) throws IOException, InterruptedException, AWTException
	{
		AccessEntitlementPage AP_Verify=new AccessEntitlementPage(driver);		
		String AccessPoint=null;	
		int counter=0;
		int pass_flag=0;
		int fail_flag=0;
		int pass_counter0=0;
		int fail_counter0=0;
		int pass_counter1=0;
		int fail_counter1=0;
		int pass_AP_count=0;
		int fail_AP_count=0;
		int AP_Count_XL=getNumberOfAccessPoints(getEntitlementColumnNumber(EntitlementName));
		
		
		if(AP_Verify.verify_AE_heading(EntitlementName))
		{		
			AP_XL.removeAll(AP_XL);
			AP_XL.clear();
			AccessPoints_XL_Collection1(EntitlementName,AP_Count_XL);
			logger.info(EntitlementName+" Access Entitlement and Heading Name are both matched");
			logger.info("Number of Access Points are :"+AP_Verify.Accesspoints_Count());
			
					
			if(AP_Verify.isDisplayed_PaginationDetails())
			{				
				counter=2;				
			}
			else
			{
				counter=1;
			}
		
			
			
			for(int j=1;j<=counter;j++)
			{	
				
				takescreenshot_Robot(driver,EntitlementName+"-"+j, ScreenshotsPath);
				System.out.println("AP_Verify.Accesspoints_Count() "+AP_Verify.Accesspoints_Count());
				
				for(int i=1;i<=AP_Verify.Accesspoints_Count();i++)
				{
					logger.info("Access Point Name is "+AP_Verify.get_AccessPointName(i));					
					AccessPoint=AP_Verify.get_AccessPointName(i).trim();					
					
					if(AP_XL.contains(AccessPoint))
					{
						pass_flag=pass_flag+1;
						if(counter==1)
						{
							pass_counter0=pass_flag;
						}
						else if(counter==2)
						{
							pass_counter1=pass_flag;
						}
						
						logger.info(AccessPoint+" Access Point matches with testdata");					
					}
					else
					{
						fail_flag=fail_flag+1;
						if(counter==1)
						{
							fail_counter0=fail_flag;
						}
						else if(counter==2)
						{
							fail_counter1=fail_flag;
						}
						System.out.println("fail_flag "+fail_flag);
						logger.info("*******************************************************");
						logger.info(AccessPoint+" Access Point doesnot matches with testdata");
						logger.info("*******************************************************");					
						
					}											
				
				}
				
				if(counter==2)
				{
					try {
					AP_Verify.click_Page2Link_Pagination();
					logger.info("***********************************************************");
					logger.info("*****************Clicked on Page 2 link********************");
					logger.info("***********************************************************");
					Thread.sleep(1000);
					}
					catch(Exception e)
					{
						
					}					
				}
				if(j==2)
				{
					try {
					AP_Verify.click_Page1Link_Pagination();}
					catch(Exception e)
					{
						
					}
				}
				
			}			

			fail_AP_count=fail_counter0+fail_counter1;			
			
			
			if(fail_AP_count>0)
			{
				switch(countvalue)
				{
					case 0:
						xlutils.insertCellData(TestData_Path, "Modeldetails", row, 8, fail_AP_count+" AccessPoint(s) of "+EntitlementName+" entitlement didn't Match");
						break;
					case 1:
						xlutils.insertCellData(TestData_Path, "Modeldetails", row, 9, fail_AP_count+" AccessPoint(s) of "+EntitlementName+" entitlement didn't Match");
						break;
				}
				
			}
			else
			{
				switch(countvalue)
				{
					case 0:
						xlutils.insertCellData(TestData_Path, "Modeldetails", row, 8, "All AccessPoints are Matched");
						break;
					case 1:
						xlutils.insertCellData(TestData_Path, "Modeldetails", row, 9, "All AccessPoints are Matched");
						break;
				}
				
			}
			
			AP_Verify.click_DoneLink();
			logger.info("***********************************************************");
			logger.info("*****************Clicked on AP_Done link********************");
			logger.info("***********************************************************");
			
		}
		else
		{
			logger.info(EntitlementName+" Access Entitlement and Heading Name are not matched");
			logger.info("***********************************************************");
			logger.info("***********************************************************");
			
		}		
		
	}
	
	public void navigateToPage2()
	{
		AccessEntitlementPage AP_Verify=new AccessEntitlementPage(driver);
		
		AP_Verify.click_Page2Link_Pagination();	
		
	}
	
	public void click_AccessPointLink(int countvalue)
	{
		AccessModelPage ModelPg=new AccessModelPage(driver);
		ModelPg.click_AccessPointLink(countvalue);
		logger.info("Access Point Link is clicked");
	}
	
	public void click_DoneLink() throws InterruptedException
	{
		AccessModelPage ModelPg=new AccessModelPage(driver);
		boolean staleelement=true;
		while(staleelement)
		{
			try {
				
				ModelPg.click_DoneLink();				
				logger.info("Done < button is clicked");
				staleelement=false;
				}
			
			catch(StaleElementReferenceException e)
			{
				staleelement=true;
			}
		}		
		
	}
	
	
	public boolean SearchModel(String ModelName,int row) throws IOException
	{
		ManageAccessModelPage SearchModel=new ManageAccessModelPage(driver);
		SearchModel.enterModelNameIntoSearchBox(ModelName);
		int flag=0;
		logger.info("***********************************************************");
		logger.info("Enter Model Name into Searchbox");
		SearchModel.click_SearchBtn();
		logger.info("*********************************************************");
		logger.info("Search for the Model "+ModelName);
		logger.info("*********************************************************");
		
		try {
			if (SearchModel.ModelTobeDisplayed(ModelName)) {
				logger.info(ModelName + " model exists");
				xlutils.insertCellData(TestData_Path, "Modeldetails", row, 5, "PASSED");
				
				SearchModel.click_ModelToOpen(ModelName);
				logger.info(ModelName+" model is clicked");
				flag=1;
			} 
			
		}
		catch(Exception e)
		{
				logger.info(ModelName + " model doesnot exists");
				logger.info("***********************************************************");
				xlutils.insertCellData(TestData_Path, "Modeldetails", row, 5, "FAILED");
				xlutils.insertCellData(TestData_Path, "Modeldetails", row, 6, "FAILED");
				xlutils.insertCellData(TestData_Path, "Modeldetails", row, 7, "FAILED");
				flag=0;
		}
		
		if(flag==0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	/*	if (SearchModel.ModelTobeDisplayed(ModelName)) {
			logger.info(ModelName + " model exists");
			
			SearchModel.click_ModelToOpen(ModelName);
			logger.info(ModelName+" model is clicked");
			return true;
		} else {
			logger.info(ModelName + " model doesnot exists");
			return false;

		}
	*/
	}
	
	/*
	public void takescreenshot(WebDriver driver,String Screenshot_Name,String Screenshot_Path) throws IOException
	{
		String FileWithPath=Screenshot_Path+File.separator+Screenshot_Name+".png";
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.75f), 1000)).takeScreenshot(driver);		
		
		ImageIO.write(screenshot.getImage(), "jpg", new File(FileWithPath));		
	}*/
	
	
	public void takescreenshot_AShot(WebDriver driver,String Screenshot_Name,String Screenshot_Path) throws IOException
	{
		String FileWithPath=Screenshot_Path+File.separator+Screenshot_Name+".png";
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.75f), 1000)).takeScreenshot(driver);		
		
		ImageIO.write(screenshot.getImage(), "jpg", new File(FileWithPath));		
	}
	
	public void takescreenshot_Robot(WebDriver driver,String Screenshot_Name,String Screenshot_Path) throws IOException, AWTException
	{
		String FileWithPath=Screenshot_Path+File.separator+Screenshot_Name+".png";
		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());		
		BufferedImage screenFullImage = new Robot().createScreenCapture(screenRect);
		ImageIO.write(screenFullImage, "png", new File(FileWithPath));
			
	}
	
	
	
	
		
	public String getModelName(int rownumber) throws IOException
	{
		//int rowcount=xlutils.getRowcount(TestData_Path, "Modeldetails");
		
		int rowcount=rownumber;
		ModelName_XL=xlutils.getCellData(TestData_Path, "Modeldetails", rowcount, 2);		
		
		return ModelName_XL;
	}
	
	public String getEntitlementName1(int rownumber) throws IOException
	{
		//int rowcount = xlutils.getRowcount(TestData_Path, "Modeldetails");

		int rowcount=rownumber;
		EntitlementName1_XL = xlutils.getCellData(TestData_Path, "Modeldetails", rowcount, 3);

		return EntitlementName1_XL;
	}
	
	public String getEntitlementName2(int rownumber) throws IOException
	{
		//int rowcount = xlutils.getRowcount(TestData_Path, "Modeldetails");
		
		int rowcount=rownumber;
		EntitlementName2_XL = xlutils.getCellData(TestData_Path, "Modeldetails", rowcount, 4);

		return EntitlementName2_XL;
	}
	
	public int getEntitlementColumnNumber(String Entitlement) throws IOException
	{
		int colcount_XL_AccessPoints=xlutils.getcolcount(TestData_Path, "AE-AccessPoints");
		String[][] Entitlement_XL = null;
		int colcount_match = 0;
		
		for(int i=0;i<colcount_XL_AccessPoints;i++)
		{
			Entitlement_XL=new String[1][colcount_XL_AccessPoints];
			Entitlement_XL[0][i]=xlutils.getCellData(TestData_Path, "AE-AccessPoints", 0, i);
			if(Entitlement_XL[0][i].contentEquals(Entitlement.trim()))
					{
						colcount_match=i;
						break;
					}
			
		}		
		return colcount_match;
	}
	
	public int getNumberOfAccessPoints(int columnnumber) throws IOException
	{
		int rowcount=xlutils.numberofRowsForaColumn(TestData_Path, "AE-AccessPoints", columnnumber);
		return rowcount;
	}
	
	public String getAccessPointName_XL(int rownumber,int colnumber) throws IOException
	{
		System.out.println("rownumber "+rownumber+" colnumber "+colnumber);
		String AccessPointName_XL=xlutils.getCellData(TestData_Path, "AE-AccessPoints", rownumber, colnumber);	
		System.out.println("Access Point Name XL "+AccessPointName_XL);
		return AccessPointName_XL.trim();
	}
	
	public Set<String> AccessPoints_XL_Collection(String Entitlement,int AP_Count) throws IOException
	//public List<String> AccessPoints_XL_Collection(String Entitlement) throws IOException
	{
		
		//int AP_Count = getNumberOfAccessPoints(getEntitlementColumnNumber(Entitlement));
		
		//ArrayList<String> AP_XL=new ArrayList<String>();
		//HashSet<String> AP_XL=new HashSet<String>();
		
		for(int k=1;k<=AP_Count;k++)
		{
			AP_XL.add(getAccessPointName_XL(k,getEntitlementColumnNumber(Entitlement)));
			
		}
		return AP_XL;
		
	}
	
	public void AccessPoints_XL_Collection1(String Entitlement,int AP_Count) throws IOException
	//public List<String> AccessPoints_XL_Collection(String Entitlement) throws IOException
	{
		
		//int AP_Count = getNumberOfAccessPoints(getEntitlementColumnNumber(Entitlement));
		
		//ArrayList<String> AP_XL=new ArrayList<String>();
		//HashSet<String> AP_XL=new HashSet<String>();
		
		for(int k=1;k<=AP_Count;k++)
		{
			AP_XL.add(getAccessPointName_XL(k,getEntitlementColumnNumber(Entitlement)));
			
		}		
		
	}
	
	public void signout()
	{
		Signout signout=new Signout(driver);
		
		signout.Click_SettingsDropdown();
		signout.Click_SignoutLink();
		logger.info("Signed Out from the application");
		
	}
	
	public void quitbrowser()
	{
		driver.quit();
	}
}
