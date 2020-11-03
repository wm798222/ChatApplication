package dg46_zh16.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.function.Consumer;

import common.*;
import common.msg.*;
import common.msg.action.chatroom.*;
import common.msg.action.application.*;
import common.msg.action.application.collection.*;
import common.msg.status.*;

import provided.datapacket.*;
import provided.discovery.*;
import provided.discovery.impl.model.*;

import provided.rmiUtils.RMIUtils;
import dg46_zh16.miniMVC.*;

/**
 * The Main Model in the Main MVC
 * 
 *
 */
public class MainModel {

	/**
	 * 
	 */
	private IMainModel2ViewAdapter mainModel2View;
	/**
	 * 
	 */
	private RMIUtils rmiUtils = new RMIUtils((s) -> mainModel2View.appendToInfo(s));
	/**
	 * 
	 */
	private Registry localRegistry;
	/**
	 * 
	 */
	private DiscoveryConnector discConn;
	/**
	 * 
	 */
	private RemoteAPIStubFactory<IUser> remoteAPIStubFac;

	/**
	 * 
	 */
	private IUser connectionEngine;
	/**
	 * 
	 */
	private IUser connectionEngineStub;
	/**
	 * 
	 */
	private Map<UUID, IMain2MiniAdapter> chatroomAdptDict = new HashMap<UUID, IMain2MiniAdapter>();
	/**
	 * 
	 */
	private HashMap<IUser, String> userConnectionStubs = new HashMap<IUser, String>();
	/**
	 * 
	 */
	private String username = "Anonymous";
	/**
	 * 
	 */
	public static int STUB_PORT = 0;

	/**
	 * 
	 */
	private AUserMsgCmd<IUserMsg> myDefaultCmd = new AUserMsgCmd<IUserMsg>() { // This is default command -> request cmd

		/**
		 * serial ID
		 */
		private static final long serialVersionUID = 4074894369370279083L;

		@Override
		public Void apply(IDataPacketID index, UserDataPacket<IUserMsg> host, Void... params) {
			// empty since we are not handling unkown messages on the IUser level
			return null;
		}

	};

	/**
	 * visitor used to process commands 
	 */
	private MyAppAlgo myAppAlgo = new MyAppAlgo(myDefaultCmd) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3986128848578421395L;

		@Override
		public void init() {
			this.setCmd(IConnectMsg.GetID(), new AUserMsgCmd<IConnectMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 533218386005285644L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IConnectMsg> host, Void... params) {
					IUser senderStub = host.getSender();
					try {
						userConnectionStubs.put(senderStub, senderStub.getName());
					} catch (RemoteException e) {
						e.printStackTrace();
					}

					Collection<IUser> users = new ArrayList<>();

					for (IUser user : userConnectionStubs.keySet())
						users.add(user);

					mainModel2View.refreshDropBox(users);
					return null;
				}
			});

			// This is called, when a new user is invited ("Invite") to a room. (
			this.setCmd(IInviteToRoomMsg.GetID(), new AUserMsgCmd<IInviteToRoomMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 4313186459370802095L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IInviteToRoomMsg> host, Void... params) {
					// How do we wait for the response? How do we link the responseMsg back to this
					// msg?
					IUser sender = (IUser) host.getSender();
					IInviteToRoomMsg d = host.getData();
					try {
						int res = mainModel2View.requestWindow(sender.getName() + " has invited you to join "
								+ d.getChatRoom().getName() + ". Would you like to join the chatroom?",
								"Invite Request");
						if (res == 0) { // Yes!
							// Here I send a message to MYSELF, where the message sender is shown to be the
							// one who invite me.
							connectionEngineStub.receiveApplicationMsg(new UserDataPacket<IAddToChatRoomMsg>(
									IAddToChatRoomMsg.make(d.getChatRoom()), host.getSender()));
						} else { // No!
							sender.receiveApplicationMsg(new UserDataPacket<IFailureMsg>(IFailureMsg
									.make(username + " has rejected your invite to join " + d.getChatRoom().getName()),
									connectionEngineStub));
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}

					return null;
				}

			});

			this.setCmd(IRequestChatRoomsCollectionMsg.GetID(), new AUserMsgCmd<IRequestChatRoomsCollectionMsg>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -1945248692336119862L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IRequestChatRoomsCollectionMsg> host,
						Void... params) {
					// tell user to send ISendChatRoomsCollectionMsg to the Sender
					Collection<IChatRoom> chatrooms = new ArrayList<IChatRoom>();
					Consumer<IMain2MiniAdapter> consumer = adpt -> chatrooms.add(adpt.getChatRoom());
					chatroomAdptDict.values().forEach(consumer);

					ISendChatRoomsCollectionMsg sendCRCollectionMsg = ISendChatRoomsCollectionMsg.make(chatrooms);
					try {
						host.getSender().receiveApplicationMsg(new UserDataPacket<ISendChatRoomsCollectionMsg>(
								sendCRCollectionMsg, connectionEngineStub));
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					return null;
				}

			});

			this.setCmd(ISendChatRoomsCollectionMsg.GetID(), new AUserMsgCmd<ISendChatRoomsCollectionMsg>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2137503949022642460L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<ISendChatRoomsCollectionMsg> host,
						Void... params) {
					Collection<IChatRoom> listOfCR = host.getData().getCollection();
					for (IChatRoom ChatRoom : listOfCR)
						System.out.println(ChatRoom);
					mainModel2View.refreshNotYetInChatRoom(listOfCR);
					return null;
				}

			});

			// The sender wants you to create a chatroom and then send everyone else a
			// message to add you!
			this.setCmd(IAddToChatRoomMsg.GetID(), new AUserMsgCmd<IAddToChatRoomMsg>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 3012381856805503255L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IAddToChatRoomMsg> host, Void... params) {
					IAddToChatRoomMsg data = host.getData();
					createChatroom(data.getChatRoom().getName(), data.getChatRoom().getId(),
							data.getChatRoom().getChatUsers());
					return null;
				}
			});
			// The sender wants to join a chatroom, approve -> send them an
			// IAddToChatRoomMsg; decline -> send them an IFailureMsg
			this.setCmd(IRequestToJoinMsg.GetID(), new AUserMsgCmd<IRequestToJoinMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -2775385227579601009L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IRequestToJoinMsg> host, Void... params) {
					IRequestToJoinMsg d = host.getData();
					IUser sender = (IUser) host.getSender();
					try {
						int res = mainModel2View
								.requestWindow(sender.getName() + " has sent you a request to join chatroom \""
										+ d.getChatRoom().getName() + "\". Do you accept?", "Join Request");
						if (res == 0) {
							host.getSender()
									.receiveApplicationMsg(new UserDataPacket<IAddToChatRoomMsg>(IAddToChatRoomMsg
											.make(((IMain2MiniAdapter) chatroomAdptDict.get(d.getChatRoom().getId()))
													.getChatRoom()),
											connectionEngineStub));
						} else {
							host.getSender()
									.receiveApplicationMsg(new UserDataPacket<IFailureMsg>(IFailureMsg.make(username
											+ " has rejected your request to join " + d.getChatRoom().getName()),
											connectionEngineStub));
						}

					} catch (RemoteException e) {
						e.printStackTrace();
					}
					return null;
				}
			});

			// The sender didn't accept something you sent, print the description on your
			// console.
			this.setCmd(IFailureMsg.GetID(), new AUserMsgCmd<IFailureMsg>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4031307560831647756L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IFailureMsg> host, Void... params) {
					String description = host.getData().getDescription();
					mainModel2View.alertWindow(description);
					return null;
				}
			});

			this.setCmd(ISuccessMsg.GetID(), new AUserMsgCmd<ISuccessMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -444237893482717931L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<ISuccessMsg> host, Void... params) {
					//					ISuccessMsg data = host.getData(); 
					//					mini2main.appendInfo(host.getData().getDescription()); //send notification 
					return null;
				}
			});

			this.setCmd(IQuitMsg.GetID(), new AUserMsgCmd<IQuitMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1129943133146570972L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IQuitMsg> host, Void... params) {
					//remove from dropdown list
					userConnectionStubs.remove(host.getSender());

					Collection<IUser> users = new ArrayList<>();

					for (IUser user : userConnectionStubs.keySet())
						users.add(user);

					mainModel2View.refreshDropBox(users);
					return null;
				}
			});

			this.setCmd(IRequestUsersCollectionMsg.GetID(), new AUserMsgCmd<IRequestUsersCollectionMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 5108769081869159600L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IRequestUsersCollectionMsg> host,
						Void... params) {
					// tell user to send ISendChatRoomsCollectionMsg to the Sender

					try {

						Collection<IUser> users = new ArrayList<>();

						for (IUser user : userConnectionStubs.keySet())
							users.add(user);

						host.getSender().receiveApplicationMsg(new UserDataPacket<IAddUsersCollectionMsg>(
								IAddUsersCollectionMsg.make(users), connectionEngineStub));
					} catch (RemoteException e) {
						e.printStackTrace();
					}

					return null;
				}
			});

			this.setCmd(IAddUsersCollectionMsg.GetID(), new AUserMsgCmd<IAddUsersCollectionMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -7200551977914117480L;

				@Override
				public Void apply(IDataPacketID index, UserDataPacket<IAddUsersCollectionMsg> host, Void... params) {
					System.out.println("IAddUsersCollectionMsg,  refershNotYetConnectedUser are: "
							+ host.getData().getCollection());
					Collection<IUser> copyList = new ArrayList<IUser>();

					for (IUser user : host.getData().getCollection()) {
						if (!userConnectionStubs.keySet().contains(user)) {
							copyList.add(user);
						}
					}

					System.out.println("IAddUsersCollectionMsg,  copyList: " + copyList.toString());

					mainModel2View.refreshNotYetConnectedUser(copyList);

					return null;
				}
			});

		}
	};

	/**
	 * @param mainModel2View - adapter
	 */
	public MainModel(IMainModel2ViewAdapter mainModel2View) {
		this.mainModel2View = mainModel2View;
	}

	/**
	 * @param usrname - set username
	 */
	public void startDiscovery(String usrname) {
		this.username = usrname;
		try {
			this.discConn = new DiscoveryConnector(rmiUtils, username, IUser.BOUND_NAME);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param classPort - the port to use
	 * @param stubPort - the stub port to user
	 * @throws RemoteException - RMI exception
	 */
	public void start(int classPort, int stubPort) throws RemoteException {
		STUB_PORT = stubPort;
		rmiUtils.startRMI(classPort);

		this.remoteAPIStubFac = new RemoteAPIStubFactory<IUser>(rmiUtils); // Instantiate the API-specific factory,
																			// replacing the "IConnectionEntity" with
																			// the proper API-defined Remote interface
		connectionEngine = new IUser() {

			@Override
			public String getName() throws RemoteException {
				return username;
			}

			@Override
			public void receiveApplicationMsg(UserDataPacket<? extends IUserMsg> msg) throws RemoteException {
				Thread newThread = new Thread() {
					public void run() {
						msg.execute(myAppAlgo);
					}
				}; // start the new thread

				//threadList.add(newThread);
				newThread.start();
			}

		};
		connectionEngineStub = (IUser) UnicastRemoteObject.exportObject(connectionEngine, stubPort);
		localRegistry = rmiUtils.getLocalRegistry();
		localRegistry.rebind(IUser.BOUND_NAME, connectionEngineStub);
		mainModel2View.startDiscoveryPanel(); // Call out to the view to start the discovery server panel now that the
												// rmiUtils has started.
	}

	/**
	 * stop function
	 */
	public void stop() {

		// get all chatrooms the user is in
		Collection<IMain2MiniAdapter> main2MiniAdapterList = chatroomAdptDict.values();
		for (IMain2MiniAdapter adapter : main2MiniAdapterList) {
			adapter.exitChatRoom(); //removes me from my chatroom, then sends everyone a message saying remove me. then removes CR from the View. 
		}

		for (IUser user : userConnectionStubs.keySet()) {
			// send IQuit msg to disconnect from other users

			try {
				((IUser) user).receiveApplicationMsg(
						(new UserDataPacket<IUserMsg>(IQuitMsg.make(connectionEngineStub), connectionEngineStub)));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rmiUtils.stopRMI();
		System.exit(-1); // exit the program.
	}

	/**
	 * @param hostIP - IP of the host
	 */
	public void connectTo(String hostIP) {
		try {
			Registry registry = rmiUtils.getRemoteRegistry(hostIP);
			IUser receiverConnStub = (IUser) registry.lookup(IUser.BOUND_NAME);
			connectTo(receiverConnStub);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param receiverConnStub - the stub to connect to
	 */
	public void connectTo(IUser receiverConnStub) {
		try {
			connectionEngine.receiveApplicationMsg(
					new UserDataPacket<IConnectMsg>(IConnectMsg.make(receiverConnStub), receiverConnStub));
			receiverConnStub.receiveApplicationMsg(
					new UserDataPacket<IConnectMsg>(IConnectMsg.make(connectionEngineStub), connectionEngineStub));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param category - the category
	 * @param watchOnly - boolean for watch only or not
	 * @param updateFunc - the update functions
	 */
	public void connectToDiscoveryServer(String category, boolean watchOnly,
			Consumer<Iterable<IEndPointData>> updateFunc) {
		try {
			discConn.connectToDiscoveryServer(category, watchOnly, updateFunc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param selectedValue - the endpoint of the user on the discovery server
	 */
	public void connectToEndPoint(IEndPointData selectedValue) {
		try {
			IUser receiverConnStub = remoteAPIStubFac.get(selectedValue);
			connectTo(receiverConnStub);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called by the main View so returns the miniView that is to be installed in the mainView
	 * @param chatroomName - room name
	 * @param CRID - room UUID
	 * @param members - the members in that toom
	 */
	public void createChatroom(String chatroomName, UUID CRID, Collection<IChatUser> members) {

		IMain2MiniAdapter main2mini = mainModel2View.makeMiniMVC(chatroomName, CRID, members); // adds them to the
																								// members
		IChatUser newMemberStub = main2mini.getSelfStub();
		// tell all members to add self to their chatroom user list
		main2mini.sendMsg(new ChatRoomDataPacket<IAddMsg>(IAddMsg.make(newMemberStub), newMemberStub));
		chatroomAdptDict.put(main2mini.getCRID(), main2mini);
		mainModel2View.addTab(main2mini.getPanel(), main2mini.getChatRoom().getName());

	}

	/**
	 * @return - getter for self username
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * @param user - the user to reqyest rooms from 
	 */
	public void requestChatRooms(IUser user) {
		try {
			user.receiveApplicationMsg(new UserDataPacket<IRequestChatRoomsCollectionMsg>(
					IRequestChatRoomsCollectionMsg.make(), connectionEngineStub));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param userStub - the user to invite
	 * @param room - the room to invite to
	 */
	public void inviteTo(IUser userStub, IChatRoom room) {
		try {
			userStub.receiveApplicationMsg(
					new UserDataPacket<IInviteToRoomMsg>(IInviteToRoomMsg.make(room), connectionEngineStub));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param userStub - the user to which we will send the request
	 * @param room - the room to request
	 */
	public void requestToJoin(IUser userStub, IChatRoom room) {
		try {
			userStub.receiveApplicationMsg(
					new UserDataPacket<IRequestToJoinMsg>(IRequestToJoinMsg.make(room), connectionEngineStub));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param room - the room to be removes
	 */
	public void removeCR(ChatRoom room) {
		UUID roomID = room.getId();
		IMain2MiniAdapter main2mini = (IMain2MiniAdapter) chatroomAdptDict.get(roomID);
		// remove the chatRoom from chatroomAdptDict
		chatroomAdptDict.remove(roomID);
		// remove the chatRoom from the main view
		mainModel2View.removeCRFromView(main2mini.getPanel(), main2mini.getChatRoom().getName());

	}

	/**
	 * @return the collection of users
	 */
	public Collection<IUser> getConnectedUser() {
		Collection<IUser> users = new ArrayList<>();
		for (IUser user : userConnectionStubs.keySet())
			users.add(user);
		return users;
	}

	/**
	 * @param user - the user to be requested
	 */
	public void requestUsers(IUser user) {
		try {
			user.receiveApplicationMsg(
					(new UserDataPacket<IUserMsg>(IRequestUsersCollectionMsg.make(), connectionEngineStub)));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
