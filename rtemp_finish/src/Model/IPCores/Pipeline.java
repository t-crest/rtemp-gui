package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Pipeline extends PlatformObject {

	@Attribute
	String dual;

	public String isDual() {
		return dual;
	}

	public void setDual(String dual) {
		this.dual = dual;
	}
}
