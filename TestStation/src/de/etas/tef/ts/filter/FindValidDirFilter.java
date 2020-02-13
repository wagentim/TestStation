package de.etas.tef.ts.filter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindValidDirFilter implements IFilter<List<Path>>
{
	private final String VALID_DIR = "_Valid";

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
			
			if( p.getFileName().toString().equalsIgnoreCase(VALID_DIR))
			{
				result.add(p);
			}
		}
		
		return result;
	}
	
}
