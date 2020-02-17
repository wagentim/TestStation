package de.etas.tef.ts.filter;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class FindVersionDirFilter implements IFilter<List<Path>>
{
	@Override
	public List<Path> filt(List<Path> input)
	{
		
		Iterator<Path> it = input.iterator();
		
		while(it.hasNext())
		{
			Path p = it.next();
			String name = p.getFileName().toString();
			if(!name.toLowerCase().startsWith("v"))
			{
				it.remove();
			}
		}
		
		return input;
	}
}
