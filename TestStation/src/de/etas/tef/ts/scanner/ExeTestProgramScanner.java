package de.etas.tef.ts.scanner;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.ts.json.Test;
import de.etas.tef.ts.utils.IConstants;

public class ExeTestProgramScanner extends AbstractScanner<Test>
{
	private static final Logger logger = LoggerFactory.getLogger(DirScanner.class);
	private Path startDir = null;
	private Test test = null;
	
	public ExeTestProgramScanner()
	{
		this(IConstants.EMPTY_STRING);
	}
	
	public ExeTestProgramScanner(final Path startDir)
	{
		this.startDir = startDir;
	}
	
	public ExeTestProgramScanner(final String startDir)
	{
		this.startDir = createPath(startDir);
	}
	
	public ExeTestProgramScanner setStartDir(final String startDir)
	{
		this.startDir = createPath(startDir);
		return this;
	}
	
	public ExeTestProgramScanner setStartDir(final Path startDir)
	{
		this.startDir = startDir;
		return this;
	}
	
	public ExeTestProgramScanner setTest(Test test)
	{
		this.test = test;
		return this;
	}
	
	private Path createPath(final String input)
	{
		return FileSystems.getDefault().getPath(input);
	}
	
	@Override
	protected Test scan()
	{
		if(startDir == null || test == null)
		{
			logger.error("Input is wrong: %1", startDir);
			return null;
		}
		
		if(!startDir.isAbsolute())
		{
			startDir = startDir.normalize().toAbsolutePath();
		}
		
		boolean exist = Files.exists(startDir);
		
		if(exist && Files.isDirectory(startDir))
		{
			File[] files = new File(startDir.toString()).listFiles();
			
			for(File file : files)
			{
				Path p = file.toPath();
				
				String s = IConstants.EMPTY_STRING;
				
				if(!Files.isDirectory(p) && (s = p.getFileName().toString().toLowerCase()).endsWith(".exe") && s.startsWith("es"))
				{
					test.setParentDir(startDir);
					test.setFile(p);
				}
			}
			
			return test;
		}
		else
		{
			logger.error("The give input do not exist or is not a directory: %1", startDir);
			
			return null;
		}
		
	}
	
}
