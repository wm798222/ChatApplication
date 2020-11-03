package dg46_zh16.miniMVC;

/**
 * IMini2MainAdapter that allows mini model to user things in the main model.
 */
public interface IMini2MainAdapter {
	/**
	 * to the user name
	 * @return String for the user name
	 */
	public String getUsername();

	/**
	 * alertWindow 
	 * @param description string description
	 */
	public void alertWindow(String description);

	/**
	 * remove ChatRoom in main
	 * @param room the chat room that you want to remove
	 */
	public void removeCR(ChatRoom room);
}
