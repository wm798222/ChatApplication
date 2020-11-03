package common.msg;

import common.IChatUser;
import provided.datapacket.DataPacket;

/**
 * Data packets to be sent at the chatroom level.
 * 
 * @param <M> Type of message.
 *
 */
public class ChatRoomDataPacket<M extends IChatRoomMsg> extends DataPacket<M, IChatUser> {

	/**
	 * Serialization number.
	 */
	private static final long serialVersionUID = 7199689912803417968L;

	/**
	 * Constructor for the class.
	 * @param data message to be sent.
	 * @param sender IChatUser sending the message.
	 */
	public ChatRoomDataPacket(M data, IChatUser sender) {
		super(data, sender);
	}

}
