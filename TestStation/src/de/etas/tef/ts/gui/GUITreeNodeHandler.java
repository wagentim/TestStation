package de.etas.tef.ts.gui;

import java.nio.file.Path;
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
		
		List<ClusterTest> ctList = ts.getClusterTests();
		
		if(ctList != null && !ctList.isEmpty())
		{
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Cluster Test");
			t.setData(ctList.get(0));
			createProjectNode(ctList.get(0), t);
		}
		
		List<FunctionTest> ftList = ts.getFunctionTests();
		if(ftList != null && !ftList.isEmpty())
		{
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Function Test");
			FunctionTest ft = ftList.get(0);
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
			ti.setText(p.getPath().getFileName().toString());
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
					
					Path path = tst.getParentDir();
					TreeItem tItem = new TreeItem(ti, SWT.NONE);
					
					if(path == null)
					{
						path = tst.getFile();
						if(path == null)
						{
							tItem.setText("NOT FOUND");
						}
						else
						{
							tItem.setText(tst.getFile().getFileName().toString());
						}
					}
					else
					{
						tItem.setText(tst.getParentDir().getFileName().toString());
						TreeItem tIt = new TreeItem(tItem, SWT.NONE);
						
						path = tst.getFile();
						
						if(path == null)
						{
							tIt.setText("NOT FOUND");
						}
						else
						{
							tIt.setText(tst.getFile().getFileName().toString());
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
