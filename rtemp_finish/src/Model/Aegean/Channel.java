package Model.Aegean;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;


@Root(name="channel")
public class Channel  extends PlatformObject
{
	@Attribute
	private String from;
	@Attribute
	private String to;
	@Attribute
	private String bandwidth;
	@Attribute
	private String phits;
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getBandwidth() {
		return bandwidth;
	}
	
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public String getPhits() {
		return phits;
	}
	
	public void setPhits(String phits) {
		this.phits = phits;
	}
}
