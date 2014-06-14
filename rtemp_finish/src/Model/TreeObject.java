package Model;

public class TreeObject {
	public String attributeName, attributeValue, className;
	public PlatformObject platformObject;

	public TreeObject(PlatformObject o, String attributeName, String attributeValue) {
		this.platformObject = o;
		this.className = o.getClass().getSimpleName();
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

}