package de.etas.tef.ts.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import de.etas.tef.ts.gui.ActionManager;
import de.etas.tef.ts.utils.IConstants;

public class ScanTypeSelectionListener implements SelectionListener
{
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}
	
	@Override
	public void widgetSelected(SelectionEvent event)
	{
		Combo scanType = (Combo) event.getSource();
		
		String txtScanType = scanType.getItem(scanType.getSelectionIndex());
		
		if(txtScanType.equals(IConstants.TXT_SCAN_TYPE_TEST_PROGRAM))
		{
			ActionManager.INSTANCE.sendAction(IConstants.EVENT_SCAN_TEST_PROGRAM_SELECTED, IConstants.EMPTY_STRING);
		}
	}
	
}
