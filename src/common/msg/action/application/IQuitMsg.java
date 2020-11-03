package common.msg.action.application;

import common.IUser;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * Tells receiver to remove connection to sender.
 * Sent on leaving ChatApp
 * 
 *
 */
public interface IQuitMsg extends IUserActionMsg {

	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(IQuitMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return IQuitMsg.GetID();
	}	
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param requestor IUser that wants others to disconnect from them
	 * @return An ISQUitMsg object
	 */
	static IQuitMsg make(final IUser requestor) {
		
		return new IQuitMsg() {

			/**
			 * For Serialization.
			 */
			private static final long serialVersionUID = 32098423487893L;

			@Override
			public String toString() {
				return requestor.toString();
			}


		};
		
	}
	
	
}
