	package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.msg.ChatRoomDataPacket;
import common.msg.IChatRoomMsg;

/**
 * User for a ChatApp in a chat room
 * 
 *
 */
public interface IChatUser extends Remote {

	/**
	 * Receive a message sent to the IChatUser and adds it to processing queue.
	 * @param msg - data packet containing the message being received
	 * @throws RemoteException if remote communication fails.
	 */
	public void receiveChatRoomMsg(ChatRoomDataPacket<? extends IChatRoomMsg> msg) throws RemoteException;
	
	
	/**
	 * Retrieve the name of this IChatUser.
	 * @return String name of the user
	 * @throws RemoteException As sending resulting String through proxy.
	 */
	public String getName() throws RemoteException;
	
		

}