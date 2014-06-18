package Model.Static;
import java.io.File;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import Model.Setting;
import Model.Interfaces.IConfig;

@Root
public class Settings implements IConfig
{
	static Settings instance;
	
	@ElementList(inline=true)
	public ArrayList<Setting> settings;
	public String filename;
	private String absolutepath;
	
	public Settings(){	}
	
	public Settings(String filename)
	{
		settings = new ArrayList<Setting>();
		this.absolutepath = filename;
	}
	
	static
	{
		File dir = new File("settings");
		dir.mkdir();
		
		String filename= OSFinder.filePath("settings/settings.xml");
		try 
		{
			File file = new File(filename);
			instance = file.exists() ? XmlSerializer.Load(Settings.class, filename) : new Settings(filename);
		} 
		catch (Exception e) 
		{
			instance =  new Settings(filename);
			System.out.println("Could not find the settings document.");
		}
	}
	
	public static Settings getInstance() throws Exception
	{
		return instance;
	}
	
	public void save() throws Exception
	{	
		System.out.println(this.absolutepath);
		XmlSerializer.Save(this, this.absolutepath);
	}
	
	public String getSetting(String key)
	{
		for(Setting setting : this.settings) 
		{
			if(setting.getKey().equals(key.toLowerCase()))
			{
				return setting.getValue();
			}
		}
		return "default";
	}
	
	public Settings setSetting(String key, String value)
	{
		for( Setting setting : this.settings) 
		{
			if(setting.getKey().equals(key.toLowerCase()))
			{
				setting.setValue(value);
				return this;
			}
		}
		
		this.settings.add(new Setting(key.toLowerCase(), value));
		return this;
	}

	@Override
	public String getFileName() 
	{
		return this.filename;
	}

	@Override
	public void setFileName(String fileName) 
	{
		this.filename = fileName;
	}

	@Override
	public void setAbsolutePath(String filePath) {
		this.absolutepath = filePath;
		
	}

	@Override
	public void setParrentFolder(String parrentFolder) {
		// TODO Auto-generated method stub
		
	}
}
