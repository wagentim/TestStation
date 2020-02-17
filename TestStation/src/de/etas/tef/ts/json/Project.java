package de.etas.tef.ts.json;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Project
{
	private final List<Test> tests;
	
	public Project()
	{
		tests = new ArrayList<Test>();
	}
	
	private Path path = null;

	public Path getPath()
	{
		return path;
	}

	public void setPath(Path path)
	{
		this.path = path;
	}
	
	public void addTest(final Test test)
	{
		tests.add(test);
	}
}
