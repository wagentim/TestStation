package de.etas.tef.ts.condition;

import de.etas.tef.ts.utils.IConstants;

public final class ConditionValidator 
{
	public static final int UNKNOWN = 0x00;
	public static final int SINGLE_TEST_STATION_SCAN = 0x01;
	public static final int ALL_TEST_STATION_SCAN = 0x02;
	
	private String selectedTestStation = IConstants.EMPTY_STRING;
	private String selectedScanType = IConstants.EMPTY_STRING;
	private String selectedDriver = IConstants.EMPTY_STRING;
	
	
	public int validate()
	{
		if(!selectedTestStation.equals(IConstants.EMPTY_STRING) &&
				!selectedDriver.equals(IConstants.EMPTY_STRING) &&
				selectedScanType.equalsIgnoreCase(IConstants.TXT_SCAN_TYPE_TEST_PROGRAM))
		{
			return SINGLE_TEST_STATION_SCAN;
		}
		else if(selectedTestStation.equals(IConstants.EMPTY_STRING) &&
				!selectedDriver.equals(IConstants.EMPTY_STRING) &&
				selectedScanType.equalsIgnoreCase(IConstants.TXT_SCAN_TYPE_TEST_PROGRAM))
		{
			return ALL_TEST_STATION_SCAN;
		}
		
		return UNKNOWN;
	}


	public String getSelectedTestStation()
	{
		return selectedTestStation;
	}


	public void setSelectedTestStation(String selectedTestStation)
	{
		this.selectedTestStation = selectedTestStation;
	}


	public String getSelectedScanType()
	{
		return selectedScanType;
	}


	public void setSelectedScanType(String selectedScanType)
	{
		this.selectedScanType = selectedScanType;
	}


	public String getSelectedDriver()
	{
		return selectedDriver;
	}


	public void setSelectedDriver(String selectedDriver)
	{
		this.selectedDriver = selectedDriver;
	}
	
}
