package raca.client;

import java.net.MalformedURLException;


/**
*
* @author ANTENA DIGITAL
*/
public interface RacaMessageParser {

	void parse(Object obj);
	String topicName();
	String hitURL() throws MalformedURLException;
}
