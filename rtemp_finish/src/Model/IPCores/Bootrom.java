package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Bootrom  extends PlatformObject {

	@Attribute
	String app;

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}
}
