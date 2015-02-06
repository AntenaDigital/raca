package raca.client;

import javax.swing.JDialog;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaNetworkDialog extends JDialog { //COMENTAR O *implements PropertyChangeListener*
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static RacaNetworkDialog instance_= null;
	public static String TITLE = "RACA Network Control - ";
	
	public static RacaNetworkDialog instance(){
		
		if(instance_ == null){
			instance_ = new RacaNetworkDialog();
		}
		
		return instance_;
	}
	
	
	
	
}
