package dg46_zh16.miniMVC;

import java.util.UUID;
import java.util.function.Supplier;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;

import common.*;
import common.msg.*;
import common.msg.action.chatroom.*;
import common.msg.status.*;
import common.msg.content.*;
import dg46_zh16.miniMVC.host.HackCmd;
import dg46_zh16.miniMVC.host.HackData;
import dg46_zh16.miniMVC.host.IHackData;
import dg46_zh16.miniMVC.host.IImageData;
import dg46_zh16.miniMVC.host.ITextData;
import dg46_zh16.miniMVC.host.ImageCmd;
import dg46_zh16.miniMVC.host.ImageData;
import dg46_zh16.miniMVC.host.MyAlgo;
import dg46_zh16.model.MainModel;
import provided.datapacket.*;

/**
 * the MiniModel class to represent a chat room
 *
 */
public class MiniModel {

	/**
	 * Chat Room object
	 */
	private ChatRoom room;

	/**
	 * List of users
	 */
	private Collection<IChatUser> initialMembers;

	/**
	 * You, as a user in the chat room
	 */
	private IChatUser meMember;

	/**
	 * Your stub
	 */
	private IChatUser meMemberStub;

	/**
	 * the model to view adapter
	 */
	ImModel2mViewAdapter model2view;

	/**
	 * the mini to main adapter
	 */
	private IMini2MainAdapter mini2main;

	/**
	 * user name 
	 */
	private String username;

	/**
	 * chat room name
	 */
	private String chatroomName;

	/**
	 * unique chat room ID
	 */
	private UUID CRID;

	/**
	 * dictionary to store command
	 */

	@SuppressWarnings("rawtypes")
	private HashMap<IDataPacketID, AChatMsgCmd> cmdDict = new HashMap<IDataPacketID, AChatMsgCmd>();

	/**
	 * dictionary to store IChatUser
	 */
	private HashMap<IChatUser, String> usernameDict = new HashMap<IChatUser, String>();

	/**
	 * dictionary to store message
	 */
	private HashMap<IDataPacketID, ChatRoomDataPacket<IChatRoomMsg>> cachedMessages = new HashMap<IDataPacketID, ChatRoomDataPacket<IChatRoomMsg>>();

	/**
	 * the command to model adapter
	 */
	private ICmd2ModelAdapter Cmd2myModelAdapter = new ICmd2ModelAdapter() {
		@Override
		public void displayComponent(Supplier<JComponent> componentFac, String title) {
			//System.out.println("model.model2view is: " + model.model2view);
			model2view.displayComponent(componentFac);
		}

		@Override
		public void displayText(String text) {
			model2view.displayText(text, "cmd2model doesn't know sender");
		}

		@Override
		public void sendMessageToMember(IChatRoomMsg msg, IChatUser member) {
			try {
				member.receiveChatRoomMsg(new ChatRoomDataPacket<IChatRoomMsg>(msg, meMemberStub));
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void sendMessageToRoom(IChatRoomMsg msg) {
			room.sendChatRoomMsg(new ChatRoomDataPacket<IChatRoomMsg>(msg, meMemberStub));
		}

	};

	/**
	 * default command
	 */
	@SuppressWarnings("rawtypes")
	AChatMsgCmd<?> myDefaultCmd = new AChatMsgCmd<IChatRoomMsg>() {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1035898362631332743L;


		@Override
		public Void apply(IDataPacketID index, ChatRoomDataPacket<IChatRoomMsg> host, Void... params) {
			System.out.println("Couldn't find command, sending a request to sender to get command for :"
					+ ((DataPacket) host).getData().getID());
			cachedMessages.put(((DataPacket) host).getData().getID(), (ChatRoomDataPacket<IChatRoomMsg>) host);
			IDataPacketID id = ((ChatRoomDataPacket) host).getData().getID();
			IRequestCommandMsg requestCmdData = IRequestCommandMsg.make(id);
			try {
				((IChatUser) ((ChatRoomDataPacket) host).getSender())
						.receiveChatRoomMsg(new ChatRoomDataPacket<IRequestCommandMsg>(requestCmdData, meMemberStub));
			} catch (RemoteException e) {
				e.printStackTrace();
			} //should have a nice sendMessage in 
			return null;
		}

	};

	/**
	 * 
	 */
	MyAlgo myAlgo = new MyAlgo(myDefaultCmd) {

		/**
		 * serial ID
		 */
		private static final long serialVersionUID = 2123571746495285034L;

		public void init() {
			//The sender has requested an unknown command. Send them IAddCmdMsg
			this.setCmd(IRequestCommandMsg.GetID(), new AChatMsgCmd<IRequestCommandMsg>() {

				/**
				 * serial ID
				 */
				private static final long serialVersionUID = 3997937313038904656L;

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<IRequestCommandMsg> host, Void... params) {
					System.out
							.println("The sender is requesting : " + host.getData().getMsgId() + " so I'm sending it.");
					//find the cmd with requested ID, check if it is NOT WELL KNOWN, if so, send an addCmdData! 
					IDataPacketID id = host.getData().getMsgId();
					IAddCommandMsg addCmdData = IAddCommandMsg.make((AChatMsgCmd<IAddCommandMsg>) cmdDict.get(id), id);
					try {
						host.getSender()
								.receiveChatRoomMsg(new ChatRoomDataPacket<IAddCommandMsg>(addCmdData, meMemberStub));
					} catch (RemoteException e) {
						e.printStackTrace();
					} //should have a nice sendMessage in 

					return null;
				}

			});

			//The sender has asked you to add a command in your algorithm. Extract the command, set its cmd2modeladapter.
			//Find the cached messages of this type, and then process them!!
			this.setCmd(IAddCommandMsg.GetID(), new AChatMsgCmd<IAddCommandMsg<? extends IChatRoomMsg>>() {

				/**
				 * serial ID
				 */
				private static final long serialVersionUID = 1926285121273342942L;

				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<IAddCommandMsg<? extends IChatRoomMsg>> host,
						Void... params) {
					try {
						System.out.println(host.getSender().getName() + "send me the cmd for "
								+ host.getData().getMsgId() + " so I'm adding it to my algo.");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					IAddCommandMsg<?> data = host.getData();
					IDataPacketID id = data.getMsgId();
					AChatMsgCmd<?> cmd = data.getCommand();
					//					ICmd2ModelAdapter Cmd2myModelAdapter = new Cmd2ModelAdapter(MiniModel.this);					

					cmd.setCmd2ModelAdpt(Cmd2myModelAdapter); //So this command can make changes to YOUR model.
					myAlgo.setCmd(id, cmd);
					cachedMessages.forEach((k, v) -> {
						if (k.equals(id)) {
							((ChatRoomDataPacket<?>) v).execute(myAlgo);
						}
					});
					return null;
				}

			});

			//The sender has sent you a string msg, display it!
			this.setCmd(IStringMsg.GetID(), new AChatMsgCmd<IStringMsg>() {
				/**
				 * serial ID
				 */
				private static final long serialVersionUID = -4971121791895787527L;

				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<IStringMsg> host, Void... params) {
					System.out.println("received StringMsg; sending to miniView to display it");
					try {
						model2view.displayText(host.getData().getMessage(), host.getSender().getName());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					return null;
				}

			});

			// the sender has sent you a image msg
			ImageCmd imgCmd = new ImageCmd();
			imgCmd.setCmd2ModelAdpt(Cmd2myModelAdapter);
			this.setCmd(IImageData.GetID(), imgCmd);
			cmdDict.put(IImageData.GetID(), imgCmd);

			HackCmd hackCmd = new HackCmd();
			hackCmd.setCmd2ModelAdpt(Cmd2myModelAdapter);
			this.setCmd(IHackData.GetID(), hackCmd);
			cmdDict.put(IHackData.GetID(), hackCmd);

			this.setCmd(IAddMsg.GetID(), new AChatMsgCmd<IAddMsg>() {
				/**
				 * serial ID
				 */
				private static final long serialVersionUID = -3240456574878227124L;

				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<IAddMsg> host, Void... params) {
					IAddMsg data = host.getData();
					room.addMember(data.getUserToAdd());
					String name = "name";
					try {
						name = host.getSender().getName();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					model2view.displayText(name + " has joined the chatroom", name);
					usernameDict.put(host.getSender(), name);
					return null;
				}

			});

			//The sender has asked you to delete the given user from your members list.
			this.setCmd(ILeaveMsg.GetID(), new AChatMsgCmd<ILeaveMsg>() {

				/**
				 * serial ID
				 */
				private static final long serialVersionUID = -8095031466912070462L;

				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<ILeaveMsg> host, Void... params) {
					ILeaveMsg data = host.getData();
					room.removeMember(data.getUserToLeave());
					model2view.displayText(usernameDict.get(host.getSender()) + " has left the chatroom",
							usernameDict.get(host.getSender()));
					return null;
				}
			});
			//The sender didn't accept something you sent, print the description on your console.
			this.setCmd(IFailureMsg.GetID(), new AChatMsgCmd<IFailureMsg>() {

				/**
				 * serial ID
				 */
				private static final long serialVersionUID = -4046745773386484055L;

				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<IFailureMsg> host, Void... params) {
					mini2main.alertWindow(host.getData().getDescription());
					return null;
				}
			});

			this.setCmd(ISuccessMsg.GetID(), new AChatMsgCmd<ISuccessMsg>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 7523892556401739408L;

				@Override
				public Void apply(IDataPacketID index, ChatRoomDataPacket<ISuccessMsg> host, Void... params) {
					//					ISuccessMsg data = host.getData(); 
					//					mini2main.appendInfo(host.getData().getDescription()); //send notification 
					return null;
				}
			});

			//			this.setCmd(I.GetID(), new AChatMsgCmd<IFailureMsg>() {
			//
			//				@Override
			//				public Void apply(IDataPacketID index, ChatRoomDataPacket<IFailureMsg> host, Object... params) {
			//					IFailureMsg data = host.getData(); 
			//					mini2main.alertWindow(host.getData().getDescription());
			//					return null;
			//				}
			//			});

		}
	};

	/**
	 * constructor for mini model
	 * @param model2view model 2 view adapter
	 * @param mini2main mini to main adapter
	 * @param chatroomName chat room name
	 * @param CRID unique ID
	 * @param members collection of room members 
	 */
	public MiniModel(ImModel2mViewAdapter model2view, IMini2MainAdapter mini2main, String chatroomName, UUID CRID,
			Collection<IChatUser> members) {
		this.model2view = model2view;
		this.mini2main = mini2main;
		this.chatroomName = chatroomName;
		this.initialMembers = members;
		this.CRID = CRID;
		username = mini2main.getUsername();
	}

	/**
	 * start the model
	 */
	public void start() {
		this.meMember = new IChatUser() {
			@Override
			public String getName() throws RemoteException {
				return username;
			}

			@Override
			public void receiveChatRoomMsg(ChatRoomDataPacket<?> msg) throws RemoteException {
				System.out.println("Msg received: " + msg.getData().getID() + " looking for it in my algo");
				(new Thread() {
					public void run() {
						msg.execute(myAlgo);
					}
				}).start(); // start the new thread

			}

		};
		try {
			this.meMemberStub = (IChatUser) UnicastRemoteObject.exportObject(this.meMember, MainModel.STUB_PORT);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		initialMembers.add(meMemberStub);
		//Check if this chatroom is a new one or mimicking an existing chatroom
		if (CRID == null) {
			//System.out.println("Case1: CRID == null");
			room = new ChatRoom(chatroomName, initialMembers);
		} else {
			//System.out.println("Case2: CRID != null");
			room = new ChatRoom(chatroomName, CRID, initialMembers);
		}

	}

	/**
	 * get the chat room
	 * @return room object
	 */
	public IChatRoom getChatRoom() {//called by main through main2mini in controller: when another user asks local user for their chatrooms!
		return room;
	}

	/**
	 * get chat room name
	 * @return name of the chat room
	 */
	public String getCRName() {
		return room.getName();
	}

	/**
	 * get unique chat room ID
	 * @return Unique ID
	 */
	public UUID getCRID() {
		return room.getId();
	}

	/**
	 * get yourself, an IChatuser
	 * @return your IChatUser object 
	 */
	public IChatUser getSelf() {
		return meMember;
	}

	/**
	 * get stub of your self
	 * @return stub of your self
	 */
	public IChatUser getSelfStub() {
		return meMemberStub;
	}

	/**
	 * sendText to chat room
	 * @param text input text
	 */
	public void sendText(String text) {
		room.sendChatRoomMsg(new ChatRoomDataPacket<IStringMsg>(IStringMsg.make(text), meMemberStub));
	}

	/**
	 * send message to chat room
	 * @param msg input message
	 */
	public void sendMsg(ChatRoomDataPacket<? extends IChatRoomMsg> msg) {
		room.sendChatRoomMsg(msg);

	}

	/**
	 * send unknown text message to chat room
	 * @param text input text
	 */
	public void sendUnknownText(String text) {
		System.out.println("MiniModel: telling room to send message");
		ITextData msg = ITextData.make(text);
		ChatRoomDataPacket<ITextData> dataPacket = new ChatRoomDataPacket<ITextData>(msg, meMemberStub);
		room.sendChatRoomMsg(dataPacket);
	}

	/**
	 * exit chat room
	 */
	public void exitChatRoom() {

		//Remove self from chatroom 
		room.removeMember(meMemberStub);

		// Member A sends �ILeaveMsg� to all members in the current chatroom.

		room.sendChatRoomMsg(new ChatRoomDataPacket<ILeaveMsg>(ILeaveMsg.make(meMemberStub), meMemberStub));

		mini2main.removeCR(room);
	}

	/**
	 * send Image to the chat room 
	 * @param text pathName
	 * @throws IOException exception msg
	 */
	public void sendImage(String text) throws IOException {
		System.out.println("MiniModel: telling room to send IMAGE message");

		Image image = Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("host/Pikachu-PNG-Transparent-Image.png"));
		ImageIcon imageIcon = new ImageIcon(image, "A image Bill and Diksha sent");

		//		ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(text)));
		ChatRoomDataPacket<ImageData> dataPacket = new ChatRoomDataPacket<ImageData>(new ImageData(imageIcon),
				meMemberStub);
		room.sendChatRoomMsg(dataPacket);
	}

	/**
	 * send hack msg to chat room. This allow you to send message on other people's behalf
	 * @param text input text that you want to send
	 */
	public void sendHackMsg(String text) {
		room.sendChatRoomMsg(new ChatRoomDataPacket<HackData>(new HackData(text), meMemberStub));
	}
}
