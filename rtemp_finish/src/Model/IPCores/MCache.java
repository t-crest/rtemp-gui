package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class MCache extends PlatformObject {

	@Attribute
	String size;
	@Attribute
	String blocks;
	@Attribute
	String repl;
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getBlocks() {
		return blocks;
	}
	
	public void setBlocks(String blocks) {
		this.blocks = blocks;
	}
	
	public String getRepl() {
		return repl;
	}
	
	public void setRepl(String repl) {
		this.repl = repl;
	}
}
