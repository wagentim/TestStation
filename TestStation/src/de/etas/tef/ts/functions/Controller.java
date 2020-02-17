package de.etas.tef.ts.functions;

import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.etas.tef.ts.condition.ConditionValidator;
import de.etas.tef.ts.json.Configure;
import de.etas.tef.ts.json.Driver;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.scanner.DriveScanner;
import de.etas.tef.ts.utils.IConstants;

public class Controller implements IActionListener
{
	private final ImageRegister imageRegister;
	private final ColorPicker colorPicker;
	private Configure configure = null;
	private final ConditionValidator validator;
	private String scanType = IConstants.EMPTY_STRING;
	private Driver selectedDriver = null;
	private TestStation ts = null;
	
	public Controller(final Display display)
	{
		imageRegister = new ImageRegister(display);
		colorPicker = new ColorPicker(display);
		validator = new ConditionValidator();
	}
	
	public Image getImage(int imageIndex)
	{
		return imageRegister.getImage(imageIndex);
	}
	
	public Color getWhite()
	{
		return colorPicker.getColorWhite();
	}

	public Color getBlue()
	{
		return colorPicker.getColorBlue();
	}
	
	public boolean loadConfigure()
	{
		if( null == configure )
		{
			this.configure = new Configure(IConstants.SETTING_FILE);
		}
		
		configure.load();
		
		return true;
	}
	
	public List<TestStation> getTestStations()
	{
		return configure.getStationList();
	}

	public void updateDrivers()
	{
		DriveScanner ds = new DriveScanner();
		List<Driver> drvs = ds.scan();
		ActionManager.INSTANCE.sendAction(IConstants.EVENT_UPDATE_DRIVERS, drvs);
	}

//	private void updateIP()
//	{
//		PCIPScanner scanner = new PCIPScanner();
//		String value = scanner.scan();
//		
//		ActionManager.INSTANCE.sendAction(IConstants.EVENT_UPDATE_IP, value);
//	}
//
//	private void updatePCName()
//	{
//		PCNameScanner scanner = new PCNameScanner();
//		String value = scanner.scan();
//		
//		ActionManager.INSTANCE.sendAction(IConstants.EVENT_UPDATE_PC_NAME, value);
//	}

	public Color getRed()
	{
		return colorPicker.getColorRed();
	}
	
	

	public Color getGreen()
	{
		return colorPicker.getColorDarkGreen();
	}
	
	public Color getGray()
	{
		return colorPicker.getColorGray();
	}

	public void scanTestProgram()
	{
		switch(validator.validateScanModel(ts == null ? IConstants.EMPTY_STRING : ts.getName(), scanType, selectedDriver == null ? IConstants.EMPTY_STRING : selectedDriver.getLetter()))
		{
			case ConditionValidator.ALL_TEST_STATION_SCAN:
				new AllTestStationScan(selectedDriver.getLetter()).run();
				break;
			case ConditionValidator.SINGLE_TEST_STATION_SCAN:
				new SingleTestStationScan(selectedDriver.getLetter()).run();
				break;
		}
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.EVENT_SCAN_TYPE_CHANGED)
		{
			scanType = content.toString();
		}
		else if(type == IConstants.EVENT_DISK_SELECTED)
		{
			selectedDriver = (Driver)content;
		}
		else if(type == IConstants.EVENT_STATION_SELECTED)
		{
			ts = (TestStation)content;
		}
	}
	
}
