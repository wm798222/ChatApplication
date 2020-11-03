package common.msg.action.application;

import common.IUser;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Sends message so that receiver can connect to sender
 * 
 *
 */
public interface IConnectMsg extends IUserActionMsg {

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IConnectMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IConnectMsg.GetID();
	}	
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param requestor user sending the IConnectMsg
	 * @return An IConnectMsg object
	 */
	static IConnectMsg make(final IUser requestor) {
		return new IConnectMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 231242332544323L;

			@Override
			public String toString() {
				return requestor.toString();
			}
			
		};
	}
	
}
