/*
    Copyright 2014 Peter Gelsbo and Andreas Nordmand Andersen

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

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
	ArrayList<IPCore> ipCores = new ArrayList<IPCore>();
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
