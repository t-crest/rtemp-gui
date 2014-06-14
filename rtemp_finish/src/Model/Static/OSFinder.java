package Model.Static;

public class OSFinder 
{
	    private static boolean isWindows = false;
	    
	    static
	    {
	    	String osname = System.getProperty("os.name").toLowerCase();
	    	isWindows = osname.contains("win") ? true : false;    	
	    }

	    public static boolean isWindows() { return isWindows; }
	    public static boolean isMac() { return !isWindows; }

}
