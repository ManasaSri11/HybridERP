package commonFunctions;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;


	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if
		(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
		Reporter.log("Browser Value is Not Matching",true);
		}
		return driver;
	}
	

	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));

	}
	
	public static void waitForElement(String LocatorType,String LocatorValue,String wait)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(wait)));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	
	
	public static void typeAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}
	
	public static void clickAction(String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
	}
	
	
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title,"Title is Not Matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void closeBrowser()
	{
		driver.quit();
	}
	
	public static void dropdownAction(String LocatorType,String LocatorValue,String Testdata)
	{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		int value =Integer.parseInt(Testdata);
		Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
		element.selectByIndex(value);
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		int value =Integer.parseInt(Testdata);
		Select element = new Select(driver.findElement(By.name(LocatorValue)));
		element.selectByIndex(value);
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		int value =Integer.parseInt(Testdata);
		Select element = new Select(driver.findElement(By.id(LocatorValue)));
		element.selectByIndex(value);
	}
	}
	
	public static void captureStock(String LocatorType,String LocatorValue) throws Throwable
	{
		String stockNumber = "";
		
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			stockNumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			stockNumber=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			stockNumber=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknum.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();
		
	}
	
	public static void stockTable() throws Throwable
	{
		FileReader fr= new FileReader("./CaptureData/stocknum.txt");
		BufferedReader br= new BufferedReader(fr);
		String Exp_Data= br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"   "+Act_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data,"Stock Number Not Matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void captureSup(String LocatorType,String LocatorValue) throws Throwable
	{
String supplierNumber = "";
		
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			supplierNumber=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			supplierNumber=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			supplierNumber=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/supplierNum.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(supplierNumber);
		bw.flush();
		bw.close();

	}
	
	public static void supplierTable() throws Throwable
	{
		FileReader fr= new FileReader("./CaptureData/supplierNum.txt");
		BufferedReader br= new BufferedReader(fr);
		String Exp_Data= br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data+"   "+Act_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data,"Supplier Number Not Matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void captureCus(String LocatorType,String LocatorValue) throws Throwable
	{
		String CustomerNum = "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			CustomerNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			CustomerNum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			CustomerNum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/customernum.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
	}
	
	public static void customerTable() throws Throwable
	{
		FileReader fr= new FileReader("./CaptureData/customernum.txt");
		BufferedReader br= new BufferedReader(fr);
		String Exp_Data= br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+"   "+Act_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data,"Customer Number Not Matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static String generateDate()
	{
		Date date =new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD");
		return df.format(date);
				
	}
}



