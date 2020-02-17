package de.etas.tef.ts.scanner;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.ts.utils.IConstants;

/**
 * Scan the give dir and give the sub-dirs
 * @author UIH9FE
 *
 */
public class DirScanner extends AbstractScanner<List<Path>>
{
	private static final Logger logger = LoggerFactory.getLogger(DirScanner.class);
	private Path startDir = null;
	
	public DirScanner()
	{
		this(IConstants.EMPTY_STRING);
	}
	
	public DirScanner(final Path startDir)
	{
		this.startDir = startDir;
	}
	
	public DirScanner(final String startDir)
	{
		this.startDir = createPath(startDir);
	}
	
	public Path getStartPath()
	{
		return this.startDir;
	}
	
	public DirScanner setStartDir(final String startDir)
	{
		this.startDir = createPath(startDir);
		return this;
	}
	
	public DirScanner setStartDir(final Path startDir)
	{
		this.startDir = startDir;
		return this;
	}
	
	private Path createPath(final String input)
	{
		return FileSystems.getDefault().getPath(input);
	}
	
	@Override
	protected List<Path> scan()
	{
		if(startDir == null)
		{
			logger.error("Input is wrong: %1", startDir);
			return Collections.emptyList();
		}
		
		if(!startDir.isAbsolute())
		{
			startDir = startDir.normalize().toAbsolutePath();
		}
		
		boolean exist = Files.exists(startDir);
		
		if(exist && Files.isDirectory(startDir))
		{
			List<Path> result = new ArrayList<Path>();
			
			try(DirectoryStream<Path> dirStreams = Files.newDirectoryStream(startDir))
			{
				for(Path p : dirStreams)
				{
					if( Files.isDirectory(p))
					{
						result.add(p);
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return result;
		}
		else
		{
			logger.error("The give input do not exist or is not a directory: %1", startDir);
			
			return Collections.emptyList();
		}
		
	}
}
