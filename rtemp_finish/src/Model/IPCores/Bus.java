package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Bus extends PlatformObject {

	@Attribute
	String burstLength;
	@Attribute
	String writeCombine;
	
	public String getBurstLength() {
		return burstLength;
	}
	
	public void setBurstLength(String burstLength) {
		this.burstLength = burstLength;
	}
	
	public String getWriteCombine() {
		return writeCombine;
	}
	
	public void setWriteCombine(String writeCombine) {
		this.writeCombine = writeCombine;
	}
}
