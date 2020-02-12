package de.etas.tef.ts.scan;

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

/**
 * Scan the give dir and give the sub-dirs
 * @author UIH9FE
 *
 */
public class DirScanner implements IScanner<List<String>>
{
	private static final Logger logger = LoggerFactory.getLogger(DirScanner.class);

	@Override
	public List<String> scan(List<String> input)
	{
		if(input == null || input.size() <= 0)
		{
			logger.error("Input is wrong: %1", input);
			return Collections.emptyList();
		}
		else
		{
			List<String> result = new ArrayList<String>();
			
			for(String in : input)
			{
				handleInputDir(in, result);
			}
			return result;
		}
	}

	private void handleInputDir(String in, List<String> result)
	{
		Path path = FileSystems.getDefault().getPath(in);			
		
		if(!path.isAbsolute())
		{
			path = path.normalize().toAbsolutePath();
		}
		
		boolean exist = Files.exists(path);
		
		if(exist && Files.isDirectory(path))
		{
			try(DirectoryStream<Path> dirStreams = Files.newDirectoryStream(path))
			{
				for(Path p : dirStreams)
				{
					logger.info(p.toString());
					result.add(p.toString());
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			logger.error("The give input do not exist or is not a directory: %1", in);
		}
		
	}
}
