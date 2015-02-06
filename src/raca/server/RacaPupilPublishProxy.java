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
public class RacaPupilPublishProxy extends RacaMediatorProxy {
	
	private static final long serialVersionUID = 7L;
   
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reqID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
        String clientID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);
        String topicName_ = (String) request.getParameter(RacaNetworkProxy.TOPIC_NAME);

        response.setContentType("text/html");

        try {

            PrintWriter writer = response.getWriter();

            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>PUPIL Publish Servlet</title>");
            writer.println("</head>");
            writer.println("<body>");          

            writer.println("<h1>Mensagem no PUPIL Publish Servlet \ndo CLIENT-ID :" + clientID_ + " - REQ-ID :"+ reqID_+ " - TOPIC-NAME :"+ topicName_ +"</h1>");

            writer.println("</body>");
            writer.println("</html>");

        } catch (IOException exc) {
            
            exc.printStackTrace();
        }	


        
	}

}
