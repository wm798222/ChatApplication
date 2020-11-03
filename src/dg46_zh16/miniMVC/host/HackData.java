package dg46_zh16.miniMVC.host;

/**
 * Implementation for IHackData
 *
 */
public class HackData implements IHackData {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = -1395847023790878813L;

	/**
	 * field of message
	 */
	String msg;

	/**
	 * constructor
	 * @param mess message you want to send
	 */
	public HackData(String mess) {
		this.msg = mess;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
