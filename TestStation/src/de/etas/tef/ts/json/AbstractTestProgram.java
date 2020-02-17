package de.etas.tef.ts.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTestProgram implements ITestProgram
{
	protected List<Project> projectList = new ArrayList<Project>();
	
	public void addProject(final Project project)
	{
		projectList.add(project);
	}
	
	public void removeAll()
	{
		Iterator<Project> it = projectList.iterator();
		
		while(it.hasNext())
		{
			it.next();
			it.remove();
		}
	}
	
	public List<Project> getProjects()
	{
		return projectList;
	}
	
	public void printProjects()
	{
		Iterator<Project> it = projectList.iterator();
		
		int i = 0;
		
		while(it.hasNext())
		{
			Project p = it.next();
			System.out.println(p.getPath().getFileName().toString());
			i++;
		}
		
		System.out.println("Total: " + i);
	}
	
}
