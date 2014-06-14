package Model.Aegean;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Memory  extends PlatformObject {
	
	@Attribute(required=false)
	private String id = "";
	@Attribute(required=false, name="DevTypeRef")	
	private String devTypeRef = "";
	@Attribute(required=false)
	private String size = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevTypeRef() {
		return devTypeRef;
	}
	public void setDevTypeRef(String devTypeRef) {
		this.devTypeRef = devTypeRef;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
}
