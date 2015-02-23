package raca.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import raca.client.RacaNetworkProxy;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterCommandProxy extends RacaMediatorProxy {
	
	private static final long serialVersionUID = 9L;
       
   	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

   		
		String reqID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
		String clientID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);
		
		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet MasterCommandProxy foi requerido...  pelo " + clientID_ + "||" + reqID_);
   		
   	}
}