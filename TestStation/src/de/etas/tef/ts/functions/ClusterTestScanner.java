package de.etas.tef.ts.functions;

import java.nio.file.Path;

public class ClusterTestScanner implements Runnable
{
	private final Path startDir;
	
	public ClusterTestScanner(final Path startDir)
	{
		this.startDir = startDir;
	}
	
	@Override
	public void run()
	{
		if( startDir == null )
		{
			return;
		}
		
	}
}
