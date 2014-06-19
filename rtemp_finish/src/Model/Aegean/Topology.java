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
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import Model.PlatformObject;
import Model.TopologyTypes;

@Root
public class Topology  extends PlatformObject {

	@Attribute(required=false)
	private TopologyTypes topoType;
	@Attribute(required=false)
	private String routerDepth="0";
	@Attribute(required=false)
	private String linkDepth="0";
	@Attribute(required=false)
	private String routerType="";
	@ElementList(inline=true, required=false)
	private List<Link> links = new ArrayList<Link>();
	
	
	
	public TopologyTypes getTopoType() {
		return topoType;
	}

	public void setTopoType(TopologyTypes type) {
		this.topoType = type;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}
	
	public void addLink(Link link) {
		this.links.add(link);
	}

	public String getRouterDepth() {
		return routerDepth;
	}

	public void setRouterDepth(String routerDepth) {
		this.routerDepth = routerDepth;
	}

	public String getLinkDepth() {
		return linkDepth;
	}

	public void setLinkDepth(String linkDepth) {
		this.linkDepth = linkDepth;
	}

	public String getRouterType() {
		return routerType;
	}

	public void setRouterType(String routerType) {
		this.routerType = routerType;
	}		
}
