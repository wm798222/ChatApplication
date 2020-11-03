package common.msg.action.application.collection;

import java.util.Collection;

import common.IChatRoom;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Sends a collection of IChatRooms that sender is in
 * 
 *
 */
public interface ISendChatRoomsCollectionMsg extends IAddCollectionMsg<IChatRoom>{

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(ISendChatRoomsCollectionMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return ISendChatRoomsCollectionMsg.GetID();
	}	
	
	@Override
	public Collection<IChatRoom> getCollection();
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param rooms A Collection of chat rooms sender is in
	 * @return An IAddChatRoomsCollectionMsg object
	 */
	static ISendChatRoomsCollectionMsg make(final Collection<IChatRoom> rooms) {
		
		return new ISendChatRoomsCollectionMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 2409980483238L;

			@Override
			public String toString() {
				return rooms.toString();
			}

			@Override
			public Collection<IChatRoom> getCollection() {
				return rooms;
			}		
			
		};
	}
	
}
