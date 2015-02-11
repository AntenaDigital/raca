package raca.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import raca.client.RacaNetworkProxy;
import raca.client.RacaSubscriberThread;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterAckProxy extends RacaMediatorProxy {
	

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String reqID_ = request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
		String clientID_ = request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);            
        
		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet MasterAckProxy foi requerido...  pelo " + clientID_ + "||" + reqID_);
    				
	}
	
	@Override
    public void contextInitialized(ServletContextEvent event) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }

	
}
