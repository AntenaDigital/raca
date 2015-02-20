package raca.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StringBufferInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import raca.util.client.RacaLogUtil;
import raca.util.client.RacaStringUtil;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaHttpPoller implements RacaMessageListener{

	public static int OBJ_POLLER = 1;
	public static volatile int POLLING_MODE = OBJ_POLLER;

	private RacaMessageParser parser_ = null;
	private String clientID_ = null;
	private String sessionID_ = null;
	
	private RacaAttendee attendee_ = null;
	
	private String hitURL_ = null;
	private boolean end_ = false;

	public RacaHttpPoller(RacaMessageParser parser, String sessionID, String clientID) throws MalformedURLException{

		parser_ = parser;
		hitURL_ = parser.hitURL();
		clientID_ = clientID.toString();
		sessionID_ = sessionID.toString();
	}

	public static String builCommanddHitURL(String mode, String hitTarget, String sessionID) throws MalformedURLException{

		if (mode.startsWith("SEND"))
			return RacaNetworkProxy.MEDIATORPROXY_URL + "racasendproxy";

		
		else if (mode.startsWith("PUBLISH")) {
			
			String urlFlag = "";

			if (hitTarget.startsWith(RacaNetworkProxy.MASTER_COMMAND_TOPIC_NAME + sessionID))


				urlFlag = "racamasterpublishproxy";

			else
				urlFlag = "racapupilpublishproxy";
		
			return RacaNetworkProxy.MEDIATORPROXY_URL + urlFlag;
		}

		else throw new java.net.MalformedURLException();
		    
	}
	
	@Override	
	public void startsListening() {

		try {
			while (!end_) {

				String objCommandURL = new String(hitURL_ + "?" + RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG + '=' +
						RacaNetworkProxy.MEDIATORPROXY_NOTEBOARD_POLL_TAG + '&' +
						RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG + '=' + clientID_ + '&' + sessionID_);

				URL racaMediatorURL;

				RacaLogUtil.log(objCommandURL);
				
				racaMediatorURL = new URL(objCommandURL);				
				URLConnection racaMediatorConn = racaMediatorURL.openConnection();			
				
				racaMediatorConn.setRequestProperty("Accept-Charset", "UTF-8");
				
				ObjectInputStream in = null;
				//InputStream input = racaMediatorConn.getInputStream();				
				
				// Tentando fugir do disparo da Exception ------				
				if(!racaMediatorConn.getInputStream().equals(null)){
					
					in = new ObjectInputStream(racaMediatorConn.getInputStream());
					
					parser_.parse(in.readObject());			
				
				}else{
					
					System.out.print("WARN : Mudando a rota, pois o objeto est� vazio");					
					attendee_.joinSession(this.sessionID_);					
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("exception URL  :  " + e.getMessage());

		} catch (IOException e) {			
			e.printStackTrace();
			System.err.println("exception URLConnection  :  " + e.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("exception readObject :  " + e.getMessage());
		}

	}
	
	@Override
	public void stopsListening() {
		end_ = true;
	}
}