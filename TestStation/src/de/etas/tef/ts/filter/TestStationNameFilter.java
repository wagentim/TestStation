package de.etas.tef.ts.filter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestStationNameFilter implements IFilter<List<Path>>
{
	
	private static final String[] IGNORE_STATION = {"_", "fe13606", "ts-man", "dummy"};

	@Override
	public List<Path> filt(List<Path> input)
	{
		List<Path> result = new ArrayList<Path>();
		List<String> ignore = Arrays.asList(IGNORE_STATION);
		
		Iterator<Path> it = input.iterator();
		
		while(it.hasNext())
		{
			Path p = it.next();
			
			String name = p.getFileName().toString().toLowerCase();
			
			Iterator<String> sit = ignore.iterator();
			
			boolean contain = false;
			
			while(sit.hasNext())
			{
				String igr = sit.next();
				
				if(name.contains(igr))
				{
					contain = true;
				}
			}
			
			if(!contain)
			{
				result.add(p);
			}
		}
		
		return result;
	}
	
}
