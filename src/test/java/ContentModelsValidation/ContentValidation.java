package ContentModelsValidation;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Test;

import Utilities.Utilities;
import Utilities.XLUtility;

public class ContentValidation extends Utilities{
	String ScreenshotsPath;
	int EntitlementCount;
	String AccessEntitlement=null;
	String AccessPoint=null;
	public XLUtility xlutils=new XLUtility();	
	
	
	
	@Test
	public void ContentValidations() throws InterruptedException, IOException, AWTException 
	{	
		
		test=extent.createTest("Content Model Validation");
		
		Login();
		
		NavigateToAdvancedModels();		
		
		int rowcount_XL_ModelDetails=xlutils.getRowcount(TestData_Path, "Modeldetails");
		
		String Dir_Path=create_DirectoryFolder();
		
		for (int i = 1; i <= rowcount_XL_ModelDetails; i++) {
			
			String Model=getModelName(i).trim();
			
			String Entitlement1=getEntitlementName1(i).trim();
			
			String Entitlement2=getEntitlementName2(i).trim();
			
			String Foldername=create_ModelsFolder(Model,Dir_Path);			

			if(SearchModel(Model,i))
			{
				Verify_Heading_ModelName(Model, Foldername);

				Verify_Modelname(Model,i);
				
				for(int j=0;j<=1;j++)
				{
					
					if(j==0)
						
					{
						
						AccessEntitlement = Entitlement1;												
						
					}
					
					else 
					{

						AccessEntitlement = Entitlement2;
						
						System.out.println("AccessEntitlement "+AccessEntitlement);
						
						if (!AccessEntitlement.contentEquals("NULL")) 
						
						{							
													
							
						}
						
						 else
							 
						{
							xlutils.insertCellData(TestData_Path, "Modeldetails", i, 7, "ACCESS ENTITLEMENT 2 IS NULL");
							xlutils.insertCellData(TestData_Path, "Modeldetails", i, 9, "ACCESS ENTITLEMENT 2 IS NULL");	
							break;
							
						}
						
					}	
					
					VerifyEntitlement_ModelPage(AccessEntitlement,j,i);						

					VerifyAccessPoints_AEPage(AccessEntitlement, Foldername,i,j);
					
				}
				
				Thread.sleep(1000);
				
				click_DoneLink();
				
			}
			else
			{
				
				continue;
				
			}			
			
		}
		
		signout();
		
		quitbrowser();
		
	}

}
