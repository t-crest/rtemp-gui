package Model.Aegean;
import java.awt.Point;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Node implements Comparable<Node>{

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
