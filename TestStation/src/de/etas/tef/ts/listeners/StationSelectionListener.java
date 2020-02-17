package de.etas.tef.ts.listeners;

import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import de.etas.tef.ts.functions.ActionManager;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.utils.IConstants;

public class StationSelectionListener implements SelectionListener
{

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void widgetSelected(SelectionEvent event)
	{
		Combo stationList = ((Combo)(event.getSource()));
		
		String name = stationList.getItem(stationList.getSelectionIndex());
		
		ActionManager.INSTANCE.sendAction(IConstants.EVENT_STATION_SELECTED, getStation(name, (List<TestStation>) stationList.getData()));
	}
	
	private TestStation getStation(String name, List<TestStation> stations)
	{
		for(TestStation ts : stations)
		{
			if(name.toLowerCase().equals(ts.getName().toLowerCase()))
			{
				return ts;
			}
		}
		
		return null;
	}
	
}
