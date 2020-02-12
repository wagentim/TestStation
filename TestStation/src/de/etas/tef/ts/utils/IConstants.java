package de.etas.tef.ts.utils;

public interface IConstants
{
	public static final String EMPTY_STRING = "";
	
	public static final String TXT_TITLE = "Test Station Helper - TEF (V1.0)";
	public static final String TXT_WINDOW_CLOSE_TITLE = "Exit the Program";
	public static final String TXT_WINDOW_CLOSE_HINT = "Do you really want to exit the Program?";
	public static final String TXT_UNKNOWN = "Unknown";

	public static final Integer IMAGE_TITLE = 0x00;
	public static final Integer IMAGE_EXIT = 0x01;
	public static final Integer IMAGE_ABOUT = 0x02;
	public static final Integer IMAGE_RUN = 0x03;

	public static final int MAIN_SCREEN_WIDTH = 800;
	public static final int MAIN_SCREEN_HEIGHT = 600;

	public static final String MENU_FILE = "File";
	public static final String MENU_EXIT = "Exit";
	public static final String MENU_SW_INFO = "?";
	public static final String MENU_ABOUT = "About";

	public static final int EVENT_UPDATE_PC_NAME = 0x00;
	public static final int EVENT_UPDATE_IP = 0x01;
	public static final int EVENT_UPDATE_DRIVERS = 0x02;
	public static final int EVENT_STATION_SELECTED = 0x03;
	
	public static final String SETTING_FILE = "./settings.json";
	public static final String[] TEST_STATIONS = {
		"avus",
		"baden-baden",
		"bayreuth",
		"bonn",
		"bremen",
		"dresden",
		"duesseldorf",
		"gaildorf",
		"groenland",
		"hamburg",
		"hildesheim",
		"imola",
		"indianapolis",
		"ingolstadt",
		"island",
		"kiel",
		"koeln",
		"konstanz",
		"lindau",
		"ludwigsburg",
		"monaco",
		"regensburg",
		"schauinsland",
	};


}
