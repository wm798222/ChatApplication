package common;

import java.util.function.Supplier;

import javax.swing.JComponent;

import common.msg.IChatRoomMsg;

/**
 * Adapter that enables a command to display a GUI component 
 * on the user interface of a local system.<br>
 * Whenever a command to process an unknown message is received, the local system should immediately give an 
 * instance of this adapter to the received command to connect it into 
 * the local system. Then the command should be installed into the message processing visitor. <br>
 *  
 * In the case of a foreign command (user 1) using this adapter (user 2) to send a message to another user (user 3),
 * the sender of the message to user 3 will be user 1
 * 
 * 
 *
 */
public interface ICmd2ModelAdapter {
	
	/**
	 * Displays the given GUI component on the local system's user interface.
	 * @param componentFac Factory that builds the GUI component to display. It is created by the same
	 * 		commands that process messages sent by other users.
	 * @param title The title of the newly created component
	 */
	public void displayComponent(Supplier<JComponent> componentFac, String title);

	/**
	 * Displays the given text on the local system's user interface (more specifically, the chat window text area.)
	 * @param text Text to display in the chat window text area.
	 */
	public void displayText(String text);
	
	/**
	 * Send a message to another IChatUser in the chat room.
	 * @param message Message to send to the other IChatUser.
	 * @param member The IChatUser to send a message to.
	 */
	public void sendMessageToMember(IChatRoomMsg message, IChatUser member);
	
	/**
	 * Send a message to all IChatUsers in the chat room.
	 * @param message Message to send to everyone in the chat room.
	 */
	public void sendMessageToRoom(IChatRoomMsg message);
	
	/**
	 * No-op singleton implementation of ICmd2ModelAdapter.
	 */
	public static final ICmd2ModelAdapter NULL_OBJECT = new ICmd2ModelAdapter() {

		@Override
		public void displayComponent(Supplier<JComponent> componentFac, String title) {}

		@Override
		public void displayText(String text) {}

		@Override
		public void sendMessageToMember(IChatRoomMsg message, IChatUser member) {}

		@Override
		public void sendMessageToRoom(IChatRoomMsg message) {}
		
	};

}
