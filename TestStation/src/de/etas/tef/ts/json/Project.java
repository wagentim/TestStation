package de.etas.tef.ts.json;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Project
{
	private final List<Test> tests;
	private Path path = null;
	
	public Project()
	{
		tests = new ArrayList<Test>();
	}
	
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
	
	public List<Test> getTests()
	{
		return tests;
	}
}
