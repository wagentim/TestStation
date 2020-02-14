package de.etas.tef.ts.gui;

import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.etas.tef.ts.json.Configure;
import de.etas.tef.ts.json.Driver;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.scanner.DriveScanner;
import de.etas.tef.ts.scanner.PCIPScanner;
import de.etas.tef.ts.scanner.PCNameScanner;
import de.etas.tef.ts.utils.IConstants;

public class Controller
{
	private final ImageRegister imageRegister;
	private final ColorPicker colorPicker;
	private Configure configure = null;
	
	public Controller(final Display display)
	{
		imageRegister = new ImageRegister(display);
		colorPicker = new ColorPicker(display);
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

	private void updateIP()
	{
		PCIPScanner scanner = new PCIPScanner();
		String value = scanner.scan();
		
		ActionManager.INSTANCE.sendAction(IConstants.EVENT_UPDATE_IP, value);
	}

	private void updatePCName()
	{
		PCNameScanner scanner = new PCNameScanner();
		String value = scanner.scan();
		
		ActionManager.INSTANCE.sendAction(IConstants.EVENT_UPDATE_PC_NAME, value);
	}

	public Color getRed()
	{
		return colorPicker.getColorRed();
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

	public Color getGreen()
	{
		return colorPicker.getColorDarkGreen();
	}
	
	public Color getGray()
	{
		return colorPicker.getColorGray();
	}
	
}
