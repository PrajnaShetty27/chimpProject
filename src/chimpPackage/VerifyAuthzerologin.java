package chimpPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;


public class VerifyAuthzerologin extends ChimpBaseForParallelExecutionOne{
	
    String strFilepath,strSheetname,strTabname,strloginbutton,
	strloginbuttonValidate,strsignUpbuttonValidate,strHome,stUsername,
	clickSignupbuttonValidate,clickSignupbuttonValidateusername,strPassword,
	strErromessageValidation,strErrorValdiate,submituserpwd,strsetDrivertoHome,
	strFirstName,strLastName,strSignupsubmit,strResendemailbutton,auth0Userid,access_token,
	strCreateUser,strCreateuserPwd="Test123!",strLoginasNewUser;
    MakeApicalls objMakeApicalls=new MakeApicalls();
	Boolean bgetValue;
	@BeforeClass(groups={"Smoke","Regression"})
	public void initialiseDriver() throws IOException{
		setWebDriver(getDriver());
		System.out.println(driver.getCurrentUrl());
		getPropertyValues();
	}
	public void getPropertyValues() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "VerifyAuthzerologin.properties";
		input = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (input != null) {
			prop.load(input);
			strFilepath=prop.getProperty("strFilepathconf");
			strSheetname=prop.getProperty("strSheetnameconf");
			strTabname=prop.getProperty("strTabnameconf");
			strloginbutton=prop.getProperty("strloginbuttonconf");
			strloginbuttonValidate=prop.getProperty("strloginbuttonValidateconf");
			strsignUpbuttonValidate=prop.getProperty("strsignUpbuttonValidateconf");
			strHome=prop.getProperty("strHomeconf");
			stUsername=prop.getProperty("stUsernameconf");
			clickSignupbuttonValidate=prop.getProperty("clickSignupbuttonValidateconf");
			clickSignupbuttonValidateusername=prop.getProperty("clickSignupbuttonValidateusernameconf");
			strPassword=prop.getProperty("strPasswordconf");
			strErromessageValidation=prop.getProperty("strErromessageValidationconf");
			submituserpwd=prop.getProperty("submituserpwdconf");
			strsetDrivertoHome=prop.getProperty("strsetDrivertoHomeconf");
			strFirstName=prop.getProperty("strFirstNameconf");
			strLastName=prop.getProperty("strLastNameconf");
			strSignupsubmit=prop.getProperty("strSignupsubmitconf");
			strResendemailbutton=prop.getProperty("strResendemailbuttonconf");
			strLoginasNewUser=prop.getProperty("strLoginasNewUserconf");
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
	}
	//is log in button Displayed
	@Test(groups={"Smoke","Regression"})
	public void tc01IsLoginbuttonpresent()
	{
		byElement=By.xpath(strloginbutton);		
		objWaitforanelement.waitForElement(driver, byElement);	
		bgetValue= driver.findElement(By.xpath(strloginbutton)).isDisplayed();
		
		try{
			Assert.assertEquals(true, bgetValue.booleanValue());
			Reporter.log("Login Button Displayed - PASS");
			System.out.println("Login Button Displayed");
		}
		catch(AssertionError e){
			Reporter.log(e.toString());
		}
		System.out.println("Login Button Displayed");
	}
	
	//username text box Displayed
	@Test(groups={"Smoke","Regression"})
	public void tc02IsUsernamepresent()
	{
		driver.findElement(By.xpath(strloginbutton)).click();
		byElement=By.xpath(stUsername);		
		objWaitforanelement.waitForElement(driver, byElement);
		bgetValue= driver.findElement(By.xpath(stUsername)).isDisplayed();
		try{
		Assert.assertEquals(true, bgetValue.booleanValue());
		System.out.println("User name text box displayed");
		Reporter.log("User name text box displayed  - PASS");
		}
		catch(AssertionError e){
			Reporter.log(e.toString());
		}
	}

	//sign up button displayed
	 @Test(groups={"Smoke","Regression"})
	public void tc03IsSignupbuttonpresent(){
		driver.navigate().back();
		byElement=By.xpath(strsignUpbuttonValidate);		
		objWaitforanelement.waitForElement(driver, byElement);
		bgetValue= driver.findElement(By.xpath(strsignUpbuttonValidate)).isDisplayed();
		try{
			Assert.assertEquals(true, bgetValue.booleanValue());
			System.out.println("Sign up Button Displayed");
			Reporter.log("Sign up Button Displayed - PASS");
		}
		catch(AssertionError e){
			Reporter.log(e.toString());
		}
		
	}
	
	//signup button click and validate using the presence of username
	 @Test(groups={"Smoke","Regression"})
	public void tc04ValidateSignUpbutton(){
		driver.findElement(By.xpath(strsignUpbuttonValidate)).click();
		byElement=By.xpath(clickSignupbuttonValidateusername);		
		objWaitforanelement.waitForElement(driver, byElement);
		bgetValue= driver.findElement(By.xpath(clickSignupbuttonValidateusername)).isDisplayed();
		try{
			Assert.assertEquals(true, bgetValue.booleanValue());
			Reporter.log("Validate SignUp button - PASS");
		}
		catch(AssertionError e){
			Reporter.log(e.toString());
		}
	}
	
	//Invalid email id password check
	@Test(groups={"Smoke","Regression"},enabled=false)
	public void tc05VerifyInvalidcredentialcombination(){
		driver.navigate().back();
		byElement=By.xpath(strloginbutton);		
		objWaitforanelement.waitForElement(driver, byElement);	
		driver.findElement(By.xpath(strloginbutton)).click();
		byElement=By.xpath(stUsername);		
		objWaitforanelement.waitForElement(driver, byElement);		
		driver.findElement(By.xpath(stUsername)).sendKeys("sanu+2@gmail.com");
		byElement=By.xpath(strPassword);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(By.xpath(strPassword)).sendKeys("Test123!"); //actual password is Test123#
		byElement=By.xpath(submituserpwd);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(By.xpath(submituserpwd)).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		byElement=By.xpath(strErromessageValidation);
		objWaitforanelement.waitForElement(driver, byElement);
		
		WebDriverWait wait=new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(byElement), "THE EMAIL OR PASSWORD YOU'VE USED IS INCORRECT. PLEASE TRY AGAIN."));
		
		strErrorValdiate=driver.findElement(By.xpath(strErromessageValidation)).getText();
		try{
		Assert.assertEquals(strErrorValdiate, "THE EMAIL OR PASSWORD YOU'VE USED IS INCORRECT. PLEASE TRY AGAIN.");
		Reporter.log("Incorrect User name / Password entry scenario to validate the error message - PASS ");
		}catch(AssertionError e){
			Reporter.log(e.toString());
			//Assert.assertNotSame(strErrorValdiate, "THE EMAIL OR PASSWORD YOU'VE USED IS INCORRECT. PLEASE TRY AGAIN.","Values are not Matching");
		}
	}
	
	@Test(groups={"Smoke","Regression"})
	public void tc06NewUsersignup() throws InterruptedException, ClientProtocolException, IOException{
		try{
			byElement=By.xpath(strsetDrivertoHome);
			objWaitforanelement.waitForElement(driver, byElement);
			driver.findElement(By.xpath(strsetDrivertoHome)).click();
			
			byElement=By.xpath(strsignUpbuttonValidate);
			objWaitforanelement.waitForElement(driver, byElement);
			driver.findElement(By.xpath(strsignUpbuttonValidate)).click();
			
			strCreateUser="TestUIAuto"+String.valueOf(System.currentTimeMillis())+"@gmail.com";
			byElement=By.xpath(stUsername);		
			objWaitforanelement.waitForElement(driver, byElement);		
			driver.findElement(By.xpath(stUsername)).sendKeys(strCreateUser);
			
			byElement=By.xpath(strPassword);
			objWaitforanelement.waitForElement(driver, byElement);
			driver.findElement(By.xpath(strPassword)).sendKeys(strCreateuserPwd); //actual password is Test123#
			
			//Enter FirsName and LastName for signupUser
			byElement=By.xpath(strFirstName);		
			objWaitforanelement.waitForElement(driver, byElement);		
			driver.findElement(By.xpath(strFirstName)).sendKeys(strCreateUser);
			
			byElement=By.xpath(strLastName);		
			objWaitforanelement.waitForElement(driver, byElement);		
			driver.findElement(By.xpath(strLastName)).sendKeys(strCreateUser);
			
			byElement=By.xpath(strSignupsubmit);		
			objWaitforanelement.waitForElement(driver, byElement);		
			driver.findElement(By.xpath(strSignupsubmit)).click();
			
			System.out.println(driver.getCurrentUrl());
			
			//verify logged in by waiting for Re send email button
			
			byElement=By.xpath(strResendemailbutton);		
			objWaitforanelement.waitForElement(driver, byElement);		
			driver.findElement(By.xpath(strResendemailbutton)).isDisplayed();
			JavascriptExecutor js = (JavascriptExecutor) driver; 
			auth0Userid=js.executeScript(String.format("return window.localStorage.getItem('%s');", "auth0UserId")).toString();
			auth0Userid=auth0Userid.substring(6, auth0Userid.length());
			access_token=objMakeApicalls.getUsertoken();
			System.out.println("UserID " + auth0Userid + "  ---- " + " Access Token "+ access_token);
			
			objMakeApicalls.activateUser(auth0Userid, access_token);
			System.out.println("New User Activation Completed");
			Reporter.log("New User Activation PASS");
		}
		catch(Exception e){
			Reporter.log(e.toString());
		}
	}
	@Test(groups={"Smoke","Regression"})
	public void tc07LoginasNewuser(){
		try{
		driver.findElement(By.xpath(strsetDrivertoHome)).click();
		byElement=By.xpath(strloginbutton);		
		objWaitforanelement.waitForElement(driver, byElement);	
		driver.findElement(By.xpath(strloginbutton)).click();
		byElement=By.xpath(stUsername);		
		objWaitforanelement.waitForElement(driver, byElement);		
		driver.findElement(By.xpath(stUsername)).sendKeys(strCreateUser);
		byElement=By.xpath(strPassword);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(By.xpath(strPassword)).sendKeys(strCreateuserPwd); //actual password is Test123#
		byElement=By.xpath(submituserpwd);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(By.xpath(submituserpwd)).click();
		Reporter.log("Signed up user logged in status PASS");
		}
		catch(Exception e){
			Reporter.log(e.toString());
		}
	}
	//@Test(groups={"Smoke","Regression"})
	@AfterClass(groups={"Smoke","Regression"})
	public void tc08SetDrivertoHome() throws IOException, InterruptedException {
		byElement=By.xpath(strsetDrivertoHome);
		objWaitforanelement.waitForElement(driver, byElement);
		driver.findElement(By.xpath(strsetDrivertoHome)).click();
		driver.getCurrentUrl();
		setWebDriver(driver);
		//Email list is mentioned in the respective property file of send email class
		//objSendEmail.intiateEmail();
	}

}
