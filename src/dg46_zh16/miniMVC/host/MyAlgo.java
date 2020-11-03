package dg46_zh16.miniMVC.host;

import provided.datapacket.DataPacket;

import provided.datapacket.DataPacketAlgo;
import provided.datapacket.IDataPacketID;

import java.util.ArrayList;
import java.util.HashMap;

import common.*;
import common.msg.AChatMsgCmd;
import common.msg.IMsg;

/**
 * 
 * MyAlgo Visitor that extends DataPacketAlgo
 *
 */
public abstract class MyAlgo extends DataPacketAlgo<Void, Void> {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 6393353882621689733L;
	/**
	 * HashMap to store added commands
	 */
	HashMap<IDataPacketID, ArrayList<DataPacket<IMsg, IChatUser>>> cachedMessage = new HashMap<IDataPacketID, ArrayList<DataPacket<IMsg, IChatUser>>>();

	/**
	 * Constructor
	 * @param defaultCmd the default command
	 */
	public MyAlgo(AChatMsgCmd<?> defaultCmd) {
		super(defaultCmd);
		init();
	}

	/**
	 * init method
	 */
	abstract public void init();
}
