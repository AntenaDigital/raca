package raca.client;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaAttendee {

	private RacaNetworkProxy proxy_ = null;	
	private String clientID_;		
	private String color_;		
	
	private boolean isOnline_ = false;

	Map<String, Boolean> sessions_ = new HashMap<String, Boolean>();
	
	public RacaAttendee(String clientID, String color){
		
		this.clientID_ = clientID;
		this.color_ = color;		
		proxy_ = new RacaNetworkProxy(this);
	
	}	
	
	public void setIsMaster(String sessionID) {
		
		sessions_.replace(sessionID, false, true);	
	}
	
	public boolean verifyIsMaster(String sessionID) {
		return sessions_.get(sessionID);
	}	
	
	public boolean 	getIsOnline() {
		return isOnline_;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline_ = isOnline;
	}

	public String getClientID() {
		return clientID_;
	}	
	
	public String getColorPaint_() {
		return color_;
	}
	
	public void setColorPaint_(String color) {
		this.color_ = color;
	}
	
	public RacaNetworkProxy getProxy_() {
		return proxy_;
	}	
	
	public void joinSession(String sessionID) throws MalformedURLException {
		
		proxy_.subsPupilAck(sessionID, getClientID());		
		proxy_.sendPupilRequest(sessionID);		
		
	}	
	
	public void joinSessionAsMaster(String sessionID) throws MalformedURLException {
		
		proxy_.subsMasterAck(sessionID, getClientID());
		proxy_.sendMasterRequest(sessionID);			
		
	}
	
	public void quitSession(){	}


	// TODO verificar isso...
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sessions_ == null) ? 0 : sessions_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RacaAttendee other = (RacaAttendee) obj;
		if (sessions_ == null) {
			if (other.sessions_ != null)
				return false;
		} else if (!sessions_.equals(other.sessions_))
			return false;
		return true;
	}	
	
}
