package raca.client;

import java.net.MalformedURLException;

import raca.util.client.RacaStringUtil;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterRequestParser implements RacaMessageParser {

	private String topicName_ = RacaNetworkProxy.MASTER_REQ_TOPIC_NAME;    //MASTER_REQUEST_LOG_MSG 
	private String sessionID_;
	private RacaAttendee attendee_ = null;
	
	public RacaMasterRequestParser(String sessionID, RacaAttendee attendee) {
		
		attendee_ = attendee;
		sessionID_ = sessionID;
	}
	
	@Override
	public void parse(Object obj) {

		if (obj instanceof String) {			
			String text = obj.toString();

			if (attendee_.isMaster(sessionID_)== true) {

				RacaMasterReqDialog reqDialog = new RacaMasterReqDialog(RacaStringUtil.filterClientID(text), attendee_, sessionID_);
            	reqDialog.setVisible(true);
			}
		}

	}

	@Override
	public String topicName() {
		
		return topicName_;
	}

	@Override
	public String hitURL() throws MalformedURLException {		
		
		if (!topicName_.startsWith(RacaNetworkProxy.MASTER_REQ_TOPIC_NAME))
			throw new MalformedURLException();
			
		return RacaNetworkProxy.MEDIATORPROXY_URL + "racamasterreqproxy";
		
	}

}
