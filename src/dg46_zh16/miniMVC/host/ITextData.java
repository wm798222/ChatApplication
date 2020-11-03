package dg46_zh16.miniMVC.host;

import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;
import common.msg.content.IContentMsg;

/**
 * An IDataPacketData that contains a command ID that the sender needs from the receiver. 
 * The expected processing of this data type is to send a response message with the 
 * command associated with the associated ID.  
 
 * Cannot make this through anon inner class because makeID requires .class to be unique. If you make anon inner class, you can't have one unique ID.
 *
 */
public interface ITextData extends IContentMsg {
	/**
	 * This method allows one to get the ID value directly from the interface.
	 * 
	 * The only difference between this code and any other data type's getID() code is the value of the 
	 * Class object being passed to the DataPacketIDFactory's makeID() method.    This has to be 
	 * specified here because this is the only place where the proper Class object is unequivocally known.
	 * 
	 *	@return The ID value associated with this data type.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(ITextData.class);   // DataPacketIDFactory.Singleton is an instance of an IDataPacketIDFactory
	}
	
	/**
	 * This method MUST be defined at this INTERFACE level so that any concrete implementation 
	 * will automatically have the ability to generate its proper host ID value.
	 * Since an instance method can call a static method but not the other way around, simply delegate to 
	 * the static method from here. 
	 * 
	 * NEVER override this method, as it defines an invariant for the data type.   Unfortunately, Java does not allow 
	 * one to define an invariant instance method at the interface level, i.e. this method cannot be made final.
	 */
	@Override
	public default IDataPacketID getID() {
		return ITextData.GetID();
	}
	
	/**
	 * Get the text 
	 * @return get the ID 
	 */
	public String getText();
	
	
	/**
	 * make method that takes in a string and return a ITextData
	 * @param text input text to make
	 * @return ITextData
	 */
	static ITextData make(final String text) {
		return new ITextData() {

			/**
			 * Version number for serialization.
			 */
			private static final long serialVersionUID = 2116930322237925265L;

			@Override
			public String toString() {
				return text;
			}

			@Override
			public String getText() {
				// TODO Auto-generated method stub
				return text;
			}			
		};
	}
}
