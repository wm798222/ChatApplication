package common.msg.action.application.collection;

import java.util.Collection;

import common.msg.action.application.IUserActionMsg;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * A message containing requested information about the IUser sender in the form of a collection.
 * @param <Data> Data to be sent back
 *
 *
 */
public interface IAddCollectionMsg<Data> extends IUserActionMsg {
	
	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IAddCollectionMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IAddCollectionMsg.GetID();
	}	
	
	/**
	 * @return Collection of Data objects
	 */
	public Collection<Data> getCollection();
	
}
