package de.etas.tef.ts.condition;

import de.etas.tef.ts.utils.IConstants;

public final class ConditionValidator 
{
	public static final int UNKNOWN = 0x00;
	public static final int SINGLE_TEST_STATION_SCAN = 0x01;
	public static final int ALL_TEST_STATION_SCAN = 0x02;
	
	public int validateScanModel(String selectedTestStation, String selectedScanType, String selectedDriver)
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
	
}
