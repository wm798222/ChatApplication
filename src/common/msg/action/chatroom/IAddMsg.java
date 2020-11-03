package common.msg.action.chatroom;

import common.IChatUser;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * A message that lets a user be added to a chat room. Process it by adding the sender’s IChatUser
 * stub to the receiver’s list of chat room members for a given chat room. 
 * 
 *
 */
public interface IAddMsg extends IChatRoomActionMsg {
	
	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IAddMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IAddMsg.GetID();
	}	
	
	/**
	 * Get user requesting to be added to a chat room.
	 * @return user requesting to be added to a chat room.
	 */
	public IChatUser getUserToAdd();
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param requestor user requesting to be added to a chat room.
	 * @return An IConnectMsg object
	 */
	static IAddMsg make(final IChatUser requestor) {
		return new IAddMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 3254203217630041443L;

			@Override
			public String toString() {
				return requestor.toString();
			}

			@Override
			public IChatUser getUserToAdd() {
				return requestor;
			}			
		};
	}
}
