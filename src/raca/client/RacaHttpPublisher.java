package raca.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import raca.util.client.RacaLogUtil;
import raca.util.client.RacaStringUtil;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaHttpPublisher {

	private String hitURL_ = null;
    private String topicName_ = null;	
	private String sessionID_;
    
	public RacaHttpPublisher(){	}	
	
	public RacaHttpPublisher(String topicName, String sessionID){		
		
		sessionID_ = sessionID;		
		topicName_ = topicName.toString();
		
        try {
			
        	hitURL_ = RacaHttpPoller.builCommanddHitURL("PUBLISH",topicName_.toString(), sessionID_);
		
        } catch (MalformedURLException e) {
			
			e.printStackTrace();
			System.err.println("message :" + e.getMessage());
		}

	}
	
	public void publish(String msg, String clientID) {
		
		String fullURL = new String(hitURL_ + "?" + RacaNetworkProxy.MEDIATORPROXY_REQ_ID_TAG + '='+ RacaNetworkProxy.MEDIATORPROXY_PUBLISH_TAG
                + '&' + RacaNetworkProxy.TOPIC_NAME + '=' + topicName_ + '&' + RacaNetworkProxy.MEDIATORPROXY_CLIENT_ID_TAG + '=' + clientID.toString()
                + '&' + RacaNetworkProxy.MEDIATORPROXY_LOG_MSG_TAG + '=' + msg.toString() + '&' + sessionID_);

		RacaLogUtil.log("The URL message to be published is : " + fullURL);
				
				URL racaMediatorURL = null;
				URLConnection racaMediatorConn = null;
				
				try {					
					
					racaMediatorURL = new URL(fullURL);					
					racaMediatorConn = racaMediatorURL.openConnection();					

					BufferedReader buffReader = new BufferedReader(new InputStreamReader(racaMediatorConn.getInputStream()));					
					
					String text = new String();
					
					/*StringBuffer fullText = new StringBuffer();
					Boolean buffFlag = false;*/
					
					while ((text = buffReader.readLine()) != null) {}			
					
				} catch (MalformedURLException e) {					
					e.printStackTrace();
					System.err.print("message : " + e.getMessage());
					
				} catch (IOException e) {					
					e.printStackTrace();
					System.err.print("message : " + e.getMessage());
					
				}		
	}
	
	public void publish(Object obj) {
	
		try {         

            RacaLogUtil.log("Will now publish object for URL : " + hitURL_);
            
            URL url = new URL(hitURL_);
            HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();

            urlCon.setRequestProperty("Content-Type", "application/octet-stream");
            urlCon.setDoOutput(true); // to be able to write.
            urlCon.setDoInput(true); // to be able to read.

            ObjectOutputStream out =  new ObjectOutputStream(urlCon.getOutputStream());

            // we might need a ... out.writeObject(new String("topicName"));
            out.writeObject(obj);
            out.flush();
            out.close();

            int result = urlCon.getResponseCode();
    
		} catch (Exception exc) {        
			exc.printStackTrace();    
		}		
	}	
	
	public void close(){
		
	}	
}