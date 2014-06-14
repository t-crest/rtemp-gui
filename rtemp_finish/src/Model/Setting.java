package Model;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Setting 
{
	@Attribute
	private String key;
	@Attribute
	private String value;
	
	public Setting(){}
	
	public Setting(String key, String value)
	{
		this.key=key;
		this.value=value;
	}
	
	public String getKey()
	{
		return this.key;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}
