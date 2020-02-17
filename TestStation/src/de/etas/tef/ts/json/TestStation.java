package de.etas.tef.ts.json;

import de.etas.tef.ts.utils.IConstants;

public final class TestStation extends AbstractFile
{
	private String name = IConstants.TXT_UNKNOWN;
	private String pcName = IConstants.TXT_UNKNOWN;
	private String ip = IConstants.TXT_UNKNOWN;
	private long lastScanTime = 0L;
	private ClusterTest clusterTest = null;
	private FunctionTest functionTest = null;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPcName()
	{
		return pcName;
	}
	public void setPcName(String pcName)
	{
		this.pcName = pcName;
	}
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public long getLastScanTime()
	{
		return lastScanTime;
	}
	public void setLastScanTime(long lastScanTime)
	{
		this.lastScanTime = lastScanTime;
	}
	public ClusterTest getClusterTest()
	{
		return clusterTest;
	}
	public void setClusterTest(ClusterTest clusterTest)
	{
		this.clusterTest = clusterTest;
	}
	public FunctionTest getFunctionTest()
	{
		return functionTest;
	}
	public void setFunctionTest(FunctionTest functionTest)
	{
		this.functionTest = functionTest;
	}
}
