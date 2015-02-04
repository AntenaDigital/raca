package raca.client;


public class RacaMasterRequestParser implements RacaMessageParser {

	//
	private String topicName_ = RacaNetworkProxy.MASTER_REQUEST_LOG_MSG; 
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

			if (attendee_.verifyIsMaster(sessionID_)== true) {

				/*RacaMasterReqDialog reqDialog = new RacaMasterReqDialog(RacaNetworkProxy.filterClientID(text));
            	reqDialog.setVisible(true);*/
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
