package de.etas.tef.ts.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Event Dispatcher. {@link IActionListener} must register itself here for Event notification.
 * 
 * Singleton Pattern
 * 
 * @author UIH9FE
 *
 */
public class ActionManager
{
	public static ActionManager INSTANCE = new ActionManager();
	private List<IActionListener> listenerList = null;
	
	public ActionManager()
	{
		listenerList = new ArrayList<IActionListener>();
	}
	
	public void addActionListener(IActionListener listener)
	{
		if( !listenerList.contains(listener) )
		{
			listenerList.add(listener);
		}
	}
	
	public void removeActionListener(IActionListener listener)
	{
		if( !listenerList.isEmpty() )
		{
			listenerList.remove(listener);
		}
	}
	
	public void sendAction(final int type, final Object content)
	{
		if( !listenerList.isEmpty() )
		{
			for( int i = 0; i < listenerList.size(); i++ )
			{
				listenerList.get(i).receivedAction(type, content);
			}
		}
	}
	
	public void close()
	{
		if( null != listenerList && !listenerList.isEmpty() )
		{
			Iterator<IActionListener> it = listenerList.iterator();
			while(it.hasNext())
			{
				it.remove();
			}
		}
	}
}
