package dg46_zh16.miniMVC.host;

import common.msg.AChatMsgCmd;
import common.msg.ChatRoomDataPacket;
import common.msg.content.IStringMsg;
import provided.datapacket.IDataPacketID;

/**
 * Command for hack
 *
 */
public class HackCmd extends AChatMsgCmd<IHackData> {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = -6084099983744109420L;

	/**
	 * constructor
	 */
	public HackCmd() {

	}

	@Override
	public Void apply(IDataPacketID index, ChatRoomDataPacket<IHackData> host, Void... params) {
		this.cmd2model.sendMessageToRoom(IStringMsg.make(host.getData().getMessage()));
		return null;
	}

}
