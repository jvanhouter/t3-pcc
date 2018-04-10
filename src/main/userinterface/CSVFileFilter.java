package userinterface;

import javax.swing.filechooser.FileFilter;

import java.io.*;

public class CSVFileFilter extends FileFilter
{

	//-------------------------------------------
	public boolean accept(File f)
	{
		String fileName = f.getName();
		
		if ((fileName.endsWith(".csv")) || (fileName.endsWith(".CSV")) || (f.isDirectory()))
			return true;
		
		else
			return false;
	}
	
	//-------------------------------------------
	public String getDescription()
	{
		return "CSV File Filter";
	}
}
