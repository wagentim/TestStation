package de.etas.tef.ts.db;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import de.etas.tef.ts.json.TestStation;

public class AccessHandler
{
	private Database db;
	private static final String dbFile = "./TSM_Master.mdb";
	
	public List<TestStation> createTestStations()
	{
		try
		{
			db = DatabaseBuilder.open(new File(dbFile));
			Set<String> names = db.getTableNames();
			for(String s:names)
			{
				System.out.println(s);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static void main(String[] args)
	{
		AccessHandler handler = new AccessHandler();
		handler.createTestStations();
	}
}
