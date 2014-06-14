package Model.IPCores;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class IPCore extends PlatformObject {

	@Attribute(name="IPType")
	String ipType;
	@Attribute(required=false)
	String entity; 
	@Attribute(required=false)
	String srcFile;
	@Element(required=false)
	Patmos patmos;
	
	public String getIpType() {
		return ipType;
	}
	
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public String getSrcFile() {
		return srcFile;
	}
	
	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	
	public Patmos getPatmos() {
		return patmos;
	}
	
	public void setPatmos(Patmos patmos) {
		this.patmos = patmos;
	}
}
