package Model.Aegean;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Application extends PlatformObject
{
	@Element
	private Communication communication = new Communication();

	public Communication getCommunication() {
		return communication;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}	
}
