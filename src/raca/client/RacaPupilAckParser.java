package raca.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import raca.util.client.RacaStringUtil;

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

				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Will now ack slave req...");
				
				proxy_.ackPupilRequest(RacaStringUtil.filterClientID(text),
									RacaStringUtil.filterAspectRatio(text), 
									sessionID_,
									RacaStringUtil.filterPupilColor(text));
			}
		}

	}

	@Override
	public String topicName() {

		return null;
	}

	@Override
	public String hitURL() {
		
		return null;
	}

	
}
