package common.msg;

import provided.datapacket.ADataPacketAlgoCmd;

/**
 * A type-narrowed definition of the ADataPacketAlgoCmd. <br>
 * 
 * This type of command processes data packets containing
 * IMsg's and returns an IStatusMsg indicating the result of the processing.
 *
 * @param <M> message to be processed
 */
public abstract class AUserMsgCmd<M extends IUserMsg>
		extends ADataPacketAlgoCmd <Void, IUserMsg, Void, Void, UserDataPacket<M>> {
	
	/**
	 * Version number for serialization.
	 */
	private static final long serialVersionUID = 3251598138202580034L;
	
	@Override
	public void setCmd2ModelAdpt(Void cmd2ModelAdpt) {
		//This empty method is here because otherwise all subclasses of AUserMsgCmd would have to implement this method
		//(which would be empty anyway), since setCmd2ModelAdpt is inherited from ADataPacketAlgoCmd
	}

}