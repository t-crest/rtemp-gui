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


public class OSFinder 
{
	    private static boolean isLinux = false;
	    private static boolean isMac = false;
	    private static boolean isWindows = false;
	    
	    static
	    {
	    	String osname = System.getProperty("os.name").toLowerCase();
	    	isLinux = osname.contains("linux") ? true : false;
	    	isMac = osname.contains("mac") ? true : false;  
	    	isWindows = osname.contains("win") ? true : false;  
	    }

	    public static boolean isWindows() { return isWindows; }
	    public static boolean isLinux() { return isLinux; }
	    public static boolean isMac() { return isMac; }
	    
	    // Checks and corrects a filepath
	    public static String filePath(String filepath) {
	    	return isWindows() ? filepath.replace("/", "\\"):filepath;
	    }
}