package common.msg.action.application.collection;

import common.msg.action.application.IUserActionMsg;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * A message sent by a user requesting a collection of data from the receiver
 * @param <Data> Data to be sent
 * 
 *
 */
public interface IRequestCollectionMsg<Data> extends IUserActionMsg {
	
	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IRequestCollectionMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IRequestCollectionMsg.GetID();
	}
	
}
