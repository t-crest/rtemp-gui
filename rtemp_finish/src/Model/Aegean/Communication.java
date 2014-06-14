package Model.Aegean;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Communication
{
	@Attribute(required=false)
	public String comType;
	@Attribute(required=false)
	public String phits;
	@ElementList (inline=true, required=false)
	public List<Channel> channel1 = new ArrayList<Channel>();
	
	public String getComType() {
		return comType;
	}
	
	public void setComType(String comType) {
		this.comType = comType;
	}
	
	public String getPhits() {
		return phits;
	}
	
	public void setPhits(String phits) {
		this.phits = phits;
	}
	
	public List<Channel> getChannel1() {
		return channel1;
	}
	
	public void setChannel1(ArrayList<Channel> channel1) {
		this.channel1 = channel1;
	}
}
