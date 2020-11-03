package dg46_zh16.miniMVC;

import javax.swing.JPanel;
import common.*;

import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.io.File;

import java.util.function.Supplier;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * MiniModel view that represents the view of a chat room
 *
 */
public class MiniView extends JPanel {
	/**
	 * serial version ID
	 */
	private static final long serialVersionUID = 1689474851737756398L;
	/**
	 * input field for message
	 */
	private final JTextField msgField = new JTextField();

	//	private final JScrollPane messageScrollPane = new JScrollPane();
	/**
	 * button to send text message
	 */
	private final JButton btnSend = new JButton("Send Text");

	/**
	 * panel for buttons
	 */
	private final JPanel buttonPnl = new JPanel();

	/**
	 * button for exit chat room 
	 */
	private final JButton btnExitChatRoom = new JButton("Exit");

	/**
	 * button to send file
	 */
	private final JButton btnSendFile = new JButton("Send File...");

	/**
	 * view to model adapter
	 */
	private ImView2mModelAdapter view2model;

	/**
	 * text area
	 */
	private final JTextArea textArea = new JTextArea();

	/**
	 * button to send unknown message 
	 */
	private final JButton btnSendunknownmsg = new JButton("Send Pikachu");

	/**
	 * button to send unknown hack message 
	 */
	private final JButton btnHackMessage = new JButton("Hack");

	/**
	 * start mini view
	 */
	public void start() {
		this.setVisible(true);
	}

	/**
	 * constructor for miniview
	 * @param view2model view to model adapter
	 * @param mini2main mini to main adapter
	 * @param chatroomID  chatroom ID
	 */
	public MiniView(ImView2mModelAdapter view2model, IMini2MainAdapter mini2main, String chatroomID) {
		this.view2model = view2model;
		initGUI();
	}

	/**
	 * init gui for view
	 */
	public void initGUI() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 344, 313, 0 };
		gridBagLayout.rowHeights = new int[] { 370, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		add(textArea, gbc_textArea);
		msgField.setToolTipText("Text message you want to send in room");

		//-----------------------------Main MESSAGE area --------------
		//		mainSplit.setRightComponent(messageScrollPane);
		//		messageScrollPane.setViewportBorder(new LineBorder(Color.RED));
		msgField.setText("Hit Enter to send text message");
		msgField.setBackground(new Color(255, 255, 255));
		msgField.setColumns(10);

		//-----------------------------sending message area--------------
		GridBagConstraints gbc_msgField = new GridBagConstraints();
		gbc_msgField.insets = new Insets(0, 0, 0, 5);
		gbc_msgField.fill = GridBagConstraints.BOTH;
		gbc_msgField.gridx = 0;
		gbc_msgField.gridy = 1;
		add(msgField, gbc_msgField);
		msgField.addActionListener(new ActionListener() {
			//Hit Enter to send message!
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == msgField) {
					btnSend.doClick();
					msgField.setText("");
				}
			}
		});

		GridBagConstraints gbc_buttonPnl = new GridBagConstraints();
		gbc_buttonPnl.fill = GridBagConstraints.BOTH;
		gbc_buttonPnl.gridx = 1;
		gbc_buttonPnl.gridy = 1;
		add(buttonPnl, gbc_buttonPnl);
		GridBagLayout gbl_buttonPnl = new GridBagLayout();
		gbl_buttonPnl.columnWidths = new int[] { 65, 51, 81, 57, 0 };
		gbl_buttonPnl.rowHeights = new int[] { 23, 0 };
		gbl_buttonPnl.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_buttonPnl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		buttonPnl.setLayout(gbl_buttonPnl);
		GridBagConstraints gbc_btnExitChatRoom = new GridBagConstraints();
		gbc_btnExitChatRoom.insets = new Insets(0, 0, 0, 5);
		gbc_btnExitChatRoom.gridx = 0;
		gbc_btnExitChatRoom.gridy = 0;
		buttonPnl.add(btnExitChatRoom, gbc_btnExitChatRoom);
		btnExitChatRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2model.exitRoom();
			}
		});
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.showDialog(null, "Please Select the File");
				jfc.setVisible(true);
				File filename = jfc.getSelectedFile();
				System.out.println("File name; " + filename.getAbsolutePath());
				view2model.sendImage(filename.getAbsolutePath());
			}
		});
		btnSend.setToolTipText("Click to send text");
		btnSend.setVerticalAlignment(SwingConstants.TOP);
		btnSend.setHorizontalAlignment(SwingConstants.LEFT);
		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("MiniView: button clicked! sending text");
				view2model.sendText(msgField.getText());
				msgField.setText("");
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		buttonPnl.add(btnSend, gbc_btnSend);
		GridBagConstraints gbc_btnSendunknownmsg = new GridBagConstraints();
		gbc_btnSendunknownmsg.insets = new Insets(0, 0, 0, 5);
		gbc_btnSendunknownmsg.gridx = 2;
		gbc_btnSendunknownmsg.gridy = 0;
		btnSendunknownmsg.setToolTipText("Click to send Pikachu!");
		buttonPnl.add(btnSendunknownmsg, gbc_btnSendunknownmsg);
		btnSendunknownmsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//view2model.sendUnknownText(msgField.getText());
				view2model.sendImage(msgField.getText());
			}
		});
		//buttonPnl.add(btnSendFile);

		GridBagConstraints gbc_btnHackMessage = new GridBagConstraints();
		gbc_btnHackMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHackMessage.gridx = 3;
		gbc_btnHackMessage.gridy = 0;
		btnHackMessage.setToolTipText("Type in message in text field and click this to do some fun stuff");
		buttonPnl.add(btnHackMessage, gbc_btnHackMessage);
		btnHackMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2model.sendHackMsg(msgField.getText());
			}
		});

	}


	/**
	 * display text on view 
	 * @param text text to display
	 * @param sender send of the message
	 */
	public void displayText(String text, String sender) {

		System.out.println("MiniView: display text");
		textArea.append("[" + sender + "]: " + text + "\n");
		//		JTextPane txtPnl = new JTextPane();
		//		txtPnl.setText(text);
		//		messageScrollPane.setViewportView(new JPanel().add(txtPnl));
	}

	/**
	 * get room from the view
	 * @return IChatRoom object 
	 */
	public IChatRoom getRoom() { //called by the MainView, that doesn't have access to MiniModel
		return view2model.getRoom();
	}

	/**
	 * create a pop-up window on the view
	 * @param componentFac factory to make a JComponent
	 */
	public void buildComponent(Supplier<JComponent> componentFac) {
		//factory.make the component
		//install the component! (show that JFrame)

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					JComponent component = componentFac.get();
					JFrame messageFrame = new JFrame("New GUI Component");
					messageFrame.getContentPane().add(component);
					messageFrame.setPreferredSize(new Dimension(500, 500));
					messageFrame.pack();
					messageFrame.setLocationRelativeTo(null);
					messageFrame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
