package chimpPackage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Copy {

	public void copyFiles(String source,String target) throws IOException
	{
		/*absolute path for source file to be copied
         String source = "C:\\TestFolder\\ExcelNew.xlsx";
         directory where file will be copied
         String target ="C:\\TestFolder\\Renamefolder\\"; */

      //name of source file
        
        File sourceFile = new File(source);
		String name = sourceFile.getName();
		
		File targetFile = new File(target+name);
		//System.out.println("Copying file : " + sourceFile.getName() +" from Java Program");

		FileUtils.copyFile(sourceFile , targetFile );
		//System.out.println("File Copy completed");
	}
}
