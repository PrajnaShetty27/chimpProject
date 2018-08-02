package chimpPackage;

import java.io.File;

public class Rename {
	public void renameFiles(String strold,String strnew)
	{
	 //create source File object
    //File oldName = new File("C:\\TestFolder\\Renamefolder\\test.xlsx");
    File oldName = new File(strold);
    //create destination File object
    //File newName = new File("C:\\TestFolder\\Renamefolder\\test.xlsx" + System.nanoTime());
    File newName = new File(strnew + System.nanoTime());
    /*
     * To rename a file or directory, use
     * boolean renameTo(File destination) method of Java File class.
     *
     * This method returns true if the file was renamed successfully, false
     * otherwise.
     */
   
     boolean isFileRenamed = oldName.renameTo(newName);
  
     if(!isFileRenamed)
    	 System.out.println("Error renaming the file " + oldName.getName());
    /* else
    	 System.out.println("Error renaming the file"); */
       
}

}
