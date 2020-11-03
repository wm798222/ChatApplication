package dg46_zh16.miniMVC;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import common.IChatRoom;
import common.IChatUser;
import common.msg.*;
import java.util.UUID;

/**
 * Implementation of IChatRoom
 *
 */
public class ChatRoom implements IChatRoom {

	/**
	* 
	*/
	private static final long serialVersionUID = 7559065451354007959L;

	/**
	  * field for the name of the ChatRoom
	  */
	private String roomName;

	/**
	 * the id of the chatroom
	 */
	private UUID uniqueID;

	/**
	 * A collections of IChatRoomMember
	 */
	private Collection<IChatUser> members;

	/**
	 * constructor with only roomName as input
	 * @param roomName Name of the Room
	 * @param members - member
	 */
	public ChatRoom(String roomName, Collection<IChatUser> members) {
		this(roomName, UUID.randomUUID(), new HashSet<IChatUser>(members));
	}

	/**
	 * constructor with roomName and id as input
	 * @param roomName2 room name
	 * @param randomUUID room id
	 * @param members - members
	 */
	public ChatRoom(String roomName2, UUID randomUUID, Collection<IChatUser> members) {
		this.roomName = roomName2;
		this.uniqueID = randomUUID;
		this.members = new HashSet<IChatUser>(members);
	}

	/**
	 * 
	 * method to send message to a chat room. It takes in a message and call receive data on all the 
	 * users in the chat room
	 * @param msg input message that sender wants to send
	 */
	public void sendChatRoomMsg(ChatRoomDataPacket<? extends IChatRoomMsg> msg) {
		System.out.println("Room: sending out the text to everyone in the room:" + members.size());
		// get a copy of all the members
		ArrayList<IChatUser> memberCopy = new ArrayList<>();
		for (IChatUser singleMember : members) {
			memberCopy.add(singleMember);
		}
		IChatRoomMsg data = msg.getData();
		IChatUser sender = msg.getSender();

		for (IChatUser singleCopyMember : memberCopy) {
			Thread newThread = new Thread() {
				public void run() {
					// Send the data packet to the chat room
					try {
						singleCopyMember.receiveChatRoomMsg(new ChatRoomDataPacket<IChatRoomMsg>(data, sender));
					} catch (RemoteException e) {
						// if we cannot send, remove the member that cannot send msg to
						members.remove(singleCopyMember);
						e.printStackTrace();
					}
					// looping 

				}
			};
			//threadList.add(newThread);
			newThread.start(); // start the new thread
		}
	}

	/**
	 * add member to member list of the room
	 * @param newMem new user that you want to add
	 */
	public void addMember(IChatUser newMem) {
		members.add(newMem);
	}

	/**
	 * remove member from the room
	 * @param mem user that you want to remove
	 */
	public void removeMember(IChatUser mem) {
		members.remove(mem); //given o, if an element e s.t. Objects.equals(o, e) exists, remove e
	}

	@Override
	public Collection<IChatUser> getChatUsers() {
		return members;
	}

	@Override
	public String getName() {
		return roomName;
	}

	@Override
	public UUID getId() {
		return uniqueID;
	}

}