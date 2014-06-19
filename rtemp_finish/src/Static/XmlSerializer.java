/*
    Copyright 2014 Peter Gelsbo and Andreas Nordmand Andersen

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package Static;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
