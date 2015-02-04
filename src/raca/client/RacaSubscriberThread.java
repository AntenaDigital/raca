package raca.client;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaSubscriberThread extends Thread{

	private RacaMessageListener subscriber_ = null;
	
	public RacaSubscriberThread(RacaMessageParser parser, String sessionID, String clientID){

		try {
			System.out.println("WARN : a Http Polling context will be started...");            

			subscriber_ = new RacaHttpPoller(parser,
											 RacaHttpPoller.buildSubscribeHitURL(parser.topicName(), sessionID),
											 sessionID, clientID);       

		} catch (java.net.MalformedURLException ex) {

			ex.printStackTrace();
		}
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
