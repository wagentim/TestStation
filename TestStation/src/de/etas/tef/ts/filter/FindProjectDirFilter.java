package de.etas.tef.ts.filter;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class FindProjectDirFilter implements IFilter<List<Path>>
{

	@Override
	public List<Path> filt(List<Path> input)
	{
		Iterator<Path> it = input.iterator();
		
		while(it.hasNext())
		{
			Path p = it.next();
			
			if( p == null )
			{
				continue;
			}
			
			String currName = p.getFileName().toString().toLowerCase(); 
			
			if(!currName.startsWith("es") || !Character.isDigit(currName.charAt(2)))
			{
				it.remove();
			}
		}
		
		return input;
	}
	
}
