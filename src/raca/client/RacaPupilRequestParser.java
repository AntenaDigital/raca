package raca.client;

public class RacaPupilRequestParser implements RacaMessageParser {

	private String topicName_ = RacaNetworkProxy.PUPIL_REQ_QUEUE_NAME;
	private String sessionID_;
	
	public RacaPupilRequestParser(String sessionID) {
	
		sessionID_ = sessionID;
	}
	
	@Override
	public void parse(Object obj) {
		
		
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
