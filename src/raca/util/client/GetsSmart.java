package raca.util.client;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import raca.client.RacaAttendee;

public class GetsSmart {

	public static void main(String[] args) throws MalformedURLException {
		
		Random ale = new Random();
		
		int num = ale.nextInt(100);
		
		String sessao = "2015" + String.valueOf(num);
				
		RacaAttendee attendee1 = new RacaAttendee("0298", "black", "680 420");		
		attendee1.joinSessionAsMaster(sessao);
		attendee1.getProxy_().sendCommand("um trecho qualquer...");
	
		
		RacaAttendee attendee2 = new RacaAttendee("1209", "blue", "680 420");
		attendee2.joinSession(sessao);
		
		
	}

}
