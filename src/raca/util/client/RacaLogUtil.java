package raca.util.client;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RacaLogUtil {
	
	private static RacaLogUtil instance_ = null;
	
	public static RacaLogUtil instance(){

		if(instance_ == null){			 
			instance_ = new RacaLogUtil();
		}

		return instance_;
	}	
	
	public static void log(String logMessage) {		

		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, logMessage);	
	}
	
}
