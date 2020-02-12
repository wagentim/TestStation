package de.etas.tef.ts.gui;

import org.eclipse.swt.widgets.Display;


public class Starter
{
	public static void main(String[] args)
	{
		Display display = new Display();
		Controller controller = new Controller(display);
		boolean success = controller.loadConfigure();
		new MainWindow(display, controller);
	}
}
