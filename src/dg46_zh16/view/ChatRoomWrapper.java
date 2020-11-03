package dg46_zh16.view;

import common.*;

/**
 * Wrapper Class for ChatRoom
 *
 */
public class ChatRoomWrapper {

	/**
	 * Object for Chat room 
	 */
	IChatRoom room;

	/**
	 * constructor
	 * @param room input room
	 */
	public ChatRoomWrapper(IChatRoom room) {
		this.room = room;
	}

	/**
	 * get room name as string
	 */
	public String toString() {
		return room.getName();
	}

	/**
	 * Getter for IChatRoom
	 * @return the room object
	 */
	public IChatRoom get() {
		return room;
	}
}
