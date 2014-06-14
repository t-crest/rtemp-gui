package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class IO extends PlatformObject {

	@Attribute(name="DevTypeRef")
	String devTypeRef;
	@Attribute
	String offset;
	
	public String getDevTypeRef() {
		return devTypeRef;
	}

	public void setDevTypeRef(String devTypeRef) {
		this.devTypeRef = devTypeRef;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}
}
