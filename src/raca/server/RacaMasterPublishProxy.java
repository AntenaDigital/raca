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

		
	        String reqID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
	        String clientID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);
	        String topicName_ = (String) request.getParameter(RacaNetworkProxy.TOPIC_NAME);
	        
	        ObjectInputStream in = new ObjectInputStream(request.getInputStream());
	        try {
	        	
				Object obj = in.readObject();
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet MasterPublishProxy diparou uma exception : " + e.getMessage());
				  
			}
	        
	        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet MasterPublishProxy foi requerido...pelo " + clientID_ + "||" + reqID_ + "||" + topicName_);
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
