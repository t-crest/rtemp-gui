package Model.IPCores;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import Model.PlatformObject;
import Model.Interfaces.IConfig;

@Root
public class IPCores extends PlatformObject implements IConfig {

	private String fileName;
	@ElementList(name="IPCores", inline=true)
	List<IPCore> ipCores = new ArrayList<IPCore>();
	private String absolutepath;

	public List<IPCore> getIpCores() {
		return ipCores;
	}

	public void setIpCores(ArrayList<IPCore> ipCores) {
		this.ipCores = ipCores;
	}

	@Override
	public String getFileName() 
	{
		return fileName;
	}

	@Override
	public void setFileName(String fileName) 
	{
		this.fileName=fileName;
	}

	@Override
	public void setAbsolutePath(String absolutepath) {
		this.absolutepath = absolutepath;
		
	}

	public String getAbsolutePath() {
		return this.absolutepath;
	}

	@Override
	public void setParrentFolder(String parrentFolder) {
		// TODO Auto-generated method stub
		
	}
}
