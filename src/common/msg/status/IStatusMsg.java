package common.msg.status;

import common.msg.IChatRoomMsg;
import common.msg.IUserMsg;

/**
 * A message indicating the result of sending one of the other type of messages, i.e. an IContentMsg or IActionMsg.
 * 
 *
 */
public interface IStatusMsg extends IUserMsg, IChatRoomMsg {
	
	/**
	 * Get the description of the message.
	 * @return message
	 */
	public String getDescription();
	
}
