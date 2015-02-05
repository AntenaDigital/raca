package raca.client;

import java.util.logging.Level;
import java.util.logging.Logger;

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
		
		if (obj instanceof String) {

			/*try {
				 COMMAND MESSAGES PARSING

				mkp.MKPCommandModule.init(XMLReaderFactory.createXMLReader(), new StringBufferInputStream((String) obj));

			} catch (SAXException ex) {

				Logger.getLogger(RacaCommandParser.class.getName()).log(Level.SEVERE, null, ex);
			}

*/		}

		/*else if (obj instanceof SerializablePathIterator) { 


			try {

				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "This is a PATH obj... !");

				if (obj == null) System.out.println("and it is null...");

				SerializablePathIterator it = (SerializablePathIterator) obj;
				MKPGlassFrame.instance().execDrawCommand(it);

			} catch (Exception e) {

				e.printStackTrace();
			}            
		}

		else {
		*/

			try {

				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "This is an IMAGE obj... !");

				//SerializableBufferedImage bi = (SerializableBufferedImage) obj;
				//MKPGlassFrame.instance().execSetPadBackgroundCommand(bi);               

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}
//}
