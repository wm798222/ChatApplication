package dg46_zh16.model;

import java.util.UUID;

import common.*;
import common.msg.ChatRoomDataPacket;
import common.msg.IChatRoomMsg;
import dg46_zh16.miniMVC.MiniView;

/**
 * Adapter from main mvc to mini mvc
 * 
 *
 */
public interface IMain2MiniAdapter {
	/**
	 * Getter for the chatroom of the mini MVC
	 * @return the Chat room for the mini MVC
	 */
	public IChatRoom getChatRoom();

	/**
	 * Getter for the IChatUser of the User in the miniMVC
	 * @return the local IChatUser object 
	 */
	public IChatUser getSelf();

	/**
	 * Getter for the proxy of the IChatuser of the User in the miniMVC
	 * @return te local IChatUser object's proxy
	 */
	public IChatUser getSelfStub();

	/**
	 * Getter for the The UUID of the chatroom 
	 * @return The UUID of the chatroom
	 */
	public UUID getCRID(); //

	/**
	 * Getter for the miniview of the miniMVC
	 * @return the chat panel of the chatroom
	 */
	public MiniView getPanel(); //called from main View to get the miniview created by main model

	/**
	 * A function to send a message in a particular chatroom from the main MVC
	 * @param chatRoomDataPacket - the message to be sent
	 */
	public void sendMsg(ChatRoomDataPacket<? extends IChatRoomMsg> chatRoomDataPacket);

	/**
	 * A function to exit the chatroom from the main MVC
	 */
	public void exitChatRoom();

}
