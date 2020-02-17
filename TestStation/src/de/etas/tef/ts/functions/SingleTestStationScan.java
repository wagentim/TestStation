package de.etas.tef.ts.functions;

import java.nio.file.Path;
import java.util.List;

import de.etas.tef.ts.filter.FindATEToolsDirFilter;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.scanner.DirScanner;
import de.etas.tef.ts.scanner.TestProgramScanner;
import de.etas.tef.ts.utils.IConstants;

public class SingleTestStationScan implements Runnable
{
	private final Path startDir;
	private final String stationName;
	private final boolean scanLocalInfo;
	private final DirScanner dirScanner;
	private final TestStation ts;
	
	public SingleTestStationScan(final Path startDir, final String stationName, final boolean scanLocalInfo)
	{
		this.startDir = startDir;
		this.stationName = stationName;
		this.scanLocalInfo = scanLocalInfo;
		dirScanner = new DirScanner(startDir);
		ts = new TestStation();
		ts.setName(stationName);
	}
	
	public TestStation getTestStation()
	{
		return ts;
	}
	
	private Path findATEToolsDir()
	{
		dirScanner.removeAllFilters();
		List<Path> ateDir = dirScanner.addFilter(FindATEToolsDirFilter.class).startScanning();
		
		if(ateDir.size() == 1)
		{
			return ateDir.get(0);
		}
		
		return null;
	}
	
	@Override
	public void run()
	{
		
		Path ateDir = findATEToolsDir();
		
		if( ateDir == null )
		{
			ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Cannot find 'ATE-Tools' Directory in: " + startDir.toString());
			return;
		}
		else
		{
			ActionManager.INSTANCE.sendAction(IConstants.MSG_GREEN, "Scan Test Program in Location: " + ateDir.toString());
			TestProgramScanner tpScanner = new TestProgramScanner(ateDir, ts);
			tpScanner.run();
		}
	}
}
