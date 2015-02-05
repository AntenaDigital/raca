package raca.util.client;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import raca.client.RacaAttendee;

public class GetsSmart {

	public static void main(String[] args) throws MalformedURLException {
		
		String sessao = "5401";
				
		RacaAttendee attendee1 = new RacaAttendee("9090", "black", "680 420");		
		attendee1.joinSessionAsMaster(sessao);
		
		RacaAttendee attendee2 = new RacaAttendee("9090", "blue", "680 420");
		attendee2.joinSession(sessao);
	}

}
