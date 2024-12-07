package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;


public class AppTest {
String inputpath= "FileInput/DataEngine.xlsx";
String outputpath="./FileOutput/HybridResults.xlsx";
String TCSheet="MasterTestCases";
ExtentReports reports;
ExtentTest logger;
WebDriver driver;
@Test
public void startTest() throws Throwable
{
	String Module_Status="";
	String Module_New="";
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	int rc=xl.rowCount(TCSheet);
	for(int i=1;i<=rc;i++)
	{
		if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
		{
		String TCModule = xl.getCellData(TCSheet, i, 1)	;
		reports =new ExtentReports("./target/reports/"+TCModule+FunctionLibrary.generateDate()+".html");
		logger = reports.startTest(TCModule);
		for(int j=1;j<=xl.rowCount(TCModule);j++)
		{
			String Description = xl.getCellData(TCModule, j, 0);
			String ObjectType = xl.getCellData(TCModule, j, 1);
			String LType = xl.getCellData(TCModule, j, 2);
			String LValue = xl.getCellData(TCModule, j, 3);
			String TestData = xl.getCellData(TCModule, j, 4);
			try {
				if(ObjectType.equalsIgnoreCase("startBrowser"))
				{
					driver= FunctionLibrary.startBrowser();
					logger.log(LogStatus.INFO, Description)	;	
					}
				if(ObjectType.equalsIgnoreCase("openUrl"))
				{
					FunctionLibrary.openUrl();
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("waitForElement"))
				{
					FunctionLibrary.waitForElement(LType, LValue, TestData);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("typeAction"))
				{
					FunctionLibrary.typeAction(LType, LValue, TestData);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("clickAction"))
				{
					FunctionLibrary.clickAction(LType, LValue);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("validateTitle"))
				{
					FunctionLibrary.validateTitle(TestData);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("closeBrowser"))
				{
					FunctionLibrary.closeBrowser();
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("dropdownAction"))
				{
					FunctionLibrary.dropdownAction(LType, LValue, TestData);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("captureStock"))
				{
					FunctionLibrary.captureStock(LType, LValue);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("stockTable"))
				{
					FunctionLibrary.stockTable();
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("captureSup"))
				{
					FunctionLibrary.captureSup(LType, LValue);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("supplierTable"))
				{
					FunctionLibrary.supplierTable();
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("captureCus"))
				{
					FunctionLibrary.captureCus(LType, LValue);
					logger.log(LogStatus.INFO, Description)	;
				}
				if(ObjectType.equalsIgnoreCase("customerTable"))
				{
					FunctionLibrary.customerTable();
					logger.log(LogStatus.INFO, Description)	;
				}	
				xl.setCellData(TCModule, j, 5, "PASS", outputpath);
				logger.log(LogStatus.PASS, Description);
				Module_Status="True";
			} catch (Exception e) {
				xl.setCellData(TCModule, j, 5, "Fail", outputpath);
				logger.log(LogStatus.PASS, Description);
				Module_New="False";
			}
			if(Module_Status.equalsIgnoreCase("True"))
			{
				xl.setCellData(TCSheet, i, 3, "PASS", outputpath);
			}
			if(Module_New.equalsIgnoreCase("False"))
			{
				xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
			}
			reports.endTest(logger);
			reports.flush();
		}
		}
		else
		{
			//write as blocked into Tcsheet which testcase flag to N
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
	}
}
}
