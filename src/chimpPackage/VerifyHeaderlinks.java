package chimpPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class VerifyHeaderlinks extends ChimpBaseForSequentialExecution{
	String strDropdownlink,strdashboardhomePage,strManageSettings,strManageGivingGroups,
	strManageCampaign,strFilepath,strSheetname, strTabname,strScreenshotfilename;
	WebElement weElement;
	
	Actions actions;
	@BeforeClass(groups={"Smoke","Regression"})
	public void initialiseDriver() throws IOException{
		setWebDriver(getDriver());
		System.out.println(driver.getCurrentUrl());
		getPropertyValues();
	}
	public void getPropertyValues() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "VerifyHeaderlinks.properties";
		//input = getClass().getClassLoader().getResourceAsStream(propFileName);
		input=new FileInputStream(System.getProperty("user.dir") +File.separator+ "resource"+File.separator+propFileName) ;
		if (input != null) {
			prop.load(input);
			strDropdownlink=prop.getProperty("strDropdownlinkconf");
			strdashboardhomePage=prop.getProperty("strdashboardhomePageconf");
			strManageSettings=prop.getProperty("strManageSettingsconf");
			strManageGivingGroups=prop.getProperty("strManageGivingGroupsconf");
			strManageCampaign=prop.getProperty("strManageCampaignconf");
			strFilepath=prop.getProperty("strFilepathconf");
			strFilepath=System.getProperty("user.dir") +File.separator+strFilepath;
			strSheetname=prop.getProperty("strSheetnameconf");
			strTabname=prop.getProperty("strTabnameconf");
			System.out.println();
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
	}
	
	@DataProvider(name = "MANAGELINKS")
	public Object[][] tc01ManageSettings() throws Exception {
		Object[][] retObjArr = objBDReadFile.readFromexcel(strFilepath,strSheetname, strTabname);
		return (retObjArr);
	}
	//Manage Settings section	
	@Test(dataProvider="MANAGELINKS",groups={"Smoke","Regression"})
	public void tc01ManageSettings(String strlinkToclick, String strPagetitletoverify, String strNameoflink) throws Exception{
		//Select Dropdown list
		byElement=By.xpath(strDropdownlink);	
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(byElement).click();
		
		//Click on Element
		byElement=By.xpath("//*[text()='"+strlinkToclick+"']");	
		System.out.println(byElement.toString());
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(byElement).click();
		strScreenshotfilename= strlinkToclick+System.currentTimeMillis();
		
		objTakeScreenShot.getscreenshot(driver, strScreenshotfilename);
		//Cpmpare the headings
		getSettings(strPagetitletoverify,strNameoflink);
		
		//Set the driver to dashboard page
		byElement=By.xpath(strdashboardhomePage);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(byElement).click();
	}
	
	public void getSettings(String strVerifyPageTitle,String strLinkname){
		try
		{
			System.out.println("Page Title "+driver.getTitle());
			Assert.assertEquals(driver.getTitle(), strVerifyPageTitle);
			Reporter.log(strLinkname +" Navigation link - PASS");
		}
		catch(AssertionError ae){
			Reporter.log(strLinkname + " Navigation link - FAILED");
		}
	}
	
	//@Test(groups={"Smoke","Regression"})
	@AfterClass(groups={"Smoke","Regression"})
	public void tc04SetDrivertoHome() throws IOException, InterruptedException {
		
		byElement=By.xpath(strdashboardhomePage);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(By.xpath(strdashboardhomePage)).click();
		driver.getCurrentUrl();
		setWebDriver(driver);
	}
	
}

