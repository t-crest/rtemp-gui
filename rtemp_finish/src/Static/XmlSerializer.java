package Static;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import Model.Aegean.Aegean;
import Model.Interfaces.IConfig;


public class XmlSerializer
{
	
	public static <T extends IConfig> T Load(Class<T> type, String filename) throws Exception 
	{
		Serializer serializer = new Persister();
		File file = new File(filename);
		T object = serializer.read(type, file);
		object.setFileName(file.getName());
		object.setAbsolutePath(filename);
		object.setParrentFolder(file.getParentFile().getParent());
		return object;
	}
	
	public static <T> void Save(T data, String filename) throws Exception
	{
		Serializer serializer = new Persister();
		File file = new File(filename);
		file.createNewFile();
	    serializer.write(data, file);
	}
	
}
