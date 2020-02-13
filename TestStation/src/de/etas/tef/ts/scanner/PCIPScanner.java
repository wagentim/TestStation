package de.etas.tef.ts.scanner;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.etas.tef.ts.utils.IConstants;

public class PCIPScanner extends AbstractScanner<String>
{
	
	public String scan()
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
