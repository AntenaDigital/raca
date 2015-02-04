package raca.client;


/**
*
* @author ANTENA DIGITAL
*/
public interface RacaMessageParser {

	void parse(Object obj);
	String topicName();
	String hitURL();
}
