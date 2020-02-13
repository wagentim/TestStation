package de.etas.tef.ts.json;

import de.etas.tef.ts.utils.IConstants;

public class Driver
{
	private String letter = IConstants.EMPTY_STRING;
	private String type = IConstants.EMPTY_STRING;
	private String name = IConstants.EMPTY_STRING;
	
	public String getLetter()
	{
		return letter;
	}
	public void setLetter(String letter)
	{
		this.letter = letter;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
