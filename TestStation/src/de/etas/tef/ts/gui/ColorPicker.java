package de.etas.tef.ts.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ColorPicker
{
	private final Color BACKGROUND;
	private final Color WHITE;
	private final Color GRAY;
	private final Color BLACK;
	private final Color RED;
	private final Color BLUE;
	private final Color DARK_GREEN;
	private final Color LIGHT_GREEN;
	
	public ColorPicker(final Display display)
	{
		BACKGROUND = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		WHITE = display.getSystemColor(SWT.COLOR_WHITE);
		GRAY = display.getSystemColor(SWT.COLOR_GRAY);
		BLACK = display.getSystemColor(SWT.COLOR_BLACK);
		RED = display.getSystemColor(SWT.COLOR_RED);
		BLUE = display.getSystemColor(SWT.COLOR_BLUE);
		DARK_GREEN = display.getSystemColor(SWT.COLOR_DARK_GREEN);
		LIGHT_GREEN = display.getSystemColor(SWT.COLOR_GREEN);
	}
	
	public Color getColorBackground()
	{
		return BACKGROUND;
	}
	
	public Color getColorWhite()
	{
		return WHITE;
	}
	
	public Color getColorGray()
	{
		return GRAY;
	}
	
	public Color getColorBlack()
	{
		return BLACK;
	}
	
	public Color getColorRed()
	{
		return RED;
	}
	
	public Color getColorBlue()
	{
		return BLUE;
	}
	
	public Color getColorDarkGreen()
	{
		return DARK_GREEN;
	}
	
	public Color getColorGreen()
	{
		return LIGHT_GREEN;
	}
}
