package de.etas.tef.ts.json;

import java.util.ArrayList;
import java.util.List;

import de.etas.tef.ts.utils.IConstants;

public final class TestStation
{
	private String name = IConstants.TXT_UNKNOWN;
	private String pcName = IConstants.TXT_UNKNOWN;
	private String ip = IConstants.TXT_UNKNOWN;
	private List<ClusterTest> clusterTests = null;
	private List<FunctionTest> functionTests = null;
	private long lastScanTime = 0L;
	
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
	public List<ClusterTest> getClusterTests()
	{
		if(clusterTests == null)
		{
			clusterTests = new ArrayList<ClusterTest>();
		}
		
		return clusterTests;
	}
	public void setClusterTests(List<ClusterTest> clusterTests)
	{
		this.clusterTests = clusterTests;
	}
	public List<FunctionTest> getFunctionTests()
	{
		if(functionTests == null)
		{
			functionTests = new ArrayList<FunctionTest>();
		}
		
		return functionTests;
	}
	public void setFunctionTests(List<FunctionTest> functionTests)
	{
		this.functionTests = functionTests;
	}
	public long getLastScanTime()
	{
		return lastScanTime;
	}
	public void setLastScanTime(long lastScanTime)
	{
		this.lastScanTime = lastScanTime;
	}
	
	public void addClusterTest(ClusterTest ct)
	{
		if(clusterTests == null)
		{
			clusterTests = new ArrayList<ClusterTest>();
		}
		
		clusterTests.add(ct);
	}
	
	public void addFunctionTest(FunctionTest ft)
	{
		if(functionTests == null)
		{
			functionTests = new ArrayList<FunctionTest>();
		}
		
		functionTests.add(ft);
	}
}
