package dg46_zh16.miniMVC;

import java.util.Collection;
import common.*;

/**
 * 
 *
 */
public interface ImView2mModelAdapter {
	/**
	 * @param text - text to be send
	 */
	public void sendText(String text);

	/**
	 * @param text - text to be send
	 */
	public void sendUnknownText(String text);

	/**
	 * @return collection
	 */
	public Collection<IChatUser> getMembers();

	/**
	 * @return room
	 */
	public IChatRoom getRoom();

	/**
	 * 
	 */
	public void exitRoom();

	/**
	 * @param text - text
	 */
	public void sendImage(String text);

	/**
	 * @param text - text
	 */
	public void sendHackMsg(String text);
}
