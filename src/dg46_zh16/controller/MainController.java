package dg46_zh16.controller;

import java.awt.EventQueue;

import java.util.UUID;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import common.*;
import provided.discovery.IEndPointData;
import provided.discovery.impl.view.IDiscoveryPanelAdapter;
import provided.rmiUtils.IRMI_Defs;
import dg46_zh16.miniMVC.*;
import dg46_zh16.model.*;
import dg46_zh16.view.*;

/**
 * 
 *
 */
public class MainController {
	/**
	 * The Main View object
	 */
	private MainView view;
	/**
	 * The Main Model object
	 */
	private MainModel model;
	/**
	 * The default Username
	 */
	String usrname = "Anonymous";
	/**
	 * The adapter from mini MVC to main MVC 
	 */
	IMini2MainAdapter mini2main = new IMini2MainAdapter() {

		@Override
		public String getUsername() {
			return model.getUserName();
		}

		@Override
		public void alertWindow(String description) {
			view.alertWindow(description);
		}

		@Override
		public void removeCR(ChatRoom room) {
			model.removeCR(room);

		}

	};

	/**
	 * 
	 */
	IMainModel2ViewAdapter model2viewAdapt;

	/**
	 * @param args - no arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainController app = new MainController();
					app.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	public MainController() {

		view = new MainView(new IView2MainModelAdapter() {

			@Override
			public void quit() {
				model.stop();
			};

			@Override
			public void createChatroom(String chatroomName) throws RemoteException {
				Collection<IChatUser> emptyMems = new ArrayList<IChatUser>();
				model.createChatroom(chatroomName, null, emptyMems);
			};

			@Override
			public void setUsername(String username) {
				usrname = username;
			}

			@Override
			public void connectTo(String hostIP) {
				model.connectTo(hostIP);
			}

			@Override
			public void startDiscovery(String username) {
				model.startDiscovery(username);
			}

			@Override
			public void requestChatRooms(IUser user) {
				model.requestChatRooms(user);
			}

			@Override
			public void inviteTo(IUser user, IChatRoom room) {
				model.inviteTo(user, room);
			}

			@Override
			public void requestToJoin(IUser userStub, IChatRoom room) {
				model.requestToJoin(userStub, room);
			}

			@Override
			public Collection<IUser> getConnectedUsers() {
				return model.getConnectedUser();
			}

			@Override
			public void requestUsers(IUser user) {
				model.requestUsers(user);
			}

			@Override
			public void connectTo(IUser receiverConnStub) {
				model.connectTo(receiverConnStub);
			};

		}, new IDiscoveryPanelAdapter<IEndPointData>() {

			@Override
			public void connectToDiscoveryServer(String category, boolean watchOnly,
					Consumer<Iterable<IEndPointData>> endPtsUpdateFn) {
				model.connectToDiscoveryServer(category, watchOnly, endPtsUpdateFn);
			}

			@Override
			public void connectToEndPoint(IEndPointData selectedValue) {
				model.connectToEndPoint(selectedValue);

			}

		});
		model2viewAdapt = new IMainModel2ViewAdapter() {

			@Override
			public void startDiscoveryPanel() {
				System.out.println("in controlled start Discover Panel");
				view.startDiscoveryPanel();
			}

			@Override
			public void refreshDropBox(Collection<IUser> otherStub) {
				view.refreshDropBox(otherStub);
			}

			/**
			 * Calls the mainMVC factory -> creates controller -> controller.start() -> return controller.getMiniMVCAdapt() 
			 * 1. take factory and make the mini controller(IMini2MainAdapter mini2main) --> this returns a main2mini adapter
			 * 2. return that main2mini adapter 
			 * this func is called in main Model (and therefore main model gets the main2mini adapter which it stores in the list)
			 * NOTE: this is the only function NOT delegating to view, but the miniController instead
			 */
			@Override
			public IMain2MiniAdapter makeMiniMVC(/*IChatRoom chatroom*/ String chatroomName, UUID CRID,
					Collection<IChatUser> members) {

				IMain2MiniAdapter main2mini = IMainModel2ViewAdapter.miniControllerFac.make(mini2main, chatroomName,
						CRID, members);
				return main2mini;
			}

			@Override
			public void refreshNotYetInChatRoom(Collection<IChatRoom> listOfCR) {
				view.refreshNotYetInChatRoom(listOfCR);

			}

			@Override
			public void addTab(MiniView panel, String title) {
				view.addTab(panel, title);
			}

			@Override
			public void alertWindow(String description) {
				view.alertWindow(description);

			}

			@Override
			public void removeCRFromView(MiniView panel, String title) {
				view.removeCRFromView(panel, title);

			}

			@Override
			public int requestWindow(String description, String title) {
				return view.requestWindow(description, title);
			}

			@Override
			public void appendToInfo(String msg) {
				view.appendToInfo(msg);

			}

			@Override
			public void refreshNotYetConnectedUser(Collection<IUser> collection) {
				view.refreshNotYetConnectedUser(collection);

			}

		};

		model = new MainModel(model2viewAdapt);

	}

	/**
	 * method to start the model and the view.
	 * @throws RemoteException - RMI error Exception
	 */
	protected void start() throws RemoteException {
		int x = 0;
		view.start();
		if (x == 0) {
			model.start(IRMI_Defs.CLASS_SERVER_PORT_CLIENT, IRMI_Defs.STUB_PORT_CLIENT);
			view.setTitle("Client");
		} else if (x == 1) {
			model.start(IRMI_Defs.CLASS_SERVER_PORT_SERVER, IRMI_Defs.STUB_PORT_SERVER);
			view.setTitle("Stub");
		} else {
			model.start(IRMI_Defs.CLASS_SERVER_PORT_EXTRA, IRMI_Defs.STUB_PORT_EXTRA);
			view.setTitle("Extra");
		}
	}
}
