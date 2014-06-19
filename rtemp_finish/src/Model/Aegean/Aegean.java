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

package Model.Aegean;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import Model.PlatformObject;
import Model.Interfaces.IConfig;

@NamespaceList({@Namespace(reference="http://www.w3.org/2001/XInclude", prefix="xi")})
@Root()
public class Aegean extends PlatformObject implements IConfig
{
	private String fileName = "";
	private String absolutePath = "";
	private String parrentFolder = "";
	
	@Attribute(required=false)
	private String version = "0.1";
	@Element(required=false)
	private String description="";
	@Element(required=false)
	private Platform platform = new Platform();
	@Element(required=false)
	private Application application = new Application();

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public void setAbsolutePath(String filePath) {
		this.absolutePath = filePath;
		
	}
	
	public String getAbsorlutepath() {
		return this.absolutePath;
		
	}

	public String getParrentFolder() {
		return parrentFolder;
		
	}
	
	public void setParrentFolder(String parrentFolder) {
		this.parrentFolder=parrentFolder;
		
	}
}
