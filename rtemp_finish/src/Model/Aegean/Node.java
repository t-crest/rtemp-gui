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
import java.awt.Point;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Node extends PlatformObject implements Comparable<Node> {

	@Attribute (required=false)
	private String id;
	@Attribute (required=false)
	private String loc; //Point loc;
	@Attribute (required=false, name="IPTypeRef")
	private String ipTypeRef;
	@Attribute (required=false, name="SPMSize")
	private String spmSize = "4K"; //default 4K	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	public void setLoc(Point loc) {
		this.loc = "("+ loc.x + "," + loc.y + ")";
	}
	
	public String getIpTypeRef() {
		return ipTypeRef;
	}

	public void setIpTypeRef(String ipTypeRef) {
		this.ipTypeRef = ipTypeRef;
	}

	public String getSpmSize() {
		return spmSize;
	}

	public void setSpmSize(String spmSize) {
		this.spmSize = spmSize;
	}

	@Override
	public int compareTo(Node other) {
		return this.getLoc().compareTo(other.getLoc());
	}

}
