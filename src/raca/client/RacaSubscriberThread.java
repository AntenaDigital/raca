package raca.client;

import java.net.MalformedURLException;

import raca.util.client.RacaLogUtil;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaSubscriberThread extends Thread{

	private RacaMessageListener subscriber_ = null;
	
	
	public RacaSubscriberThread(RacaMessageParser parser, String sessionID, String clientID) throws MalformedURLException{

		RacaLogUtil.log("WARN : a Http Polling context will be started...");            

		subscriber_ = new RacaHttpPoller(parser,sessionID, clientID);
	}	

	public void run() {

		if (subscriber_ != null)
			subscriber_.startsListening();
	}

	public void unsubscribe() {

		if (subscriber_ != null)
			subscriber_.stopsListening();
	}
}
