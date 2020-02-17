package de.etas.tef.ts.listeners;

import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import de.etas.tef.ts.functions.ActionManager;
import de.etas.tef.ts.json.Driver;
import de.etas.tef.ts.utils.IConstants;

public class DiskSelectionListener implements SelectionListener
{
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void widgetSelected(SelectionEvent event)
	{
		Combo disks = (Combo)event.getSource();
		
		String name = disks.getItem(disks.getSelectionIndex());
		
		Driver d = null;
		
		if( name != null && !name.equals(IConstants.EMPTY_STRING))
		{
			d = findDriver(name, (List<Driver>) disks.getData());
		}

		ActionManager.INSTANCE.sendAction(IConstants.EVENT_DISK_SELECTED, d);
	}

	public Driver findDriver(String name, List<Driver> drivers)
	{
		for(Driver d : drivers)
		{
			String n = d.getName();
			
			if(n == null || n.equals(IConstants.EMPTY_STRING))
			{
				String type = d.getType();
				
				if(name.contains(type))
				{
					return d;
				}
			}
			else if (n.equals(name))
			{
				return d;
			}
		}
		
		return null;
	}
	
}
