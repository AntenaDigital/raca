package raca.server;

import raca.client.RacaCommandParser;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaProxySubscriber extends RacaCommandParser {

	
	public RacaProxySubscriber(String topicName, String sessionID) {
		
    	super(topicName, sessionID);
		
	}	

    public void parseMessageObject(Object obj) {

    	System.out.println("Subscriber Proxy will parse object now...");
    
    }

}