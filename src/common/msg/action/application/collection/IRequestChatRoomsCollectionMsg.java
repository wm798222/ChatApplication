package common.msg.action.application.collection;

import common.IChatRoom;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Requests a collection of chat rooms receiver is currently in
 * 
 *
 */
public interface IRequestChatRoomsCollectionMsg extends IRequestCollectionMsg<IChatRoom>{

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IRequestChatRoomsCollectionMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IRequestChatRoomsCollectionMsg.GetID();
	}	
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @return An IRequestChatRoomsCollectionMsg object
	 */
	static IRequestChatRoomsCollectionMsg make() {
		return new IRequestChatRoomsCollectionMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 123456789L;
		
		};
	}
	
}
