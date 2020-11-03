package common;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Encapsulation of IChatUsers in a chat room.
 * 
 *
 */
public interface IChatRoom extends Serializable {
	/**
	 * @return Collection of IChatUsers in this IChatRoom
	 */
	public Collection<IChatUser> getChatUsers();
	
	/**
	 * Gets the name of this chatroom.
	 * @return String name of chatroom.
	 */
	public String getName();
	
	/**
	 * @return ID of the room
	 */
	public UUID getId();

}
