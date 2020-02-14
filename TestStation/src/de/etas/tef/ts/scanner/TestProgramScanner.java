package de.etas.tef.ts.scanner;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import de.etas.tef.ts.filter.FindClusterTestDirFilter;
import de.etas.tef.ts.filter.FindFunctionTestDirFilter;
import de.etas.tef.ts.filter.FindProjectDirFilter;
import de.etas.tef.ts.gui.ActionManager;
import de.etas.tef.ts.json.AbstractTestProgram;
import de.etas.tef.ts.json.ClusterTest;
import de.etas.tef.ts.json.FunctionTest;
import de.etas.tef.ts.json.Project;
import de.etas.tef.ts.utils.IConstants;

public class TestProgramScanner implements Runnable
{
	private final Path ateDir;
	private final DirScanner dirScanner;
	
	private Path ctPath = null;
	private Path ftPath = null;
	
	private ClusterTest ct = null;
	private FunctionTest ft = null;
	
	public TestProgramScanner(final Path ateDir)
	{
		this.ateDir = ateDir;
		dirScanner = new DirScanner(ateDir);
	}
	
	@Override
	public void run()
	{
		if(null == ateDir)
		{
			return;
		}
		
		dirScanner.removeAllFilters();
		List<Path> paths = dirScanner.setStartDir(ateDir).addFilter(FindClusterTestDirFilter.class).startScanning();
		
		if(paths != null && paths.size() == 1)
		{
			ctPath = paths.get(0);
			if( ctPath != null )
			{
				ct = new ClusterTest();
				ActionManager.INSTANCE.sendAction(IConstants.MSG_GREY, "Scan Cluster Test......");
				scanProjects(ctPath, ct);
			}
		}
		
		dirScanner.removeAllFilters();
		paths = dirScanner.setStartDir(ateDir).addFilter(FindFunctionTestDirFilter.class).startScanning();
		
		if(paths != null && paths.size() == 1)
		{
			ftPath = paths.get(0);
			if( ftPath != null )
			{
				ft = new FunctionTest();
				ActionManager.INSTANCE.sendAction(IConstants.MSG_GREY, "Scan Function Test......");
				scanProjects(ftPath, ft);
			}
		}
		
	}
	
	private void scanProjects(Path startDir, AbstractTestProgram atp)
	{
		dirScanner.removeAllFilters();
		dirScanner.setStartDir(startDir);
		List<Path> projects = dirScanner.addFilter(FindProjectDirFilter.class).startScanning();
		
		Iterator<Path> it = projects.iterator();
		
		int i = 0;
		
		while(it.hasNext())
		{
			Path p = it.next();
			
			if( p == null )
			{
				continue;
			}
			
			Project prj = new Project();
			prj.setPath(p);
			atp.addProject(prj);
			i++;
			ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Project: " + p.getFileName().toString());
		}
		
		ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Total: " + i);
	}
}
