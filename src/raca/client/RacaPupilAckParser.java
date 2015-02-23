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
public class RacaPupilAckParser implements RacaMessageParser{

	protected boolean end_ = false;	
	
	private RacaNetworkProxy proxy_ = null;	
	private String topicName_ = RacaNetworkProxy.PUPIL_ACK_TOPIC_NAME;
	private String sessionID_;
		
	public RacaPupilAckParser(String sessionID,RacaNetworkProxy proxy) {
		
		proxy_ = proxy;
		sessionID_ = sessionID;	
	}	
	
	@Override
	public void parse(Object obj) {
	
		if (obj instanceof String) {			

			String text = obj.toString();

			if (text.startsWith(topicName_)) {          

				RacaLogUtil.log("Will now ack slave req...");
				
				try {
					proxy_.ackPupilRequest(RacaStringUtil.filterClientID(text),
										RacaStringUtil.filterAspectRatio(text), 
										sessionID_,
										RacaStringUtil.filterPupilColor(text));
					
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
	public String hitURL() throws MalformedURLException{		
		
		if (!topicName_.startsWith(RacaNetworkProxy.PUPIL_ACK_TOPIC_NAME))
			throw new MalformedURLException();
			
		return RacaNetworkProxy.MEDIATORPROXY_URL + "racapupilackproxy";

	}
	
}