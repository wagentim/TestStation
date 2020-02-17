package de.etas.tef.ts.gui;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.ts.functions.Controller;


public class Starter
{
	public static void main(String[] args)
	{
		Display display = new Display();
		Controller controller = new Controller(display);
		controller.loadConfigure();
		new MainWindow(display, controller);
	}
}
