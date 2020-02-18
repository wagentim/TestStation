package de.etas.tef.ts.gui;

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
		ActionManager.INSTANCE.addActionListener(this);
	}
	
	public void updateStationList(final List<TestStation> stations, String text)
	{
		Iterator<TestStation> it = stations.iterator();
		
		tree.setData(stations);
		
		while(it.hasNext())
		{
			TestStation ts = it.next();
			
			if(ts == null)
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_ERR, "Test Station is NULL");
				continue;
			}
			
			updateStation(ts, text);
		}
		
	}
	
	private boolean isProjectAvailableInClusterTest(TestStation ts, String text)
	{
		if(text == null || text.isEmpty())
		{
			return true;
		}
		
		ClusterTest ct = ts.getClusterTest();
		
		if(ct == null)
		{
			return true;
		}
		
		for(Project p : ts.getClusterTest().getProjectList())
		{
			if(Paths.get(p.getPath()).getFileName().toString().toLowerCase().contains(text.toLowerCase()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isProjectAvailableInFunctionTest(TestStation ts, String text)
	{
		if(text == null || text.isEmpty())
		{
			return true;
		}
		
		FunctionTest ft = ts.getFunctionTest();
		
		if(ft == null)
		{
			return true;
		}
		
		for(Project p : ts.getFunctionTest().getProjectList())
		{
			if(Paths.get(p.getPath()).getFileName().toString().toLowerCase().contains(text.toLowerCase()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void updateStation(final TestStation station, String text)
	{
		if(!isProjectAvailableInClusterTest(station, text) && isProjectAvailableInFunctionTest(station, text))
		{
			return;
		}
		
		TreeItem ti = new TreeItem(tree, SWT.NONE);
		ti.setText(station.getName());
		ti.setData(station);
		
		createTestStationDetail(station, ti, text);
		
		ti.setExpanded(true);
	}
	
	private void createTestStationDetail(TestStation ts, TreeItem ti, String text)
	{
		
		ClusterTest ct = ts.getClusterTest();
		
		if(ct != null && isProjectAvailableInClusterTest(ts, text))
		{
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Cluster Test");
			t.setData(ct);
			createProjectNode(ct, t, text);
			t.setExpanded(true);
		}
		
		FunctionTest ft = ts.getFunctionTest();
		if(ft != null && isProjectAvailableInFunctionTest(ts, text))
		{
			TreeItem t = new TreeItem(ti, SWT.NONE);
			t.setText("Function Test");
			t.setData(ft);
			createProjectNode(ft, t, text);
			t.setExpanded(true);
		}
	}

	private void createProjectNode(AbstractTestProgram ct, TreeItem t, String text)
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
			
			if(text != null && !text.isEmpty() && !Paths.get(p.getPath()).getFileName().toString().toLowerCase().contains(text.toLowerCase()))
			{
				ActionManager.INSTANCE.sendAction(IConstants.MSG_INFO_BLUE, "Skip Project: " + p.getPath());
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
					
					String sParentPath = tst.getParentDir();
					TreeItem tItem = new TreeItem(ti, SWT.NONE);
					
					if(sParentPath == null || sParentPath.isEmpty())
					{
						String sPath = tst.getPath();
						
						if(sPath == null || sPath.isEmpty())
						{
							tItem.setText("NOT FOUND");
						}
						else
						{
							tItem.setText(Paths.get(sPath).getFileName().toString());
						}
					}
					else
					{
						tItem.setText(Paths.get(tst.getParentDir()).getFileName().toString());
						TreeItem tIt = new TreeItem(tItem, SWT.NONE);
						
						String sPath = tst.getPath();
						
						if(sPath == null || sPath.isEmpty())
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void receivedAction(int type, Object content)
	{
		if( type == IConstants.EVENT_UPDATE_SEARCH )
		{
			String searchText = content.toString();
			
			List<TestStation> stations = (List<TestStation>) tree.getData();
			
			if(tree.getItemCount() <= 0)
			{
				return;
			}
			
			if(stations == null || stations.isEmpty())
			{
				TestStation ts = (TestStation) tree.getItem(0).getData();
				
				if(ts != null)
				{
					tree.removeAll();
					updateStation(ts, searchText);
				}
			}
			else
			{
				tree.removeAll();
				updateStationList(stations, searchText);
			}
		}
	}
	
}
