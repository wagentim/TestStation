package de.etas.tef.ts.json;

import java.util.ArrayList;
import java.util.List;

public class Project extends AbstractFile
{
	private List<Test> tests;
	
	public Project()
	{
		tests = new ArrayList<Test>();
	}
	
	public void addTest(final Test test)
	{
		tests.add(test);
	}

	public List<Test> getTests()
	{
		return tests;
	}

	public void setTests(List<Test> tests)
	{
		this.tests = tests;
	}
}
