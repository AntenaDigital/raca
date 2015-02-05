package raca.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import raca.util.client.RacaStringUtil;

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
	public void parse(Object obj) {
		
		if (obj instanceof String) {			

			String text = obj.toString();
			
			if (text.startsWith(topicName_)) {
				
				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Will now ack master req...");
				
				proxy_.ackMasterRequest(RacaStringUtil.filterClientID(text),sessionID_);

			}			
		}		
	}

	@Override
	public String topicName() {
		
		return topicName_;
	}

	@Override
	public String hitURL() {
		
		return topicName_ + sessionID_;
	}

}
