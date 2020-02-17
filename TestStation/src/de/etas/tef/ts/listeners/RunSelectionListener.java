package de.etas.tef.ts.listeners;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.etas.tef.ts.condition.ConditionValidator;
import de.etas.tef.ts.functions.AllTestStationScan;
import de.etas.tef.ts.functions.Controller;
import de.etas.tef.ts.json.Driver;
import de.etas.tef.ts.utils.IConstants;

public class RunSelectionListener implements SelectionListener
{
	private ConditionValidator validator = null;
	private final Controller controller;
	
	public RunSelectionListener(final Controller controller)
	{
		this.controller = controller;
	}
	
	public void setValidator(ConditionValidator validator)
	{
		this.validator = validator;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}
	
	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		switch(validator.validate())
		{
			case ConditionValidator.SINGLE_TEST_STATION_SCAN:
				scanSingle();
				break;
			default:
				scanFull();
		}
	}
	
	private Path getScanPath()
	{
		
	}
	
	private void scanFull()
	{
		controller.scanAllStations();
	}

	private void scanSingle()
	{
		
		
	}

	private void runAllTestStationScan()
	{
		String drver = diskCombo.getItem(diskCombo.getSelectionIndex());
		
		@SuppressWarnings("unchecked")
		Driver d = controller.findDriver(drver, (List<Driver>) diskCombo.getData());
		AllTestStationScan sscan = new AllTestStationScan(d.getLetter(), display); 
		sscan.run();
		swithInfoPane(INFO_TABLE);
		controller.setTestStationList(sscan.getTestStationList());
		treeNodeHandler.updateTree(sscan.getTestStationList());
		assignStations();
	}
	
}
