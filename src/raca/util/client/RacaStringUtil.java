package raca.util.client;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaStringUtil {

	private static RacaStringUtil _instance = null;
	
	public static RacaStringUtil instance(){
		
		if(_instance == null){			 
			_instance = new RacaStringUtil();
		}
		
		return _instance;
	}
	
	
	public static String filterClientID(String text) {

        String[] split = text.split("\\|");
        return split[1];
    }
	
	public static String filterSessionID(String text){
		
		
		return null;
	}

	
    public static String filterAspectRatio(String text) {

        String[] split = text.split("\\|");
        return split[2];
    }

    
    public static String filterPupilColor(String text) {

        String[] split = text.split("\\|");
        return split[3];
    }

    
    public static String trimLocalJmsPrefix(String jmsName) {

        // LOCAL JNDI DOES NOT USE jms/ prefix...
        return jmsName.substring(3, jmsName.length());
    }

    
    public static String trimURL(String url) {

    	try {
            
    		return java.net.URLEncoder.encode(url, "UTF-8");
            
        } catch (java.io.UnsupportedEncodingException ex) {
            
        	ex.printStackTrace();
        }

        return null;
    }

    
    public static String extractCommandName(String commandDesc) {
        
	if (commandDesc.contains("MARK")) return "MARK";
	if (commandDesc.contains("CLEAR")) return "CLEAR";
	if (commandDesc.contains("CLOSE")) return "CLOSE";

	return "UNKNOWN COMMAND";
    }
	
}
