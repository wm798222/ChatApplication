package dg46_zh16.model;

import provided.datapacket.DataPacketAlgo;

import common.msg.AUserMsgCmd;
import common.msg.IUserMsg;

/**
 * 
 *
 */
public abstract class MyAppAlgo extends DataPacketAlgo<Void, Void> {

	//	HashMap<IDataPacketID, ArrayList<DataPacket<IMsg, IChatUser>>> cachedMessage = new HashMap<IDataPacketID, ArrayList<DataPacket<IMsg, IChatUser>>>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110832671930670259L;

	/**
	 * @param defaultCmd - the default command
	 */
	public MyAppAlgo(AUserMsgCmd<IUserMsg> defaultCmd) {
		super(defaultCmd);
		init();
	}

	/**
	 * 
	 */
	abstract public void init();
}
