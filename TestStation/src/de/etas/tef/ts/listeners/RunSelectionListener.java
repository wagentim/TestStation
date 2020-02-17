package de.etas.tef.ts.listeners;


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.etas.tef.ts.functions.Controller;

public class RunSelectionListener implements SelectionListener
{
	private final Controller controller;
	
	public RunSelectionListener(final Controller controller)
	{
		this.controller = controller;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}
	
	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		controller.scanTestProgram();
	}
}
