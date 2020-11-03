package common.msg.action.application.collection;

import java.util.Collection;

import common.IUser;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Requests a collection of IUsers that sender is connected to
 * 
 *
 */
public interface IAddUsersCollectionMsg extends IAddCollectionMsg<IUser>{

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IAddUsersCollectionMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IAddUsersCollectionMsg.GetID();
	}	
	
	@Override
	public Collection<IUser> getCollection();
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param users A Collection of users sender is connected to
	 * @return An IAddUsersCollectionMsg object
	 */
	static IAddUsersCollectionMsg make(final Collection<IUser> users) {
		return new IAddUsersCollectionMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 420982873248L;

			@Override
			public String toString() {
				return users.toString();
			}

			@Override
			public Collection<IUser> getCollection() {
				return users;
			}			
		};
	}
	
}
