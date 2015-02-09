package raca.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import raca.client.RacaNetworkProxy;
import raca.util.client.RacaStringUtil;

/**
 *
 * @author ANTENA DIGITAL
 */
public class RacaSendProxy extends RacaMediatorProxy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Servlet SendProxy foi requerido...");
		
		String reqID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
		String clientID_ = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);
		
		response.setContentType("text/html");

        try {

            PrintWriter writer = response.getWriter();

            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>SEND Servlet</title>");
            writer.println("</head>");
            writer.println("<body>");          

            writer.println("<h1>Mensagem no SEND Servlet \ndo CLIENT-ID :" + clientID_ + " e o REQ-ID :"+ reqID_+ "</h1>");

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
