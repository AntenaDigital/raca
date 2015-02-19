package raca.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import raca.client.RacaHttpPublisher;
import raca.client.RacaNetworkProxy;
import raca.util.client.RacaStringUtil;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterPublishProxy extends RacaMediatorProxy {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	@Override
	    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			Object obj = new Object();
			
	        ObjectInputStream in = new ObjectInputStream(request.getInputStream());
	        try {
	        	
				obj = in.readObject();
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet MasterPublishProxy diparou uma exception : " + e.getMessage());
				  
			}
	        
	        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet MasterPublishProxy recebeu um command :" + obj.toString());
	    }

	    @Override
	    public void init() throws ServletException {

	    }

	    @Override
	    public void contextInitialized(ServletContextEvent event) {


	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent event) {


	    }

}
