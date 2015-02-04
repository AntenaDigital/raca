package raca.server;

import java.io.IOException;
import java.io.ObjectInputStream;

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

	 @Override
	    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	        String reqId = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
	        String clientId = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);
	        String topicName = (String) request.getParameter(RacaNetworkProxy.TOPIC_NAME);


	        if ((reqId == null) && (clientId == null) && (topicName == null))  {

	                System.out.println("preparing to publish the object instance for master... \n");
	                RacaHttpPublisher publisher = null;
	                ObjectInputStream in = new ObjectInputStream(request.getInputStream());

	                try {

	                    System.out.println("Will now publish the object instance for master... \n");
	                    Object obj = in.readObject();

			    publisher = new RacaHttpPublisher(RacaStringUtil.trimLocalJmsPrefix(RacaNetworkProxy.MASTER_COMMAND_TOPIC_NAME),null);
	                    publisher.publish(obj);

	                } catch (ClassNotFoundException e) {
	                    e.printStackTrace();
	                }

	                in.close();
			if (publisher != null)
	                	publisher.close();           
	        }

	        else if (reqId.compareTo(RacaNetworkProxy.MEDIATORPROXY_PUBLISH_TAG) == 0) {


		    // only for binary msgs topicName is null
	            topicName = RacaStringUtil.trimLocalJmsPrefix(topicName);

	            String logMsg = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_LOG_MSG_TAG);

	            RacaHttpPublisher publisher = new RacaHttpPublisher(topicName, null);

	            
	            publisher.publish(logMsg);
	            
	            publisher.close();

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
