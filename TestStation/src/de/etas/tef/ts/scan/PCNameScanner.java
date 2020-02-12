package de.etas.tef.ts.scan;

import java.util.Map;

import de.etas.tef.ts.utils.IConstants;

public class PCNameScanner implements IScanner<String>
{
	private static final String COMPUTER_NAME = "COMPUTERNAME";
	private static final String HOST_NAME = "HOSTNAME";
	
	@Override
	public String scan(String input)
	{
		Map<String, String> env = System.getenv();
		
	    if (env.containsKey(COMPUTER_NAME))
	        return env.get(COMPUTER_NAME).trim();
	    else if (env.containsKey(HOST_NAME))
	    	return env.get(HOST_NAME).trim();
	    else
	    	return IConstants.EMPTY_STRING;
	}
	
}
