package raca.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaHttpSender {
	
	private String hitURL_ = null;
    private String queueName_ = null;

    public RacaHttpSender(String queueName, String sessionID) {

        queueName_ = queueName.toString();
		
        try {
			
        	hitURL_ = RacaHttpPoller.builCommanddHitURL("SEND", queueName_, sessionID.toString());
		
        } catch (MalformedURLException e) {
        	
			e.printStackTrace();
			System.err.println("message...:" + e.getMessage());
		}
    
    }

    public void send(String msg, String clientID) {
    	
    	try {           

    		String fullURL = new String(hitURL_ + "?" + RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG + '='
    				+ RacaNetworkProxy.MEDIATORPROXY_SEND_TAG 
    				+ '&' + RacaNetworkProxy.QUEUE_NAME + '=' + queueName_
    				+ '&' + RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG + '=' + clientID.toString()
    				+ '&' + RacaNetworkProxy.MEDIATORPROXY_LOG_MSG_TAG + '=' + msg.toString());

    		System.out.println("The URL message to be sent is : " + fullURL);

    		URL racaMediatorURL = new URL(fullURL);

    		/**
    		 * Will now connect to Mediator with URL...
    		 */
    		
    		URLConnection racaMediatorConn = racaMediatorURL.openConnection();
    		
    		BufferedReader buffReader = new BufferedReader(new InputStreamReader(racaMediatorConn.getInputStream()));
    		
    		String text;
 
    		/*
    		StringBuffer fullText = new StringBuffer();
    		Boolean buffFlag = false;*/

    		while ((text = buffReader.readLine()) != null) {

    		}

    	} catch (Exception exc) {

    		exc.printStackTrace();

    	}    	
    }		
}
