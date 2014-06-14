package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class DCache extends PlatformObject {

	@Attribute
	String size;
	@Attribute
	String assoc;
	@Attribute
	String repl;
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getAssoc() {
		return assoc;
	}
	
	public void setAssoc(String assoc) {
		this.assoc = assoc;
	}
	
	public String getRepl() {
		return repl;
	}
	
	public void setRepl(String repl) {
		this.repl = repl;
	}
}
