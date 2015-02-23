package raca.client;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import raca.util.client.RacaLogUtil;
import raca.util.client.RacaStringUtil;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterAckParser implements RacaMessageParser{

	protected boolean end_ = false;	
	
	private String topicName_ = RacaNetworkProxy.MASTER_ACK_TOPIC_NAME;
	private String sessionID_;
	private RacaNetworkProxy proxy_ = null;
	
	public RacaMasterAckParser(String sessionID, RacaNetworkProxy proxy) {		

		proxy_ = proxy;
		sessionID_ = sessionID;		
	}
	
	@Override
	public void parse(Object obj){
		
		if (obj instanceof String) {			

			String text = obj.toString();
			
			if (text.startsWith(topicName_)) {
				
				RacaLogUtil.log("Will now ack master req...");
				
				try {
					proxy_.ackMasterRequest(RacaStringUtil.filterClientID(text),sessionID_);
					
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				}

			}			
		}		
	}

	@Override
	public String topicName() {
		
		return topicName_;
	}

	@Override
	public String hitURL() throws MalformedURLException {

		if (!topicName_.startsWith(RacaNetworkProxy.MASTER_ACK_TOPIC_NAME))
			
			throw new MalformedURLException();
			
			
		return RacaNetworkProxy.MEDIATORPROXY_URL + "racamasterackproxy";
		
	}

}