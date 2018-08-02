package chimpPackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Backuppreviousresults {
	
	private List<String> fileList;
	public Backuppreviousresults()
	{
	   fileList = new ArrayList<String>();
	}

	/*
	public static void main(String[] args)
	{
	   String strOutputzipfile="/opt/autoBackup/backupAutomation/backupAutomation";
		String strSourceFolder="/opt/backupAutomation";
		Backuppreviousresults appZip = new Backuppreviousresults();
		strOutputzipfile=strOutputzipfile + System.currentTimeMillis() + ".zip";
		   appZip.generateFileList(new File(strSourceFolder),strSourceFolder);
		   appZip.zipIt(strOutputzipfile,strSourceFolder,strOutputzipfile);
	}*/

	public void zipIt(String zipFile,String strSourcefolder,String strOutputzipfile )
	{
	   System.out.println(" --- > " +zipFile +"  -- "  + strSourcefolder +" --- " + strOutputzipfile);
		byte[] buffer = new byte[1024];
	   String source = "";
	   FileOutputStream fos = null;
	   ZipOutputStream zos = null;
	  
	   try
	   {
	      try
	      {
	         source = strSourcefolder.substring(strSourcefolder.lastIndexOf("/")+1, strSourcefolder.length());
	      }
	     catch (Exception e)
	     {
	        source = strSourcefolder;
	     }
	     fos = new FileOutputStream(zipFile);
	     zos = new ZipOutputStream(fos);
	     
	     FileInputStream in = null;

	     for (String file : this.fileList)
	     {
	        ZipEntry ze = new ZipEntry(source + File.separator + file);
	        zos.putNextEntry(ze);
	        try
	        {
	           in = new FileInputStream(strSourcefolder + File.separator + file);
	           int len;
	           while ((len = in.read(buffer)) > 0)
	           {
	              zos.write(buffer, 0, len);
	           }
	           System.out.println(ze.getName());
	        }
	        finally
	        {
	           in.close();
	        }
	     }

	     zos.closeEntry();
	     System.out.println("Folder successfully compressed");

	  }
	  catch (IOException ex)
	  {
	     ex.printStackTrace();
	  }
	  finally
	  {
	     try
	     {
	        zos.close();
	     }
	     catch (IOException e)
	     {
	        e.printStackTrace();
	     }
	  }
	}

	public void generateFileList(File node,String strSourcefolder)
	{

	  // add file only
	  if (node.isFile())
	  {
	     fileList.add(generateZipEntry(node.toString(),strSourcefolder));

	  }

	  if (node.isDirectory())
	  {
	     String[] subNote = node.list();
	     for (String filename : subNote)
	     {
	    	 generateFileList(new File(node, filename),strSourcefolder);
	     }
	  }
	}

	private String generateZipEntry(String file,String strSourcefolder)
	{
	   return file.substring(strSourcefolder.length() + 1, file.length());
	}

}
