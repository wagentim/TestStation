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
	private String startDir = IConstants.EMPTY_STRING;
	
	public DirScanner()
	{
		this(IConstants.EMPTY_STRING);
	}
	
	public DirScanner(final String startDir)
	{
		this.startDir = startDir;
	}
	
	public void setStartDir(final String startDir)
	{
		this.startDir = startDir;
	}
	
	public List<Path> scan()
	{
		if(startDir == null || startDir.isEmpty())
		{
			logger.error("Input is wrong: %1", startDir);
			return Collections.emptyList();
		}
		else
		{
			return scanDir();
		}
	}
	
	private List<Path> scanDir()
	{
		Path path = FileSystems.getDefault().getPath(startDir);			
		
		if(!path.isAbsolute())
		{
			path = path.normalize().toAbsolutePath();
		}
		
		boolean exist = Files.exists(path);
		
		if(exist && Files.isDirectory(path))
		{
			List<Path> result = new ArrayList<Path>();
			
			try(DirectoryStream<Path> dirStreams = Files.newDirectoryStream(path))
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
