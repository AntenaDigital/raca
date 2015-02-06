package raca.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import raca.client.RacaNetworkProxy;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaPupilAckProxy extends RacaMediatorProxy {
	
	private static final long serialVersionUID = 5L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String reqID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
        String clientID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);

        response.setContentType("text/html");

        try {

            PrintWriter writer = response.getWriter();

            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>PUPIL Ack Servlet</title>");
            writer.println("</head>");
            writer.println("<body>");          

            writer.println("<h1>Mensagem no PUPIL Ack Servlet \ndo CLIENT-ID :" + clientID_ + " e o REQ-ID :"+ reqID_+ "</h1>");

            writer.println("</body>");
            writer.println("</html>");

        } catch (IOException exc) {
            
            exc.printStackTrace();
        }
	
	}

}
