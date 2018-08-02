package chimpPackage;

import java.io.File;

public class Delete {
	public void deleteFiles(File file)
	{
		String[] myFiles;    
	    if(file.isDirectory())
	    {
	        myFiles = file.list();
	        for (int i=0; i<myFiles.length; i++) 
	        {
	            File myFile = new File(file, myFiles[i]); 
	            myFile.delete();
	        }
	    }
	}

}
