package raca.client;

import raca.util.client.RacaStringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaNetworkProxy {

	/*** 
	 * 			DECLARATION OF VARIABLE 
	 ***/	
	
	private boolean isMaster_ = false;

	public static boolean NO_BUS_CONTROL_ = false;
	
	public static String SERVERNAME = new String("localhost"); //"147.65.7.10"
	public static String QUEUE_NAME = "QUEUE_NAME";
	public static String TOPIC_NAME = "TOPIC_NAME";

	/*
	 * MASTER and PUPIL command publishing TOPIC
	 */
	public static String MASTER_COMMAND_TOPIC_NAME = new String(
			"jms/topic/RACA_MASTER_COMMAND_TOPIC_");

	public static String PUPIL_COMMAND_TOPIC_NAME = new String(
			"jms/topic/RACA_PUPIL_COMMAND_TOPIC_");

	/*
	 * MASTER listening on SLAVE REQ QUEUE and publishing on SLAVE ACK
	 */
	public static String PUPIL_REQ_QUEUE_NAME = new String(
			"jms/queue/RACA_PUPIL_REQ_QUEUE_");

	public static String MASTER_REQ_TOPIC_NAME = new String(
			"jms/topic/RACA_MASTER_REQ_TOPIC_");

	/*
	 * MASTER ACKNOWLEDGE
	 */

	public static String MASTER_ACK_TOPIC_NAME = new String(
			"jms/topic/RACA_MASTER_ACK_TOPIC_");

	public static String MASTER_QUEUE_NAME = new String(
			"jms/queue/RACA_MASTER_QUEUE_");

	public static String PUPIL_ACK_TOPIC_NAME = new String(
			"jms/topic/RACA_PUPIL_ACK_TOPIC_");	

	/*
	 * CONTROL MESSAGES
	 */
	public static String MASTER_REQUEST_LOG_MSG = new String("MASTER_REQUEST");

	public static String MASTER_ACK_LOG_MSG = new String("MASTER_ACK");

	public static String PUPIL_REQ_LOG_MSG = new String("PUPIL_REQ");

	public static String PUPIL_ACK_LOG_MSG = new String("PUPIL_ACK");

	public static String NULL_MSG = new String("");

	public static String COMMAND_PREFIX = new String("<COMMAND");

	/*
	 * CLIENT/SERVER CONTROL MESSAGES
	 */
	public static String MEDIATORPROXY_REQ_ID_TAG = "REQ_ID";

	public static String MEDIATORPROXY_COMMAND_TAG = "RACA_COMMAND";

	public static String MEDIATORPROXY_CLIENT_ID_TAG = "CLIENT_ID";

	public static String MEDIATORPROXY_SESSION_ID_TAG = "SESSION_ID";


	public static String MEDIATORPROXY_MASTER_UPDATE_TAG = "MASTER_UPDATE";

	public static String MEDIATORPROXY_MASTER_CHECK_TAG = "MASTER_CHECK";

	public static String MEDIATORPROXY_MASTER_RESET_TAG = "MASTER_RESET";

	public static String MEDIATORPROXY_POLL_TAG = "POLL";

	public static String MEDIATORPROXY_NOTEBOARD_POLL_TAG = "OBJ_POLL";


	public static String MEDIATORPROXY_SEND_TAG = "SEND";
	public static String MEDIATORPROXY_PUBLISH_TAG = "PUB";

	public static String MEDIATORPROXY_LISTENING_NAME_TAG = "LISTENING_NAME";

	public static String MEDIATORPROXY_LISTENING_TAG = "SUBSREC";

	public static String MEDIATORPROXY_LOG_MSG_TAG = "LOG_MSG";


	public static String MEDIATORPROXY_URL = "http://" + SERVERNAME
			+ ":8080/racaserver/"; //racamediatorproxy

	public static String ACTIVATED_FRAME_TITLE = "NO_TITLE";

		
	/*** 
	 * 			REFERENCE OF CLASS 
	 ***/	

	// listens to the slave request queue (MASTER)
	
	private RacaSubscriberThread pupilReqSubscribeThread_ = null;

	// subscription to RACA COMMANDs topic (PUPIL)
	private RacaSubscriberThread commandSubscriberThread_ = null;

	// subscription for an acknowledge given to a PUPIL to join the session
	// (ALL)
	private RacaSubscriberThread pupilAckSubscriberThread_ = null;

	// subscription to MASTER request
	private RacaSubscriberThread masterReqSubscriberThread_ = null;

	// subscription to MASTER acknowledge (PUPIL)
	private RacaSubscriberThread masterAckSubscriberThread_ = null;

	// publisher for RACA COMMAND (MASTER)
	private RacaHttpPublisher commandPublisher_ = null;

	// publisher for MASTER request
	private RacaHttpPublisher masterRequestPublisher_ = null;

	// queues up a pupil request (PUPIL)
	private RacaHttpSender pupilRequestSender_ = null;

	private RacaHttpSender masterSender_ = null;

	private RacaMessageListener masterCheckConsumer_ = null;

	private RacaMessageListener masterResetConsumer_ = null;
	
	private RacaAttendee attendee_ = null;
	
	
	/*** 
	 * 			METHODS 
	 ***/
	
	/*
	 * TODO - lista as possiveis sessoes ja existentes
	 */
	public static List<String> listCurrentSessions() {
		
		return new ArrayList<String>();
	}
	
	
	/*
	 * TODO - cria uma nova sessao
	 */
	public static String startNewSession() {
		
		return "NEW SESSION ID";
	}
	

	public RacaNetworkProxy(RacaAttendee attendee) {		
		
		this.attendee_ = attendee;			
	
	}	

	public void log(String logMessage) {		
		
		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, logMessage);	
	}	
	
	
	// 		envia pedido para se tornar MASTER	
	public void sendMasterRequest(String sessionID) {

		// 		checa para ver se ja existe um MASTER
		boolean gotMaster = checkMasterQueue(sessionID);

		if (!gotMaster) {
			
			// I AM THE MASTER NOW FOR SESSION ID...
			ackMasterRequest(attendee_.getClientID(), sessionID);			
			
		} else {

			// THERE IS A MASTER...SO ASK FOR THE LOCK...
			if (masterRequestPublisher_ == null)
				masterRequestPublisher_ = new RacaHttpPublisher(MASTER_REQ_TOPIC_NAME + sessionID, sessionID);

			masterRequestPublisher_.publish(MASTER_REQUEST_LOG_MSG + '|'+ attendee_.getClientID(), attendee_.getClientID());

			log(attendee_.getClientID() + " has requested MASTER lock for SESSION with ID : "	+ sessionID + '\n');

		}

	}
	
	
	public void sendPupilRequest(String sessionID) {

		/**
		 * REQUESTS TO BECOME A PUPIL 
		 */
		if (pupilRequestSender_ == null)
			pupilRequestSender_ = new RacaHttpSender(PUPIL_REQ_QUEUE_NAME + sessionID, sessionID);

		pupilRequestSender_.send(PUPIL_REQ_LOG_MSG + '|' + attendee_.getClientID(), attendee_.getClientID());
		
		log("Pupil request sent !!!");
	}
	
	
	public void ackMasterRequest(String clientID, String sessionID) {

		if (clientID.compareTo(attendee_.getClientID()) == 0) {
			
			// and now BECOMES MASTER
			attendee_.sessions_.put(sessionID, true);
			

			log("You are now being configured as MASTER for SESSION with ID : "+ sessionID + '\n');
			
			RacaNetworkDialog.instance().setTitle(RacaNetworkDialog.TITLE + "MASTER");

			/*
			 * RACA COMMAND PUBLISH FOR MASTER
			 */
			if (commandPublisher_ == null)
				commandPublisher_ = new RacaHttpPublisher(MASTER_COMMAND_TOPIC_NAME + sessionID, sessionID);

			// MASTER SUBS to PUPIL COMMAND TOPIC (specific to old_MKP)
			if (commandSubscriberThread_ == null)
				commandSubscriberThread_ = new RacaSubscriberThread(new RacaCommandParser(PUPIL_COMMAND_TOPIC_NAME,sessionID),
																	sessionID,
																	clientID);
			
			commandSubscriberThread_.start();

			/*
			 * PUPIL REQ RECEIVE
			 * mudanca na referencia
			 */

			if (pupilReqSubscribeThread_ == null) {
				pupilReqSubscribeThread_ = new RacaSubscriberThread(new RacaPupilRequestParser(sessionID, this.attendee_) , sessionID, clientID);
				pupilReqSubscribeThread_.start();
			}

			/*
			 * MASTER REQ SUBS
			 */
			if (masterReqSubscriberThread_ == null) {
				masterReqSubscriberThread_ = new RacaSubscriberThread(new RacaMasterRequestParser(sessionID, this.attendee_), sessionID, clientID);


				masterReqSubscriberThread_.start();
				
			}
			
			// SETs THE MASTER_QUEUE EMPTY
			resetMasterQueue(sessionID, clientID);
			updateMasterQueue(clientID, sessionID);

			attendee_.setOnline(true);

		} else
			
			// o novo mestre nao sou eu ... somente uma notificacao entao...
			
			log(clientID + " has being acknowledged as MASTER for SESSION with ID : "+ sessionID + '\n');

	}

	
	public void ackPupilRequest(String clientID, String master_aspect, String sessionID, String color) {

		log(clientID + " has being acknowledged as PUPIL of RACA-SESSION with ID : " + sessionID + '\n');

		if (clientID.compareTo(attendee_.getClientID()) == 0) {

			/*
			 * RACA COMMAND PUBLISH
			 */        	
			if (commandPublisher_ == null)
				commandPublisher_ = new RacaHttpPublisher(PUPIL_COMMAND_TOPIC_NAME + sessionID, sessionID);

			/* 
			 * PUPIL SUBS to RACA COMMAND TOPIC
			 */            
			if (commandSubscriberThread_ == null)
				commandSubscriberThread_ = new RacaSubscriberThread(new RacaCommandParser(MASTER_COMMAND_TOPIC_NAME,sessionID), sessionID, clientID);

			commandSubscriberThread_.start();

			log("You are now following RACA-SESSION with ID : " + sessionID + '\n');

			RacaNetworkDialog.instance().setTitle(RacaNetworkDialog.TITLE + "PUPIL");

			if (attendee_.aspectRatio().compareTo(master_aspect) != 0)
				JOptionPane.showMessageDialog(null,
						"Please adjust your aspect ratio to " + master_aspect, "Aspect Warning", JOptionPane.WARNING_MESSAGE);

			attendee_.setColor(color);
			attendee_.setOnline(true);
		}

	}

	
	public void subsMasterAck(String sessionID, String clientID) {

		if (masterAckSubscriberThread_ == null)
			masterAckSubscriberThread_ = new RacaSubscriberThread(new RacaMasterAckParser(sessionID, this), sessionID, clientID);

		masterAckSubscriberThread_.start();

		log("Will be listening to MASTER ACK now...");

	}

	
	public void subsPupilAck(String sessionID, String clientID) {

		if (pupilAckSubscriberThread_ == null)
			pupilAckSubscriberThread_ = new RacaSubscriberThread(new RacaPupilAckParser(sessionID, this), sessionID, clientID);

		pupilAckSubscriberThread_.start();

		log("Will be listening to PUPIL ACK now...");

	}

	
	public void updateMasterQueue(String clientID, String sessionID) {

		/** UPDATES THE MASTER_QUEUE STATUS */		

			// FILLs UP THE MASTER_QUEUE with proper CONTROL MSGs
			if (masterSender_ == null) {
				masterSender_ = new RacaHttpSender(MASTER_QUEUE_NAME + sessionID, sessionID);
			}

			// CHECK IF FIFO really ??
			masterSender_.send(MASTER_ACK_LOG_MSG + '|' + attendee_.getClientID(), attendee_.getClientID());

			try {

				log("Will now hit RACA Mediator URL..." + '\n');

				URL racaMediatorURL = new URL(MEDIATORPROXY_URL	+ "racamasterqueueproxy" + "?"+ MEDIATORPROXY_REQ_ID_TAG + '='
						+ MEDIATORPROXY_MASTER_UPDATE_TAG + "&"	+ MEDIATORPROXY_CLIENT_ID_TAG
						+ '=' + clientID);

				URLConnection racaMediatorConn = racaMediatorURL.openConnection();
				
				BufferedReader buffReader = new BufferedReader(new InputStreamReader(racaMediatorConn.getInputStream()));

				/*
				StringBuffer fullText = new StringBuffer();
				Boolean buffFlag = false;*/
				
				String text = new String();
				while ((text = buffReader.readLine()) != null) {

				}

			}catch (Exception exc) {
				exc.printStackTrace();
			}
	}

	
	public void resetMasterQueue(String sessionID, String clientID) {

		/** RESETs THE MASTER_QUEUE STATUS // NON PERSISTENT */

		if (masterResetConsumer_ == null){

			log("WARN : a Http Polling context will be started...");

			try {
					                                       // RacaSubscriberParser() ---------
				masterResetConsumer_ = new RacaHttpPoller(new RacaCommandParser(MASTER_QUEUE_NAME,sessionID),
									RacaHttpPoller.buildSubscribeHitURL(MASTER_QUEUE_NAME + sessionID, sessionID), sessionID, clientID);
      
			} catch (MalformedURLException e) {

				e.printStackTrace();
				log("message erro...: "+ e.getMessage());
			}

		} else
			
			// resets and releases the MASTER_QUEUE for others to listen to...		
			
		masterResetConsumer_.stopsListening();
		
		masterResetConsumer_ = null;
		
		log("MASTER QUEUE has being reset...");

	}

	
	public boolean checkMasterQueue(String sessionID){
		
		/**
		 * REQUESTS TO BECOME MASTER 
		 * WILL BE A QUICK ACCESS METHOD TO THE
		 * MASTER_QUEUE
		 */
		boolean gotMaster = false;

		log("CHECK MASTER QUEUE has returned : " + gotMaster + " for RACA-SESSION with ID : " + sessionID + '\n');

		return gotMaster;
	}

	
	public void sendCommand(String commandDesc) {

		commandPublisher_.publish(RacaStringUtil.trimURL(commandDesc));

		log("COMMAND EXECUTED : = "+ RacaStringUtil.extractCommandName(commandDesc));
	}

	
	public void sendCommand(Object obj) {

		commandPublisher_.publish(obj);

		log("Object successfully published ! ");

	}


	public void unsubscribeAsMaster(){
		
		if (commandPublisher_ != null) {
            commandPublisher_.close();
            commandPublisher_ = null;            
        }
        
        if (commandSubscriberThread_ != null) {
            commandSubscriberThread_.unsubscribe();
            commandSubscriberThread_ = null;
        }
        
        if (masterAckSubscriberThread_ != null) {
            masterAckSubscriberThread_.unsubscribe();
            masterAckSubscriberThread_ = null;
        }

		if (pupilAckSubscriberThread_ != null) {
            pupilAckSubscriberThread_.unsubscribe();
            pupilAckSubscriberThread_ = null;
        }        
        
        if (masterReqSubscriberThread_ != null) {
            masterReqSubscriberThread_.unsubscribe();
            masterReqSubscriberThread_ = null;
        }


        if (masterCheckConsumer_ != null) {
            masterCheckConsumer_.stopsListening();
            masterCheckConsumer_ = null;
        }
        
        if (masterResetConsumer_ != null) {
            masterResetConsumer_.stopsListening();
            masterResetConsumer_ = null;
        }

        if (masterSender_ != null) {
            masterSender_.close();
            masterSender_ = null;
        }
        
        
        if (masterRequestPublisher_ != null) {
            masterRequestPublisher_.close();
            masterRequestPublisher_ = null;
        }
        
        try {
                // needs time for reset
                Thread.sleep((long)1000);
            }
        catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            log("All Connections closed for MASTER session ...");
            // TODO notify that SESSION has no MASTER now...
                   
        		
	}
	
	public void unsubscribe(){
		
		if (commandPublisher_ != null) {
            commandPublisher_.close();
            commandPublisher_ = null;            
        }
        
        if (commandSubscriberThread_ != null) {
            commandSubscriberThread_.unsubscribe();
            commandSubscriberThread_ = null;
        }
		
		if (pupilRequestSender_ != null) {
            pupilRequestSender_.close();
            pupilRequestSender_ = null;
        }
		
		if (pupilAckSubscriberThread_ != null) {
            pupilAckSubscriberThread_.unsubscribe();
            pupilAckSubscriberThread_ = null;
        }

        if (masterAckSubscriberThread_ != null) {
            masterAckSubscriberThread_.unsubscribe();
            masterAckSubscriberThread_ = null;
        }

		
		
		try {
        
			// needs time for reset
            Thread.sleep((long)1000);

		}catch (InterruptedException ex) {
            ex.printStackTrace();
        }
		
            log("All Connections closed for SLAVE session ...");   

	}

}
