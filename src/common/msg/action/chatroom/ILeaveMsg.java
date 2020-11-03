package common.msg.action.chatroom;

import common.IChatUser;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * A message that lets a user leave a chat room. Process it by removing the sender’s 
 * IChatUser stub from the receiver’s list of chat room members for a given chat room.
 * 
 *
 */
public interface ILeaveMsg extends IChatRoomActionMsg {
	
	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(ILeaveMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return ILeaveMsg.GetID();
	}	
	
	/**
	 * Get user requesting to leave a chat room.
	 * @return user requesting to leave a chat room.
	 */
	public IChatUser getUserToLeave();
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param requestor user requesting to leave a chat room.
	 * @return An ILeaveMsg object
	 */
	static ILeaveMsg make(final IChatUser requestor) {
		return new ILeaveMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 3254203217630041443L;

			@Override
			public String toString() {
				return requestor.toString();
			}

			@Override
			public IChatUser getUserToLeave() {
				return requestor;
			}			
		};
	}
}
