package raca.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

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
	
	 // COMMAND
    private static RacaProxySubscriber subscriber_ = null;
    private static RacaSubscriberThread subscriberThread_ = null;

    public static String TOPIC_NAME = RacaNetworkProxy.MASTER_ACK_TOPIC_NAME;
    public static HashMap subsDatalog_ = new HashMap();

    public RacaMasterAckProxy() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reqID = request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
		String clientID = request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);

		if ((reqID == null) || (clientID == null))

			responseErrorMsg(response,WRONG_INPUT_ERROR_MSG);

		else if (reqID.compareTo(RacaNetworkProxy.MEDIATORPROXY_POLL_TAG) == 0) {

			// 	this will enable the browser output...
			response.setContentType( "text/xml;charset=UTF-8" );

			PrintWriter writer = response.getWriter();
			
			Vector msgQueue = (Vector)subsDatalog_.get(clientID);
			
			if (msgQueue != null)
				
				while (!msgQueue.isEmpty()) {

					String command = (String) msgQueue.remove(msgQueue.size() - 1);

				// 		for DEBUGING
					System.out.println("Message received at RacaMasterAckProxy : " + '\n' + command);
					writer.println(command);
				}

		} else if (reqID.compareTo(RacaNetworkProxy.MEDIATORPROXY_LISTENING_TAG) == 0) {

			System.out.println("Listener " + clientID + " willing to be registred...");
			
			if (!subsDatalog_.containsKey(clientID)) {
				System.out.println("Listener " + clientID + " is being registred...");
				subsDatalog_.put(clientID, new Vector());
			}
		}
	}
	
	@Override
    public void contextInitialized(ServletContextEvent event) {

        /*System.out.println("Will now listen to : " + RacaNetworkProxy.MASTER_ACK_TOPIC_NAME);
        subscriber_ = new RacaProxySubscriber(RacaStringUtil.trimLocalJmsPrefix(RacaNetworkProxy.MASTER_ACK_TOPIC_NAME),
                                            new HashMap(),subsDatalog_);

        if (subscriberThread_ == null) {
            subscriberThread_ = new RacaSubscriberThread(subscriber_);
        }

        subscriberThread_.start();
*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        if (subscriberThread_ != null)
            subscriberThread_.unsubscribe();
    }

	
}
