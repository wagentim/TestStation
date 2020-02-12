package de.etas.tef.ts.scan;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.etas.tef.ts.utils.IConstants;

public class IPScanner implements IScanner<String>
{
	
	@Override
	public String scan(String input)
	{
		InetAddress inetAddress = null;
		
		try
		{
			inetAddress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		
		if( null != inetAddress )
		{
			return inetAddress.getHostAddress().trim();
		}
		else
		{
			return IConstants.EMPTY_STRING;
		}
		
	}
}
