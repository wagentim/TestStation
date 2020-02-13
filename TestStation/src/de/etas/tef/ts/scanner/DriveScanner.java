package de.etas.tef.ts.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import de.etas.tef.ts.json.Driver;

public class DriveScanner extends AbstractScanner<List<Driver>>
{

	public List<Driver> scan()
	{
		List<Driver> result = new ArrayList<Driver>();
		
		FileSystemView fsv = FileSystemView.getFileSystemView();
        
        File[] drives = File.listRoots();
        if (drives != null && drives.length > 0) 
        {
            for (File aDrive : drives) 
            {
            	Driver drv = new Driver();
            	drv.setLetter(aDrive.toString());
            	drv.setType(fsv.getSystemTypeDescription(aDrive));
            	drv.setName(fsv.getSystemDisplayName(aDrive));
            	
            	result.add(drv);
            }
        }
		
		return result;
	}
}
