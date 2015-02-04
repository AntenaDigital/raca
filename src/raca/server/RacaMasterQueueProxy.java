package raca.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import raca.client.RacaNetworkProxy;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterQueueProxy extends RacaMediatorProxy {

	public static Vector masterLog_ = new Vector();

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String reqID = request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
		String clientID = request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);

		RacaNetworkProxy proxy_ = null;
		proxy_.log("Recebi uma visita  :"+clientID);
		
		PrintWriter write = response.getWriter();

		if (reqID == null)
			write.print("REQ_ID (null) :" + reqID );           

		else if (reqID.compareTo(RacaNetworkProxy.MEDIATORPROXY_MASTER_CHECK_TAG) == 0) {

			// this will enable the browser output...
			response.setContentType( "text/xml;charset=UTF-8" );

			PrintWriter writer = response.getWriter();

			if (masterLog_.isEmpty()) {
				// for DEBUGING
				System.out.println("No Master registered for RPN SESSION...");
				writer.println("0");

			}
			else {
				// for DEBUGING
				System.out.println("Got Master registered for RPN SESSION...");
				writer.println("1");
			}

		} else if (reqID.compareTo(RacaNetworkProxy.MEDIATORPROXY_MASTER_UPDATE_TAG) == 0) {

			// for DEBUGING
			System.out.println("Master is being registered for RPN SESSION...");
			masterLog_.add(clientID);

		} else if (reqID.compareTo(RacaNetworkProxy.MEDIATORPROXY_MASTER_RESET_TAG) == 0) {

			// for DEBUGING
			System.out.println("Master is being unregistered for RPN SESSION...");
			masterLog_.removeAllElements();
		}    	
	}
}
