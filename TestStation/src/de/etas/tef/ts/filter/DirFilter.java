package de.etas.tef.ts.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirFilter implements IFilter<List<String>>
{
	private final List<String> BLACK_LIST;
	
	public DirFilter(final List<String> blackList)
	{
		this.BLACK_LIST = blackList;
	}

	@Override
	public List<String> filt(final List<String> input)
	{
		if(input == null || input.isEmpty())
		{
			return Collections.emptyList();
		}
		
		if(BLACK_LIST == null || BLACK_LIST.isEmpty())
		{
			return input;
		}
		
		List<String> result = new ArrayList<String>();
		
		for(String in : input)
		{
			boolean exist = false;
			
			for( String bl : BLACK_LIST )
			{
				if( in.toLowerCase().equals(bl.toLowerCase()) )
				{
					exist = true;
				}
			}
			
			if( !exist )
			{
				result.add(in);
			}
		}
		
		return result;
	}
	
}
