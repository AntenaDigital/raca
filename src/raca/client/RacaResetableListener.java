package raca.client;

/**
*
* @author ANTENA DIGITAL
*/
public interface RacaResetableListener extends RacaMessageListener{

	void reset();
    boolean check();
	
}
