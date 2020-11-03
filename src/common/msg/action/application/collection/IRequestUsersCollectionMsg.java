package common.msg.action.application.collection;

import common.IUser;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Requests a collection of Users receiver is currently connected to
 * 
 *
 */
public interface IRequestUsersCollectionMsg extends IRequestCollectionMsg<IUser> {

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IRequestUsersCollectionMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IRequestUsersCollectionMsg.GetID();
	}
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @return An IRequestChatRoomsCollectionMsg object
	 */
	static IRequestUsersCollectionMsg make() {
		return new IRequestUsersCollectionMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 9484283L;
	
		};
	}
	
}
