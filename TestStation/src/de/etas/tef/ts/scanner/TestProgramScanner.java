package de.etas.tef.ts.scanner;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import de.etas.tef.ts.filter.FindClusterTestDirFilter;
import de.etas.tef.ts.filter.FindFunctionTestDirFilter;
import de.etas.tef.ts.filter.FindProjectDirFilter;
import de.etas.tef.ts.filter.FindVersionDirFilter;
import de.etas.tef.ts.gui.ActionManager;
import de.etas.tef.ts.json.AbstractTestProgram;
import de.etas.tef.ts.json.ClusterTest;
import de.etas.tef.ts.json.FunctionTest;
import de.etas.tef.ts.json.Project;
import de.etas.tef.ts.json.Test;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.utils.IConstants;

public class TestProgramScanner implements Runnable
{
	private final Path ateDir;
	private final DirScanner dirScanner;
	private final ExeTestProgramScanner exeScanner;
	
	private Path ctPath = null;
	private Path ftPath = null;
	
	private ClusterTest ct = null;
	private FunctionTest ft = null;
	private TestStation ts = null;
	
	public TestProgramScanner(final Path ateDir, TestStation ts)
	{
		this.ateDir = ateDir;
		dirScanner = new DirScanner(ateDir);
		exeScanner = new ExeTestProgramScanner();
		this.ts = ts;
	}
	
	@Override
	public void run()
	{
		if(null == ateDir)
		{
			return;
		}
		
		// Scan project under Cluster Test
		dirScanner.removeAllFilters();
		List<Path> paths = dirScanner.setStartDir(ateDir).addFilter(FindClusterTestDirFilter.class).startScanning();
		
		if(paths != null && paths.size() == 1)
		{
			ctPath = paths.get(0);
			if( ctPath != null )
			{
				ct = new ClusterTest();
				ts.addClusterTest(ct);
				ActionManager.INSTANCE.sendAction(IConstants.MSG_GREY, "Scan Cluster Test......");
				scanProjects(ctPath, ct);
			}
		}
		
		// Scan project under Function Test
		dirScanner.removeAllFilters();
		paths = dirScanner.setStartDir(ateDir).addFilter(FindFunctionTestDirFilter.class).startScanning();
		
		if(paths != null && paths.size() == 1)
		{
			ftPath = paths.get(0);
			if( ftPath != null )
			{
				ft = new FunctionTest();
				ts.addFunctionTest(ft);
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
			scanTests(prj);
			ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Project: " + p.getFileName().toString());
		}
		
		ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Total: " + i);
	}
	
	private void scanTests(Project project)
	{
		Path startDir = project.getPath();
		
		dirScanner.removeAllFilters();
		dirScanner.setStartDir(startDir);
		List<Path> versionDir = dirScanner.addFilter(FindVersionDirFilter.class).startScanning();
		
		if(versionDir == null || versionDir.isEmpty())
		{
			Test test = new Test();
			exeScanner.removeAllFilters();
			exeScanner.setStartDir(startDir).setTest(test).startScanning();
			ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Path: " + startDir.toString());
			if( test.getFile() == null)
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "NO EXE File found!");
			}
			else
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_GREEN, "Test: " + test.getFile().getFileName().toString());
			}
			project.addTest(test);
		}
		else
		{
			Iterator<Path> it = versionDir.iterator();
			
			while(it.hasNext())
			{
				Path p = it.next();
				
				Test test = new Test();
				exeScanner.removeAllFilters();
				exeScanner.setStartDir(p).setTest(test).startScanning();
				ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO, "Path: " + p.toString());
				
				if( test.getFile() == null)
				{
					ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "NO EXE File found!");
				}
				else
				{
					ActionManager.INSTANCE.sendAction(IConstants.MSG_GREEN, "Test: " + test.getFile().getFileName().toString());
				}
				project.addTest(test);
			}
		}
	}
}
