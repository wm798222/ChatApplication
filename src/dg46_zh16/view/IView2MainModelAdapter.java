package dg46_zh16.view;

import java.rmi.RemoteException;
import java.util.Collection;

import common.*;

/**
 * Interface for view to main model adapter
 *
 */
public interface IView2MainModelAdapter {
	/**
	 * quit model
	 */
	public void quit();

	/**
	 * Create chat room in model
	 * @param chatroomTitle title of the chat room 
	 * @throws RemoteException remote exception
	 */
	public void createChatroom(String chatroomTitle) throws RemoteException;

	/**
	 * set user name in model 
	 * @param username user name you want to set
	 */
	public void setUsername(String username);

	/**
	 * Connect to user using IP
	 * @param hostIP IP Address
	 */
	public void connectTo(String hostIP);

	/**
	 * start discovery server
	 * @param username your name appear on discovery server
	 */
	public void startDiscovery(String username);

	/**
	 * request chat rooms from a user
	 * @param user user that you want to get chat rooms that it's in
	 */
	public void requestChatRooms(IUser user); //Note: this returns void because we don't want to deadlock if remote doesnt respond. 

	/**
	 * invite user to a chat room 
	 * @param user user you want to invite 
	 * @param room room you want to invite the user to
	 */
	public void inviteTo(IUser user, IChatRoom room); //Note: this returns void because we don't want to deadlock if remote doesnt respond.

	/**
	 * request to join a room 
	 * @param userStub your user stub
	 * @param room room you want to join
	 */
	public void requestToJoin(IUser userStub, IChatRoom room);

	/**
	 * get your connected users
	 * @return a collection of users that you are connected to
	 */
	public Collection<IUser> getConnectedUsers();

	/**
	 * request user
	 * @param selectedItem selected user you want to request
	 */
	public void requestUsers(IUser selectedItem);

	/**
	 * connect to a user on the server
	 * @param receiverConnStub user you want to connect to
	 */
	public void connectTo(IUser receiverConnStub);
}
