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
    /*
    	Set entries = subsDatalogOBJ_.entrySet();
    	Iterator it = entries.iterator();
    	
    	while (it.hasNext()) {

    		System.out.println("Map for subs has entries...");
    	
    		Map.Entry entry = (Map.Entry) it.next();
    		Vector data = (Vector) entry.getValue();

    		if (obj == null) 
    			System.out.println("but the obj inside is null...");	

    		else
    			data.add(obj);
    	}
*/    }

}
