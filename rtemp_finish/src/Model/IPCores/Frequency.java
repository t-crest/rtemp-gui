package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Frequency extends PlatformObject {

	@Attribute(name="Hz")
	String hz;

	public String getHz() {
		return hz;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}
}
