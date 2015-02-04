package raca.client;

public class RacaCommandParser implements RacaMessageParser {	
	
	protected boolean end_ = false;
	
	private String topicName_;
	private String sessionID_;
	
	public RacaCommandParser(String topicName, String sessionID) {
		topicName_ = topicName.toString();
		sessionID_ = sessionID.toString();	
	}
	
	public String topicName() {
		
		return topicName_;
	}
	
	public String hitURL() {
		
		return topicName_ + sessionID_;
	}

	public void parse(Object obj) {
		
		// TODO
		
	}
}
