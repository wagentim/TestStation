package de.etas.tef.ts.gui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.ts.functions.ActionManager;
import de.etas.tef.ts.functions.IActionListener;
import de.etas.tef.ts.json.AbstractTestProgram;
import de.etas.tef.ts.json.ClusterTest;
import de.etas.tef.ts.json.FunctionTest;
import de.etas.tef.ts.json.Project;
import de.etas.tef.ts.json.Test;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.utils.IConstants;

public class GUITreeNodeHandler implements IActionListener
{
	private final Tree tree;
	
	public GUITreeNodeHandler(final Tree tree)
	{
		this.tree = tree;
	}
	
	public void updateTree(final List<TestStation> testStations)
	{
		tree.removeAll();
		
		Iterator<TestStation> it = testStations.iterator();
		
		while(it.hasNext())
		{
			TestStation ts = it.next();
			
			if(ts == null)
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Test Station is NULL");
				continue;
			}
			
			TreeItem ti = new TreeItem(tree, SWT.NONE);
			ti.setText(ts.getName());
			ti.setData(ts);
			
			createTestStationDetail(ts, ti);
		}
	}
	
	private void createTestStationDetail(TestStation ts, TreeItem ti)
	{
		
		ClusterTest ct = ts.getClusterTest();
		
		if(ct != null)
		{
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Cluster Test");
			t.setData(ct);
			createProjectNode(ct, t);
		}
		
		FunctionTest ft = ts.getFunctionTest();
		if(ft != null)
		{
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Function Test");
			t.setData(ft);
			createProjectNode(ft, t);
		}
	}

	private void createProjectNode(AbstractTestProgram ct, TreeItem t)
	{
		List<Project> prjs = ct.getProjects();
		
		if(prjs == null || prjs.isEmpty())
		{
			ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Project List is NULL or Empty in Test: " + ct.getPath().toString());
			return;
		}
		
		Iterator<Project> it = ct.getProjects().iterator();
		
		while(it.hasNext())
		{
			Project p = it.next();
			
			if(p == null)
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Project is NULL: " + ct.getPath().toString());
				continue;
			}
			
			TreeItem ti = new TreeItem(t, SWT.NONE);
			ti.setText(Paths.get(p.getPath()).getFileName().toString());
			ti.setData(p);
			
			List<Test> tests = p.getTests();
			
			if( tests == null || tests.isEmpty())
			{
				continue;
			}
			else
			{
				Iterator<Test> itTest = tests.iterator();
				
				while(itTest.hasNext())
				{
					Test tst = itTest.next();
					
					Path path = Paths.get(tst.getParentDir());
					TreeItem tItem = new TreeItem(ti, SWT.NONE);
					
					if(path == null)
					{
						path = Paths.get(tst.getPath());
						if(path == null)
						{
							tItem.setText("NOT FOUND");
						}
						else
						{
							tItem.setText(Paths.get(tst.getPath()).getFileName().toString());
						}
					}
					else
					{
						tItem.setText(Paths.get(tst.getParentDir()).getFileName().toString());
						TreeItem tIt = new TreeItem(tItem, SWT.NONE);
						
						path = Paths.get(tst.getPath());
						
						if(path == null)
						{
							tIt.setText("NOT FOUND");
						}
						else
						{
							tIt.setText(Paths.get(tst.getPath()).getFileName().toString());
						}
					}
					
				}
			}
		}
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		
	}
	
}
