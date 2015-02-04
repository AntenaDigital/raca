package raca.util.client;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import raca.client.RacaAttendee;

public class GetsSmart {

	public static void main(String[] args) throws MalformedURLException {
		
		String sessao = "5401";
				
		RacaAttendee attendee = new RacaAttendee("9090", "black");
		
		attendee.joinSessionAsMaster(sessao);
		
	}

}
