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
	private String aspect_;
	
	private boolean isOnline_ = false;

	Map<String, Boolean> sessions_ = new HashMap<String, Boolean>();
	
	public RacaAttendee(String clientID, String color, String aspect){
		
		this.clientID_ = clientID.toString();
		this.color_ = color.toString();
		this.aspect_ = aspect.toString();
		proxy_ = new RacaNetworkProxy(this);
	
	}	
	
	public void switchToMaster(String sessionID) {
		
		sessions_.replace(sessionID, false, true);	
	}
	
	public void switchToPupil(String sessionID){
		
		sessions_.replace(sessionID, true, false);
	}
	
	public boolean isMaster(String sessionID) {
		return sessions_.get(sessionID);
	}	
	
	public boolean 	isOnline() {
		return isOnline_;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline_ = isOnline;
	}

	public String getClientID() {
		return clientID_;
	}	
	
	public String getColor_() {
		return color_;
	}
	
	public void setColor(String color) {
		this.color_ = color;
	}
	
	public String aspectRatio() {
		return aspect_;
	}

	public RacaNetworkProxy getProxy_() {
		return proxy_;
	}	
	
	public void joinSession(String sessionID) throws MalformedURLException {
		
		proxy_.subsPupilAck(sessionID, getClientID());		
		proxy_.subsMasterAck(sessionID, getClientID());
		
		proxy_.sendPupilRequest(sessionID);		
		proxy_.sendMasterRequest(sessionID);
		
		proxy_.sendCommand("espero que servlet receba isso...");
		this.quitSession(sessionID);
		
	}	
	
	public void joinSessionAsMaster(String sessionID) throws MalformedURLException {

		proxy_.sendMasterRequest(sessionID);
	}

	public void quitSession(String sessionID){

		if (isMaster(sessionID))
			proxy_.unsubscribeAsMaster();
		else
			proxy_.unsubscribe();

	}
	
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
