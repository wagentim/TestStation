package de.etas.tef.ts.gui;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.ts.functions.IActionListener;
import de.etas.tef.ts.json.AbstractTestProgram;
import de.etas.tef.ts.json.ClusterTest;
import de.etas.tef.ts.json.FunctionTest;
import de.etas.tef.ts.json.Project;
import de.etas.tef.ts.json.Test;
import de.etas.tef.ts.json.TestStation;

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
				continue;
			}
			
			TreeItem ti = new TreeItem(tree, SWT.NONE);
			ti.setText(ts.getName());
			
			createTestStation(ts, ti);
		}
	}
	
	private void createTestStation(TestStation ts, TreeItem ti)
	{
		Iterator<ClusterTest> itCluster = ts.getClusterTests().iterator();
		
		while(itCluster.hasNext())
		{
			ClusterTest ct = itCluster.next();
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Cluster Test");
			
			createProjectNode(ct, t);
		}
		
		Iterator<FunctionTest> itFunction = ts.getFunctionTests().iterator();
		
		while(itFunction.hasNext())
		{
			FunctionTest ft = itFunction.next();
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Function Test");
			
			createProjectNode(ft, t);
		}
	}

	private void createProjectNode(AbstractTestProgram ct, TreeItem t)
	{
		Iterator<Project> it = ct.getProjects().iterator();
		
		while(it.hasNext())
		{
			Project p = it.next();
			
			TreeItem ti = new TreeItem(t, SWT.NONE);
			ti.setText(p.getPath().getFileName().toString());
			
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
					ti = new TreeItem(ti, SWT.NONE);
					
					if(path == null)
					{
						
						path = tst.getFile();
						
						if(path == null)
						{
							ti.setText("NOT FOUND");
						}
						else
						{
							ti.setText(tst.getFile().getFileName().toString());
						}
					}
					else
					{
						ti.setText(tst.getParentDir().getFileName().toString());
						ti = new TreeItem(ti, SWT.NONE);
						
						path = tst.getFile();
						
						if(path == null)
						{
							ti.setText("NOT FOUND");
						}
						else
						{
							ti.setText(tst.getFile().getFileName().toString());
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
