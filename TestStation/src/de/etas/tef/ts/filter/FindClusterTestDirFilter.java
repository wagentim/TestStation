package de.etas.tef.ts.filter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindClusterTestDirFilter implements IFilter<List<Path>>
{
	private final String CLUSTER_TEST = "ClusterTest";

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
			
			if( p.getFileName().toString().equalsIgnoreCase(CLUSTER_TEST))
			{
				result.add(p);
			}
		}
		
		return result;
	}
	
}
