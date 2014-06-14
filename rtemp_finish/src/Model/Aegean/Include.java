package Model.Aegean;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root(name="include")
@Namespace(reference="http://www.w3.org/2001/XInclude", prefix="xi")
public class Include extends PlatformObject
{
	@Attribute
	private String href="";

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}
