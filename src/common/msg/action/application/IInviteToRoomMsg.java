package common.msg.action.application;

import common.IChatRoom;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Invite a user to chat room
 * 
 *
 */
public interface IInviteToRoomMsg extends IUserActionMsg {

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IInviteToRoomMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IInviteToRoomMsg.GetID();
	}	
	
	/**
	 * @return Chat room to invite user to
	 */
	public IChatRoom getChatRoom();
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param room to invite user to
	 * @return An IInviteToRoomMsg object
	 */
	static IInviteToRoomMsg make(final IChatRoom room) {
		return new IInviteToRoomMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 32098423487893L;

			@Override
			public String toString() {
				return room.toString();
			}

			@Override
			public IChatRoom getChatRoom() {
				return room;
			}			
		};
	}
	
}
