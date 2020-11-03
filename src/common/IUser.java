package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.msg.IUserMsg;
import common.msg.UserDataPacket;

/**
 * Persistent user for a single ChatApp. Exists in ChatApp’s registry
 * for others to find and connect to.
 * 
 *
 */
public interface IUser extends Remote {
	
	/**
	 * The name the ICompute object will be bound to in the RMI Registry
	 */
	public static final String BOUND_NAME = "User";
	
	/**
	 * Get the name of this user.
	 * @return String name of the user.
	 * @throws RemoteException if remote communication failed
	 */
	public String getName() throws RemoteException;
	
	 /**
      * Receive a message sent to the IUser and adds it to processing queue
      * @param msg - data packet containing the IMsg being received
      * @throws RemoteException if remote communication failed             
      */
     public void receiveApplicationMsg(UserDataPacket<? extends IUserMsg> msg) throws RemoteException;

}
