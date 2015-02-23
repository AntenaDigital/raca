package raca.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.MalformedURLException;

/**
*
* @author ANTENA DIGITAL
*/
public class RacaMasterReqDialog extends JDialog {

	private String sessionID_;
	private String reqClientID_;
	private RacaAttendee attendee_ = null;

	JPanel mainPanel = new JPanel();
	JPanel infoPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();

	JButton allowButton = new JButton("Allow");
	JButton denyButton = new JButton("Deny");

	BorderLayout gridLayout = new BorderLayout();

	public JLabel infoLabel = new JLabel("A request for MASTER lock has arrived from : ");

	public RacaMasterReqDialog(String reqClientID, RacaAttendee attendee, String sessionID) {

		try {

			init(reqClientID, attendee, sessionID);

			setLocationRelativeTo(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void init(String reqClientID, RacaAttendee attendee, String sessionID) throws Exception {


		reqClientID_ = new String(reqClientID);
		attendee_ = attendee;
		sessionID_ = new String(sessionID);
		
		setResizable(false);
		setTitle("RPn Session Access Grant");

		infoLabel.setText(infoLabel.getText() + reqClientID_);
		infoPanel.add(infoLabel);

		buttonsPanel.add(allowButton);
		buttonsPanel.add(denyButton);

		mainPanel.setLayout(gridLayout);
		mainPanel.add(infoPanel,BorderLayout.NORTH);
		mainPanel.add(buttonsPanel,BorderLayout.SOUTH);

		getContentPane().add(mainPanel);

		allowButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				allowButton_actionPerformed(e);
			}
		});

		denyButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				denyButton_actionPerformed(e);
			}
		});

		pack();

	}

	void allowButton_actionPerformed(ActionEvent e) {

		// MASTER leaves the session...
		attendee_.quitSession(sessionID_);
		
		
		// notifies of the new MASTER...
		RacaHttpPublisher publisher = new RacaHttpPublisher(RacaNetworkProxy.MASTER_ACK_TOPIC_NAME, sessionID_);

		publisher.publish(RacaNetworkProxy.MASTER_ACK_LOG_MSG + '|' + reqClientID_);
		publisher.close();

		
		// reconnect as PUPIL
		try {
		
			attendee_.joinSession(sessionID_);
		
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		
		//RacaNetworkProxy.instance().connect(RacaNetworkProxy.instance().clientID(),false,RacaNetworkProxy.instance().isFirewalled(),mkp.MKPGlassFrame.ASPECT_RATIO);

		dispose();

	}

	void denyButton_actionPerformed(ActionEvent e) {

		dispose();
	}

}