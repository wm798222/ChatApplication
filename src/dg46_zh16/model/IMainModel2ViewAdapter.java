package dg46_zh16.model;

import java.util.Collection;
import java.util.UUID;

import common.*;
import dg46_zh16.miniMVC.IMiniControllerFac;
import dg46_zh16.miniMVC.IMini2MainAdapter;
import dg46_zh16.miniMVC.MiniController;
import dg46_zh16.miniMVC.MiniView;

/**
 * Model to View Adapter has a factory method that makes a mini-controller returning an adapter to the resultant mini-MVC. 
 * 
 *
 */
public interface IMainModel2ViewAdapter {

	/**
	 * factory method that makes a mini-controller returning an adapter to the resultant mini-MVC. 
	 */
	IMiniControllerFac miniControllerFac = new IMiniControllerFac() {
		@Override
		public IMain2MiniAdapter make(IMini2MainAdapter mini2main, String chatroomName, UUID CRID,
				Collection<IChatUser> members) {
			MiniController miniController = new MiniController(mini2main, chatroomName, CRID, members);
			miniController.start();
			IMain2MiniAdapter main2mini = miniController.getMainMVCAdapt();
			return main2mini;
		}
	};

	/**
	 * STARTing the discovery panel
	 */
	void startDiscoveryPanel();

	/**
	 * @param allStubs - the list of users its connected to
	 */
	void refreshDropBox(Collection<IUser> allStubs);

	/**
	 * @param listOfCR - list of chatrooms the user is in
	 */
	public void refreshNotYetInChatRoom(Collection<IChatRoom> listOfCR);

	/**
	 * @param chatroomName - the chatroom name of the chatroom to be created
	 * @param CRID - the uuid of the chatroom to be created
	 * @param members - the members in the chatroom to be created
	 * @return an adapter to allo main to talk to this new chatroom
	 */
	public IMain2MiniAdapter makeMiniMVC(String chatroomName, UUID CRID, Collection<IChatUser> members);

	/**
	 * @param panel - the miniview panel to be installed
	 * @param title - the chatroom name that will be the title
	 */
	public void addTab(MiniView panel, String title);

	/**
	 * @param description - the text for the alter window
	 */
	public void alertWindow(String description);

	/**
	 * @param desciption - the text for the request window
	 * @param title - the title of the pop up window
	 * @return 0 if the answer is yes, 1 if the answer is no. 
	 */
	public int requestWindow(String desciption, String title);

	/**
	 * @param panel - the panel to be deleted
	 * @param title - the title to be deleted
	 */
	public void removeCRFromView(MiniView panel, String title);

	/**
	 * @param msg - the msg to append
	 */
	public void appendToInfo(String msg);

	/**
	 * @param collection - list of users that the local user's user is connected to
	 */
	public void refreshNotYetConnectedUser(Collection<IUser> collection);

}
