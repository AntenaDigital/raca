package raca.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

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
	        
	        response.setContentType("text/html");

	        try {

	            PrintWriter writer = response.getWriter();

	            writer.println("<html>");
	            writer.println("<head>");
	            writer.println("<title>MASTER Publish Servlet</title>");
	            writer.println("</head>");
	            writer.println("<body>");          

	            writer.println("<h1>Mensagem no MASTER Publish Servlet \ndo CLIENT-ID :" + clientID_ + " - REQ-ID :"+ reqID_+ " - TOPIC-NAME :"+ topicName_ +"</h1>");

	            writer.println("</body>");
	            writer.println("</html>");

	        } catch (IOException exc) {
	            
	            exc.printStackTrace();
	        }

	        
	        
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
