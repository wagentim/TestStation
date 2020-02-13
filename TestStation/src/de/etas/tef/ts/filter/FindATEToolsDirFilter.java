package de.etas.tef.ts.filter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindATEToolsDirFilter implements IFilter<List<Path>>
{
	private final String ATE_TOOLS_DIR = "ATE-Tools";

	@Override
	public List<Path> filt(List<Path> input)
	{
		Iterator<Path> it = input.iterator();
		
		List<Path> result = new ArrayList<Path>();
		
		while(it.hasNext())
		{
			Path p = it.next();
			
			if( p == null )
			{
				continue;
			}
			
			if( p.getFileName().toString().equalsIgnoreCase(ATE_TOOLS_DIR))
			{
				result.add(p);
			}
		}
		
		return result;
	}
	
}
