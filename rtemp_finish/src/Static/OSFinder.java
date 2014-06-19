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
