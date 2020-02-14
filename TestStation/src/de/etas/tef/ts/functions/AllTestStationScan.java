package de.etas.tef.ts.functions;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.ts.filter.FindValidDirFilter;
import de.etas.tef.ts.filter.TestStationNameFilter;
import de.etas.tef.ts.gui.ActionManager;
import de.etas.tef.ts.scanner.DirScanner;
import de.etas.tef.ts.utils.IConstants;

public class AllTestStationScan implements Runnable
{
	private final String startDir;
	private final DirScanner dirScanner;
	private final Display display;
	private List<Path> stations = Collections.emptyList();
	
	public AllTestStationScan(final String startDir, final Display display)
	{
		this.startDir = startDir;
		this.dirScanner = new DirScanner();
		this.display = display;
	}

	@Override
	public void run()
	{
		boolean isTestStationDisk = checkTestStationDisk();
		
		if(!isTestStationDisk)
		{
			ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Plese check selected Disk. It seems not the Test Station Disk...");
			return;
		}
		
		ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Find Total: " + stations.size() + " Stations.");
		
		for(Path p : stations)
		{
			Path validDirPath = searchValidDir(p);
			
			if(validDirPath != null)
			{
				SingleTestStationScan sts = new SingleTestStationScan(validDirPath, p.getFileName().toString(), false);
				sts.run();
			}
			else
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Cannot find '_Valid' Directory in path: " + p.toString() + ". Ignore the Scan for Station: " + p.getFileName().toString());
			}
		}
	}

	private Path searchValidDir(Path p)
	{
		dirScanner.removeAllFilters();
		dirScanner.setStartDir(p);
		List<Path> validDir = dirScanner.addFilter(FindValidDirFilter.class).startScanning();
		
		if(validDir.size() == 1)
		{
			return validDir.get(0);
		}
		else
		{
			return null;
		}
	}

	private boolean checkTestStationDisk()
	{
		List<String> internalStations = (List<String>) Arrays.asList(IConstants.TEST_STATIONS);
		dirScanner.removeAllFilters();
		dirScanner.setStartDir(startDir).addFilter(TestStationNameFilter.class);
		stations = dirScanner.startScanning();
		
		int found = 0;
		
		for(Path scanStation : stations)
		{
			if(internalStations.contains(scanStation.getFileName().toString().toLowerCase()))
			{
				found++;
			}
		}
		return found > 4 ? true : false;
	}
}
