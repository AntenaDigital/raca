package raca.server;

import java.io.IOException;

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

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		String reqId = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG);
        String clientId = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG);

        if ((reqId == null) || (clientId == null))

            responseErrorMsg(response,WRONG_INPUT_ERROR_MSG);

        else if (reqId.compareTo(RacaNetworkProxy.MEDIATORPROXY_SEND_TAG) == 0) {


            String queueName = RacaStringUtil.trimLocalJmsPrefix((String) request.getParameter(RacaNetworkProxy.QUEUE_NAME));
            String logMsg = (String) request.getParameter(RacaNetworkProxy.MEDIATORPROXY_LOG_MSG_TAG);

           /* RPnSender sender = new RPnSender(queueName,true);
            sender.send(logMsg);

            sender.close();*/

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
