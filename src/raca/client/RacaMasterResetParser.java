package raca.client;


import java.net.MalformedURLException;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterResetParser implements RacaMessageParser{

	private String topicName_ = RacaNetworkProxy.MASTER_QUEUE_NAME;
	private String sessionID_;
	private RacaNetworkProxy proxy_;
	
	public RacaMasterResetParser(String sessionID, RacaNetworkProxy proxy) {
		
		sessionID_ = sessionID.toString();
		proxy_ = proxy;
	}	
	
	@Override
	public void parse(Object obj) {
		
	}

	@Override
	public String topicName() {
		return topicName_;
	}

	@Override
	public String hitURL() throws MalformedURLException {
		
		if (!topicName_.startsWith(RacaNetworkProxy.MASTER_QUEUE_NAME))
			throw new java.net.MalformedURLException();

		return RacaNetworkProxy.MEDIATORPROXY_URL + "racamasterqueueproxy";	

	}

}