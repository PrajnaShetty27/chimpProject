package chimpPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenShot {
	InputStream input;
	String strFileLoc, strfileExtension;
	public TakeScreenShot() {
		try {
			getPropertyValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	String strfilePath=null;
	public void getscreenshot(WebDriver driver,String strfileName) throws Exception {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in c drive with name
		// "image_runningtime.pnj"
		strfileName=strfileName.replace("&", "");
		strfileName=strfileName.replace(" ", "");
		strfilePath = strFileLoc + strfileName + System.currentTimeMillis() + strfileExtension;
		System.out.println(" >>> >> " + strfilePath);
		FileUtils.copyFile(scrFile, new File(strfilePath));
	}
	
	public void getPropertyValues() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "TakeScreenShot.properties";
		input = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (input != null) {
			prop.load(input);
			strFileLoc=prop.getProperty("strFileLocconf");
			strFileLoc=System.getProperty("user.dir") +File.separator+strFileLoc;
			strfileExtension=prop.getProperty("strfileExtensionconf");
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
	
	}

}
