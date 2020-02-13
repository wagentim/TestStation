package de.etas.tef.ts.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.etas.tef.ts.utils.IConstants;

public final class Configure
{
	private static final Logger logger = LoggerFactory.getLogger(Configure.class);
	private final String configFile;
	
	private String name = "Setting";
	private List<TestStation> stationList = Collections.emptyList();
	private boolean programStarted = false;
	
	public Configure(final String settingFile)
	{
		this.configFile = settingFile;
	}
	
	public void writeJson()
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();
		try
		{
			FileWriter writer = new FileWriter(configFile);
			writer.write(gson.toJson(this));
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void readJson()
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			Configure configure = gson.fromJson(reader, Configure.class);
			assignValues(configure);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private void assignValues(Configure configure)
	{
		if( configure == null )
		{
			logger.error("new assigned configure object is null");
			return;
		}
		setName(configure.getName());
		setStationList(configure.getStationList());
		
		configure = null;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isProgramStarted()
	{
		return programStarted;
	}

	public void setProgramStarted(boolean programStarted)
	{
		this.programStarted = programStarted;
	}

	public void load()
	{
		// check setting file
		Path settingFile = Paths.get(IConstants.SETTING_FILE);
		
		if(!Files.exists(settingFile))
		{
			createNewConfigFile();
		}
		else
		{
			readJson();
		}
	}

	private void createNewConfigFile()
	{
		this.setName(this.getName());
		this.writeJson();
	}

	public List<TestStation> getStationList()
	{
		return stationList;
	}

	public void setStationList(List<TestStation> stationList)
	{
		this.stationList = stationList;
	}
}
