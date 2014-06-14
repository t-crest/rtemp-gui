package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class ExtMem extends PlatformObject {

	@Attribute
	String size;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
