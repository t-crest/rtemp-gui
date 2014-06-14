package Model.Aegean;
import java.awt.Point;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root(name="link")
public class Link extends PlatformObject implements Comparable<Link> {

	@Attribute(required=false)
	private String source;
	@Attribute(required=false) 
	private String sink;
	@Attribute(required=false)
	private String linkDepth;

	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSink() {
		return sink;
	}

	public void setSink(String sink) {
		this.sink = sink;
	}
	

	public String getLinkDepth() {
		return linkDepth;
	}

	public void setLinkDepth(String linkDepth) {
		this.linkDepth = linkDepth;
	}
	
	public Point getSourcePoint() {
		return new Point(Integer.parseInt(source.substring(1, 2)), Integer.parseInt(source.substring(3, 4)));
	}
	
	public Point getSinkPoint() {
		return new Point(Integer.parseInt(sink.substring(1, 2)), Integer.parseInt(sink.substring(3, 4)));
	}

	@Override
	public int compareTo(Link o) {
		// Sort links by source point, then sink
		int x = getSource().compareTo(o.getSource());
		return x == 0 ? getSink().compareTo(o.getSink()) : x;
	}
}
