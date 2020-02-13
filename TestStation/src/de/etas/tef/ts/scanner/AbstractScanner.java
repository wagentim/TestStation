package de.etas.tef.ts.scanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.etas.tef.ts.filter.IFilter;

public abstract class AbstractScanner<T> implements IScanner<T>
{
	protected final List<Class<? extends IFilter<T>>> filters = new ArrayList<Class<? extends IFilter<T>>>();
	
	public AbstractScanner<T> addFilter(Class<? extends IFilter<T>> filter)
	{
		if( null == filter || filters.contains(filter) )
		{
			// do nothing
		}
		else
		{
			filters.add(filter);
		}
		
		return this;
	}
	
	public AbstractScanner<T> removeAllFilters()
	{
		if(filters == null || filters.isEmpty())
		{
			// do nothing
		}
		else
		{
			Iterator<Class<? extends IFilter<T>>> it = filters.iterator();
			
			while(it.hasNext())
			{
				it.next();
				it.remove();
			}
		}
		
		return this;
	}
	
	public AbstractScanner<T> removeFilter(Class<? extends IFilter<T>> filter)
	{
		Iterator<Class<? extends IFilter<T>>> it = filters.iterator();
		
		while(it.hasNext())
		{
			Class<? extends IFilter<T>> clazz = it.next();
			
			if( clazz == filter)
			{
				it.remove();
			}
		}
		
		return this;
	}
	
	abstract protected T scan();
	
	public T startScanning()
	{
		T result = scan();
		
		Iterator<Class<? extends IFilter<T>>> it = filters.iterator();
		
		while(it.hasNext())
		{
			Class<? extends IFilter<T>> clazz = it.next();
			
			IFilter<T> filter = null;
			try
			{
				filter = clazz.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
			if(filter != null)
			{
				result = filter.filt(result);
			}
		}
		
		return result;
	}
	
	
}
