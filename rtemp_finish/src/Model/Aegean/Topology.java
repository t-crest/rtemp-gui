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
