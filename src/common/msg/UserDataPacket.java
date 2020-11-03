package common.msg;

import common.IUser;
import provided.datapacket.DataPacket;

/**
 * Data packets to be sent at the user level.
 * 
 * @param <M> Type of message
 *
 */
public class UserDataPacket<M extends IUserMsg> extends DataPacket<M, IUser>{

	/**
	 * Serialization number.
	 */
	private static final long serialVersionUID = 8909253345124767510L;

	/**
	 * Constructor for the class.
	 * @param data Message to send at the user level.
	 * @param sender IUser sending the message.
	 */
	public UserDataPacket(M data, IUser sender) {
		super(data, sender);
	}

}
