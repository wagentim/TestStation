package de.etas.tef.ts.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import de.etas.tef.ts.functions.ActionManager;
import de.etas.tef.ts.functions.AllTestStationScan;
import de.etas.tef.ts.functions.Controller;
import de.etas.tef.ts.functions.IActionListener;
import de.etas.tef.ts.json.Driver;
import de.etas.tef.ts.json.TestStation;
import de.etas.tef.ts.listeners.DiskSelectionListener;
import de.etas.tef.ts.listeners.ScanTypeSelectionListener;
import de.etas.tef.ts.listeners.StationSelectionListener;
import de.etas.tef.ts.utils.IConstants;

public class MainWindow implements IActionListener
{
	private Label dateLabel;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	private ProgressBar progressbar = null;
	private Label statusLabel;
	private final Controller controller;
	private final Display display;
	
	private Label pcNameValue;
	private Label ipNameValue;
	private Label tsName;
	private Combo diskCombo;
	private SashForm sf;
	private StyledText txtInfoBlock;
	private Tree tree;
	private Combo stationList;
	private Combo scanTypeCombo;
	private Button run;
	
	private static final int INFO_TABLE = 0x00;
	private static final int INFO_TEXT = 0x01;
	
	private GUITreeNodeHandler treeNodeHandler = null;
	
	public MainWindow(Display display, Controller controller)
	{
		ActionManager.INSTANCE.addActionListener(this);
		this.controller = controller;
		this.display = display;
		
		Shell shell = new Shell(display);
		shell.setText(IConstants.TXT_TITLE);
		shell.setImage(controller.getImage(IConstants.IMAGE_TITLE));
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		shell.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		shell.setLayoutData(gd);
		
		shell.addShellListener(new ShellAdapter()
		{
			
			@Override
			public void shellClosed(ShellEvent event)
			{
				MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				mb.setText(IConstants.TXT_WINDOW_CLOSE_TITLE);
				mb.setMessage(IConstants.TXT_WINDOW_CLOSE_HINT);

				event.doit = mb.open() == SWT.YES;
				
			}
		});
		
		initMainScreen(shell);
		initMenu(shell);
		initTopComposite(shell);
		initShellContent(shell);
		initStatusBar(shell);
		
		Runnable timer = new Runnable()
		{
			public void run()
			{
				dateLabel.setText(" " + sdf.format(new Date()) + " ");
				display.timerExec(1000, this);
			}
		};
		display.timerExec(1000, timer);
		
		shell.open();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void initTopComposite(Shell shell)
	{
		Composite topComposite = new Composite(shell, SWT.BORDER);
		GridLayout layout = new GridLayout(7, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		topComposite.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		topComposite.setLayoutData(gd);
		
		initTestStationName(topComposite);
		new Label(topComposite, SWT.SEPARATOR);
		
		initTSSelection(topComposite);
		new Label(topComposite, SWT.SEPARATOR);
		
		initScanOptions(topComposite);
	}

	private void initScanOptions(Composite topComposite)
	{
		Composite scanOptions = new Composite(topComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		scanOptions.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		scanOptions.setLayoutData(gd);
		
		Label scanType = new Label(scanOptions, SWT.NONE);
		FontData[] fD = scanType.getFont().getFontData();
		fD[0].setHeight(10);
		scanType.setFont( new Font(display,fD[0]));
		scanType.setText("Scan Type:");
		gd = new GridData();
		gd.heightHint = 25;
		scanType.setLayoutData(gd);
		
		scanTypeCombo = new Combo(scanOptions, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		scanTypeCombo.add("");
		scanTypeCombo.add(IConstants.TXT_SCAN_TYPE_TEST_PROGRAM);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		scanTypeCombo.setLayoutData(gd);
		scanTypeCombo.addSelectionListener(new ScanTypeSelectionListener());
		
		Label scanDisk = new Label(scanOptions, SWT.NONE);
		fD = scanDisk.getFont().getFontData();
		fD[0].setHeight(10);
		scanDisk.setFont( new Font(display,fD[0]));
		scanDisk.setText("Scan Disk:");
		gd = new GridData();
		gd.heightHint = 25;
		scanDisk.setLayoutData(gd);
		
		diskCombo = new Combo(scanOptions, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		diskCombo.setLayoutData(gd);
		diskCombo.setEnabled(false);
		diskCombo.addSelectionListener(new DiskSelectionListener(controller));
		
		run = new Button(scanOptions, SWT.PUSH);
		run.setImage(controller.getImage(IConstants.IMAGE_RUN));
		run.setEnabled(false);
		run.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				// check if there is test station selected
				
				int index = stationList.getSelectionIndex();
				
				if( index == -1)
				{
					logInfo("Scan All Test Stations...");
					runAllTestStationScan();
				}
				else
				{
					String selStation = stationList.getItem(stationList.getSelectionIndex());
					if(selStation == null || selStation.equals(IConstants.EMPTY_STRING))
					{
						logInfo("Scan All Test Stations...");
						runAllTestStationScan();
					}
					else
					{
						logInfo("Scan Station: " + selStation);
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				
			}
		});
	}
	
	private void runAllTestStationScan()
	{
		String drver = diskCombo.getItem(diskCombo.getSelectionIndex());
		
		@SuppressWarnings("unchecked")
		Driver d = controller.findDriver(drver, (List<Driver>) diskCombo.getData());
		AllTestStationScan sscan = new AllTestStationScan(d.getLetter(), display); 
		sscan.run();
		swithInfoPane(INFO_TABLE);
		treeNodeHandler.updateTree(sscan.getTestStationList());
	}
	
	private void initTSSelection(Composite topComposite)
	{
		Composite tsSelection = new Composite(topComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		tsSelection.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 200;
		tsSelection.setLayoutData(gd);
		
		Label station = new Label(tsSelection, SWT.NONE);
		FontData[] fD = station.getFont().getFontData();
		fD[0].setHeight(10);
		station.setFont( new Font(display,fD[0]));
		station.setText("Station:");
		gd = new GridData();
		gd.heightHint = 25;
		station.setLayoutData(gd);
		
		stationList = new Combo(tsSelection, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		stationList.setLayoutData(gd);
		
		assignStations();
		
		stationList.addSelectionListener(new StationSelectionListener());
	}

	private void assignStations()
	{
		List<TestStation> stations = controller.getTestStations();
		
		stationList.add(IConstants.EMPTY_STRING);
		
		for(TestStation ts : stations)
		{
			stationList.add(ts.getName());
		}
		
		stationList.setData(stations);
	}

	private void initTSInfo(Composite topComposite)
	{
		Composite tsInfo = new Composite(topComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		tsInfo.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 200;
		tsInfo.setLayoutData(gd);
		
		Label pcName = new Label(tsInfo, SWT.NONE);
		FontData[] fD = pcName.getFont().getFontData();
		fD[0].setHeight(10);
		pcName.setFont( new Font(display,fD[0]));
		pcName.setText("Name:");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		pcName.setLayoutData(gd);
		
		pcNameValue = new Label(tsInfo, SWT.NONE);
		fD = pcNameValue.getFont().getFontData();
		fD[0].setHeight(10);
		pcNameValue.setFont( new Font(display,fD[0]));
		pcNameValue.setText(IConstants.TXT_UNKNOWN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		pcNameValue.setLayoutData(gd);
		
		Label ipName = new Label(tsInfo, SWT.NONE);
		fD = pcName.getFont().getFontData();
		fD[0].setHeight(10);
		ipName.setFont( new Font(display,fD[0]));
		ipName.setText("IP:");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		ipName.setLayoutData(gd);
		
		ipNameValue = new Label(tsInfo, SWT.NONE);
		fD = pcNameValue.getFont().getFontData();
		fD[0].setHeight(10);
		ipNameValue.setFont( new Font(display,fD[0]));
		ipNameValue.setText(IConstants.TXT_UNKNOWN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		ipNameValue.setLayoutData(gd);
		
	}

	private void initTestStationName(Composite topComposite)
	{
		Composite stationName = new Composite(topComposite, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		stationName.setLayout(layout);
		
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 150;
		stationName.setLayoutData(gd);
		
		Label tsTitle = new Label(stationName, SWT.NONE);
		FontData[] fD = tsTitle.getFont().getFontData();
		fD[0].setHeight(18);
		fD[0].setStyle(SWT.BOLD);
		tsTitle.setFont( new Font(display,fD[0]));
		tsTitle.setText("Test Station");
		tsTitle.setForeground(controller.getBlue());
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 150;
		tsTitle.setLayoutData(gd);
		
		tsName = new Label(stationName, SWT.NONE);
		fD = tsTitle.getFont().getFontData();
		fD[0].setHeight(12);
		tsName.setFont( new Font(display,fD[0]));
		tsName.setText(IConstants.TXT_UNKNOWN);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 150;
		tsName.setLayoutData(gd);
	}

	private void initShellContent(Shell shell)
	{
		Composite mainComposite = new Composite(shell, SWT.BORDER);
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginLeft = layout.marginRight = layout.marginBottom = 0;
		mainComposite.setLayout(layout);
		mainComposite.setBackground(controller.getWhite());
		
		GridData gd = new GridData(GridData.FILL_BOTH);
		mainComposite.setLayoutData(gd);
		
		sf = new SashForm(mainComposite, SWT.VERTICAL); 
		gd = new GridData(GridData.FILL_BOTH);
		sf.setLayoutData(gd);
		
		tree = new Tree(sf, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gd);
		tree.setHeaderVisible(false);
		tree.setLinesVisible(true);
		
		treeNodeHandler = new GUITreeNodeHandler(tree);
		
		txtInfoBlock = new StyledText(sf, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		txtInfoBlock.setLayoutData(gd);
		txtInfoBlock.setEditable(false);
		txtInfoBlock.setVisible(false);
		
		sf.setWeights(new int[]{1, 0});
		
	}

	private void initMenu(Shell shell)
	{
		Menu menuBar = new Menu(shell, SWT.BAR);
		
		// menu "File"
		
	    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText(IConstants.MENU_FILE);

	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);
	    
	    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText(IConstants.MENU_EXIT);
	    fileExitItem.setImage(controller.getImage(IConstants.IMAGE_EXIT));
//	    fileExitItem.addSelectionListener(new MenuExitSelectionListener(shell));
	    
	    // menu "About"
	    
	    MenuItem aboutMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    aboutMenuHeader.setText(IConstants.MENU_SW_INFO);

	    Menu aboutMenu = new Menu(shell, SWT.DROP_DOWN);
	    aboutMenuHeader.setMenu(aboutMenu);
	    
	    MenuItem aboutItem = new MenuItem(aboutMenu, SWT.PUSH);
	    aboutItem.setText(IConstants.MENU_ABOUT);
	    aboutItem.setImage(controller.getImage(IConstants.IMAGE_ABOUT));
//	    aboutItem.addSelectionListener(new AboutSelectionListener(controller, shell));
	    
	    shell.setMenuBar(menuBar);
	}

	private void initMainScreen(Shell shell)
	{
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		shell.pack();
		shell.setBounds((Math.abs(area.width - IConstants.MAIN_SCREEN_WIDTH)) / 2,
				Math.abs((area.height - IConstants.MAIN_SCREEN_HEIGHT)) / 2, IConstants.MAIN_SCREEN_WIDTH,
				IConstants.MAIN_SCREEN_HEIGHT);

		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginBottom = 10;
		shell.setLayout(layout);
	}
	
	private void initStatusBar(Shell shell)
	{
		
		Composite statusbar = new Composite(shell, SWT.BORDER);
		
		GridLayout layout = new GridLayout(5, false);
		layout.marginBottom = layout.marginHeight = layout.marginLeft = layout.marginRight = layout.marginTop = layout.marginWidth = 0;
		statusbar.setLayout(layout);
		
		GridData cgd = new GridData(GridData.FILL_HORIZONTAL);
		cgd.horizontalIndent = cgd.verticalIndent = cgd.horizontalSpan = cgd.verticalSpan = 0;
		cgd.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
		cgd.heightHint = 17;
		statusbar.setLayoutData(cgd);

        dateLabel = new Label(statusbar, SWT.BOLD);
        dateLabel.setBounds(0, 0, 300, 16);
        dateLabel.setText(" "+sdf.format(new Date())+" ");
        
        Label sep = new Label(statusbar, SWT.NONE);
        sep.setText("|");
        sep.setBounds(310, 0, 1, 18);
        
        progressbar = new ProgressBar(statusbar, SWT.SMOOTH);
        progressbar.setMinimum(0);
        progressbar.setMaximum(100);
        progressbar.setVisible(false);
        
        sep = new Label(statusbar, SWT.NONE);
        sep.setText("|");
        sep.setBounds(310, 0, 1, 18);
        sep.setVisible(false);
        
        statusLabel = new Label(statusbar, SWT.NONE);
        cgd = new GridData(GridData.FILL_HORIZONTAL);
        statusLabel.setLayoutData(cgd);
        statusLabel.setAlignment(SWT.CENTER);
        statusLabel.setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void receivedAction(int type, Object content)
	{
		if( type == IConstants.EVENT_UPDATE_PC_NAME )
		{
//			updatePCName((String)content);
		}
		else if( type == IConstants.EVENT_UPDATE_IP )
		{
//			updateIP((String)content);
		}
		else if( type == IConstants.EVENT_UPDATE_DRIVERS )
		{
			updateDrivers((List<Driver>)content);
		}
		else if( type == IConstants.EVENT_STATION_SELECTED )
		{
			TestStation ts = (TestStation)content;
			if( ts == null )
			{
				return;
			}
			
			updateStationName(ts.getName());
			setScanTypeEnable(true);
		}
		else if( type == IConstants.EVENT_SCAN_TYPE_TEST_PROGRAM_SELECTED)
		{
			logInfo("Scanning local Drivers...");
			setDiskDriversEnable(true);
		}
		else if( type == IConstants.MSG_INFO )
		{
			logInfo((String)content);
		}
		else if( type == IConstants.MSG_ERR )
		{
			logError((String)content);
		}
		else if( type == IConstants.MSG_GREEN )
		{
			logGreen((String)content);
		}
		else if( type == IConstants.MSG_GREY )
		{
			logGrey((String)content);
		}
		else if( type == IConstants.EVENT_SCAN_TYPE_NULL_SELECTED)
		{
			setDiskDriversEnable(false);
			setRunButtonEnable(false);
		}
		else if (type == IConstants.EVENT_DISK_SELECTED)
		{
			setRunButtonEnable(content == null ? false : true);
		}
	}

	private void logGreen(String text)
	{
		String txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";

		StyleRange sr = new StyleRange();
		sr.start = txtInfoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = controller.getGreen();
		sr.fontStyle = SWT.NORMAL;
		txtInfoBlock.append(txt);
		txtInfoBlock.setStyleRange(sr);
		moveToLastLine();
	}

	private void setRunButtonEnable(boolean b)
	{
		run.setEnabled(b);
	}

	private void setDiskDriversEnable(boolean b)
	{
		diskCombo.setEnabled(b);
		diskCombo.removeAll();
		swithInfoPane(INFO_TEXT);

		if(b)
		{
			display.asyncExec(new Runnable()
			{
				
				@Override
				public void run()
				{
					controller.updateDrivers();
					logInfo("Scan local Drivers finished!");
				}
			});
		}
	}
	
	private void swithInfoPane(int index)
	{
		switch(index)
		{
			case INFO_TABLE:
				tree.setVisible(true);
				txtInfoBlock.setVisible(false);
				sf.setWeights(new int[]{1, 0});
				break;
			case INFO_TEXT:
				tree.setVisible(false);
				txtInfoBlock.setVisible(true);
				sf.setWeights(new int[]{0, 1});
				break;
		}
	}

	private void setScanTypeEnable(boolean b)
	{
		scanTypeCombo.setEnabled(b);
	}

	private void updateDrivers(List<Driver> content)
	{
		diskCombo.removeAll();
		
		diskCombo.add(IConstants.EMPTY_STRING);
		
		for(Driver d : content)
		{
			String name = d.getName();
			
			if(name.isEmpty())
			{
				name = "(" + d.getLetter().substring(0, d.getLetter().length()-1) + ") " + d.getType();
			}
			diskCombo.add(name);
		}
		
		diskCombo.setData(content);
	}

	private void updateIP(String content)
	{
		if(content == null || content.isEmpty())
		{
			content = IConstants.TXT_UNKNOWN;
		}
		ipNameValue.setText(content);
	}

	private void updatePCName(String content)
	{
		if(content == null || content.isEmpty())
		{
			content = IConstants.TXT_UNKNOWN;
		}
		pcNameValue.setText(content);
	}
	
	private void updateStationName(String content)
	{
		if(content == null || content.isEmpty())
		{
			content = IConstants.TXT_UNKNOWN;
		}
		tsName.setText(content);
	}
	
	private void logError(String text)
	{
		String txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[ERROR] " + text + "\n";

		StyleRange sr = new StyleRange();
		sr.start = txtInfoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = controller.getRed();
		sr.fontStyle = SWT.NORMAL;
		txtInfoBlock.append(txt);
		txtInfoBlock.setStyleRange(sr);
		moveToLastLine();
	}

	private void logInfo(String text)
	{
		String txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";
		
		StyleRange sr = new StyleRange();
		sr.start = txtInfoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = controller.getBlue();
		sr.fontStyle = SWT.NORMAL;
		txtInfoBlock.append(txt);
		txtInfoBlock.setStyleRange(sr);
		moveToLastLine();
	}
	
	private void logGrey(String text)
	{
		String txt = "[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "[INFO] " + text + "\n";
		
		StyleRange sr = new StyleRange();
		sr.start = txtInfoBlock.getText().length();
		sr.length = txt.length();
		sr.foreground = controller.getGray();
		sr.fontStyle = SWT.NORMAL;
		txtInfoBlock.append(txt);
		txtInfoBlock.setStyleRange(sr);
		moveToLastLine();
	}
	
	private void moveToLastLine()
	{
		txtInfoBlock.setTopIndex(txtInfoBlock.getLineCount());
	}
}
