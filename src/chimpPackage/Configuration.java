package chimpPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import chimpBasePackage.ChimpBaseClass;

public class Configuration extends ChimpBaseClass{
	String strFilePath,strSheetName,strSheetDelete,strSheetCopy,strSheetReNameTab,strSheetDeleteTab,strSheetCopyTab;
	@BeforeClass(groups={"Main"})
    public void initialiseVariables() throws IOException{
		//To read property file and assign values
		System.out.println("Read Property Completed");
		getProperty();
		System.out.println("Read Property Completed");
    	
    }
	public void getProperty() throws IOException{
		try 
    	{
    	     input = getClass().getClassLoader().getResourceAsStream("Configuration.properties");
    	    // load a properties file
    	    prop.load(input);
    	    strFilePath = prop.getProperty("strFilepathconf");
    	    strSheetName =prop.getProperty("strSheetNameconf");
    	    strSheetDelete = prop.getProperty("strSheetDeleteconf");
    	    strSheetCopy = prop.getProperty("strSheetCopyconf");
    	    strSheetReNameTab =prop.getProperty("strSheetReNameTabconf");
    	    strSheetDeleteTab = prop.getProperty("strSheetDeleteTabconf");
    	    strSheetCopyTab = prop.getProperty("strSheetCopyTabconf");
    		System.out.println(strFilePath + " -- "+ strSheetName + " -- " + strSheetDelete + " -- " + strSheetCopy);
    		

    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	} finally {
    	    if (input != null) {
    	        try {
    	            input.close();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	}
	}
    
    
    /* For renaming a file
    @DataProvider(name = "RENAME")
	public Object[][] BDsetConfigurationsRename() throws Exception {
		Object[][] retObjArr = objBDReadFile.readFromexcel(strConfPath,strSheetReName,strSheetReNameTab);
		return (retObjArr);
	}
	
	@Test(dataProvider = "RENAME",priority=1)
	public void setConfigurationsRename(String strValueold,String strValuenew) throws IOException{
		Rename objRenameFile=new Rename();
		objRenameFile.renameFiles(strValueold,strValuenew);
	}*/

	//To Backup the results in the previous run
		@Test(groups={"Main"})
		public void tc01setBackUpfiles() throws IOException, InterruptedException{
				getPropertyValues();
				System.out.println();
				Backuppreviousresults appZip = new Backuppreviousresults();
				// Zip Code to archive previously executed Results		   
				strOutputzipfile=strOutputzipfile + System.currentTimeMillis() + ".zip";	
			    appZip.generateFileList(new File(strSourcefolder),strSourcefolder);
			    appZip.zipIt(strOutputzipfile,strSourcefolder,strOutputzipfile);
		}
		
	//For Deleting file
	@DataProvider(name = "Delete")
	public Object[][] setConfigurationsDelete() throws Exception {
		Object[][] retObjArr = objBDReadFile.readFromexcel(strFilePath,strSheetDelete,strSheetDeleteTab);
		return (retObjArr);
	}
	
	@Test(dataProvider="Delete",groups={"Main"})
	public void tc02setConfigurationsDelete(String strFolderPath) throws IOException{
		File file = new File(strFolderPath); 
		Delete objDeleteFile=new Delete();
		objDeleteFile.deleteFiles(file);
	}
	
	//For Copying file
	@DataProvider(name = "COPY")
	public Object[][] BDsetConfigurationsCopy() throws Exception {
		
		Object[][] retObjArr = objBDReadFile.readFromexcel(strFilePath,strSheetCopy,strSheetCopyTab);
		return (retObjArr);
	}
	
	
	@Test(dataProvider = "COPY",groups={"Main"})
	public void tc03setConfigurationsCopy(String strValueold,String strValuenew) throws IOException, InterruptedException{
		Copy objCopyFiles=new Copy();
		objCopyFiles.copyFiles(strValueold,strValuenew);
	}
	
	

	public void getPropertyValues() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "ChimpBaseClass.properties";
		input = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (input != null) {
			prop.load(input);
			strOutputzipfile=prop.getProperty("strOutputzipfileconf");
			strSourcefolder=prop.getProperty("strSourcefolderconf");
			strFirefoxDriver = prop.getProperty("strFirefoxDriverconf");
			strBaseUrlDev=prop.getProperty("strBaseUrlDevconf");
			strBaseUrlStage=prop.getProperty("strBaseUrlStageconf");
			strChromeDriver=prop.getProperty("strChromeDriverconf");
			strBSPlatform=prop.getProperty("strBSPlatformconf");
			strBSBrowserVersion=prop.getProperty("strBSBrowserVersionconf");
			strBSSeleniumversion=prop.getProperty("strBSSeleniumversionconf");
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
	
	}
	

}
