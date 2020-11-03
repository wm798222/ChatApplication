package common.msg.status;

import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * A message that lets the user know an action was successful.
 * 
 *
 */
public interface ISuccessMsg extends IStatusMsg {
	/**
	 * Retrieve the ID value directly from the interface.
	 * getID() merely delegates to this method.
	 * @return The host ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(ISuccessMsg.class);
	}
	
	@Override
	public default IDataPacketID getID() {
		return ISuccessMsg.GetID();
	}	
	
	/**
	 * Factory method to create the data type from an input value.   
	 * The problem with this technique is while it does hide the data type
	 * implementation, defining it at this interface level means that 
	 * the implementation is invariant, which is an unnecessary restriction.
	 * @param text description of the message
	 * @return An ISuccessMsg object
	 */
	static ISuccessMsg make(final String text) {
		return new ISuccessMsg() {

			/**
			 * Version number for serialization.
			 */
			private static final long serialVersionUID = 7650256718685508984L;

			@Override
			public String toString() {
				return text;
			}

			@Override
			public String getDescription() {
				return text;
			}			
		};
	}
}
