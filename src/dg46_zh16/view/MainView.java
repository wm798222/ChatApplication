package dg46_zh16.view;

import javax.swing.JFrame;

import dg46_zh16.miniMVC.MiniView;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import common.*;
import provided.discovery.IEndPointData;
import provided.discovery.impl.view.*;

import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


/**
 * Main View
 *
 */
public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2562827110066675953L;

	/**
	 * the view to main model adapter
	 */
	transient IView2MainModelAdapter view2mainModel;

	/**
	 * quit button 
	 */
	private final JButton btnQuit = new JButton("Quit");

	/**
	 * create room button
	 */
	private final JButton btnCreateChatroom = new JButton("Create Chatroom");

	/**
	 * field to eneter user IP
	 */
	private final JTextField userIP = new JTextField();

	/**
	 * login panel
	 */
	private final JPanel LoginPnl = new JPanel();

	/**
	 * remote host panel
	 */
	private final JPanel RemoteHostsPnl = new JPanel();
	/**
	 * connect to panel
	 */
	private final JPanel ConnectToPnl = new JPanel();
	/**
	 * make chat room panel
	 */
	private final JPanel MakeChatroomPnl = new JPanel();
	/**
	 * connect button
	 */
	private final JButton btnConnect = new JButton("Connect!");
	/**
	 * split the main panel 
	 */
	private final JSplitPane MainSplit = new JSplitPane();
	/**
	 * navigation bar panel
	 */
	private final JPanel NavBarPnl = new JPanel();
	/**
	 * field to enter chat room name
	 */
	private final JTextField chatroomName = new JTextField();

	/**
	 * discovery panel
	 */
	@SuppressWarnings("rawtypes")
	private DiscoveryPanel discoveryPnl;

	/**
	 * connected host panel
	 */
	private final JPanel ConnectedHostPnl = new JPanel();

	/**
	 * button to request joining a room
	 */
	private final JButton btnRequest = new JButton("Request!");

	/**
	 * last penl
	 */
	private final JPanel lastPnl = new JPanel();
	/**
	 * current connected user list
	 */
	private final JComboBox<UserWrapper> currUserList = new JComboBox<UserWrapper>();
	/**
	 * content panel
	 */
	private JPanel contentPane;
	/**
	 * button to invite someone to a room
	 */
	private final JButton btnInvite = new JButton("Invite!");
	/**
	 * field to enter user name
	 */
	private final JTextField userNameField = new JTextField();
	/**
	 * button to start
	 */
	private final JButton startButton = new JButton("Start!");
	/**
	 * tabbed panel
	 */
	private final JTabbedPane tabbedPanel = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * current chat rooms that you are in
	 */
	private final JComboBox<ChatRoomWrapper> connUsersCRs = new JComboBox<ChatRoomWrapper>();
	/**
	 * info panel
	 */
	private final JPanel infoPnl = new JPanel();
	/**
	 * info text panel
	 */
	private final JTextPane infoTextPnl = new JTextPane();
	/**
	 * button for testing alert
	 */
	private final JButton btnTestingAlert = new JButton("Testing alert");
	/**
	 * connect user info panel
	 */
	private final JPanel connUsersInfo = new JPanel();
	/**
	 * list of users you are not yet connected to
	 */

	private final JComboBox<UserWrapper> usersNotYetConnected = new JComboBox<UserWrapper>();
	/**
	 * button to connect to not yet connected users
	 */
	private final JButton btnConnectTo = new JButton("Connect To");

	/**
	 * Discovery panel adapter
	 */
	@SuppressWarnings("rawtypes")
	private IDiscoveryPanelAdapter discSrvadpt;

	/**
	 * refresh usersNotYetConnected and list of chat rooms you can join.
	 */
	private final JButton btnRefresh = new JButton("Refresh!");
	/**
	 * label for chat room
	 */
	private final JLabel lblChatroom = new JLabel("ChatRoom");
	/**
	 * label for Not Yet Connected Users
	 */
	private final JLabel label = new JLabel("Not Yet Connected Users");

	/**
	 * Launch the application.
	 */

	public void start() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		//		currUserList.setRenderer(new GetUsernameRenderer());
		//		connUsersCRs.setRenderer(new GetChatRoomNameRenderer());
		//		usersNotYetConnected.setRenderer(new GetUsernameRenderer());
		//		connectedUserList.setRenderer(new GetUsernameRenderer());

	}

	/**
	 * Main View constructor
	 * @param view2mainModeladpt view to main model adapter
	 * @param discSrvadpt discovery server adapter
	 */
	public MainView(IView2MainModelAdapter view2mainModeladpt, IDiscoveryPanelAdapter<IEndPointData> discSrvadpt) {
		this.discSrvadpt = discSrvadpt;
		view2mainModel = view2mainModeladpt;
		initGUI();
	}

	/**
	 * init the GUI
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initGUI() {
		//-----------------------------------SETUP-----------------------------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ChatApp");
		setBounds(100, 100, 1170, 563);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		MainSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);

		contentPane.add(MainSplit, BorderLayout.CENTER);

		MainSplit.setLeftComponent(NavBarPnl);
		GridBagLayout gbl_NavBarPnl = new GridBagLayout();
		gbl_NavBarPnl.columnWidths = new int[] { 96, 129, 450, 219, 225, 0 };
		gbl_NavBarPnl.rowHeights = new int[] { 229, 0 };
		gbl_NavBarPnl.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_NavBarPnl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		NavBarPnl.setLayout(gbl_NavBarPnl);
		userNameField.setToolTipText("Enter your user name here");

		userNameField.setText("diksha");
		userNameField.setColumns(8);
		GridBagConstraints gbc_LoginPnl = new GridBagConstraints();
		gbc_LoginPnl.fill = GridBagConstraints.HORIZONTAL;
		gbc_LoginPnl.insets = new Insets(0, 0, 0, 5);
		gbc_LoginPnl.gridx = 0;
		gbc_LoginPnl.gridy = 0;
		LoginPnl.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Enter Name:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		NavBarPnl.add(LoginPnl, gbc_LoginPnl);
		GridBagLayout gbl_LoginPnl = new GridBagLayout();
		gbl_LoginPnl.columnWidths = new int[] { 53, 0 };
		gbl_LoginPnl.rowHeights = new int[] { 0, 0, 0 };
		gbl_LoginPnl.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_LoginPnl.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		LoginPnl.setLayout(gbl_LoginPnl);

		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.insets = new Insets(0, 0, 5, 0);
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.gridx = 0;
		gbc_userNameField.gridy = 0;
		LoginPnl.add(userNameField, gbc_userNameField);

		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.gridx = 0;
		gbc_startButton.gridy = 1;
		startButton.setToolTipText("Start the ChatApp!");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2mainModel.startDiscovery(userNameField.getText());
				//TODO: make all other elements change from disabled -> enabled.
			}
		});
		LoginPnl.add(startButton, gbc_startButton);

		//		//-----------------------------------MAKE CHATROOM-----------------------------------------
		MakeChatroomPnl
				.setBorder(new TitledBorder(null, "Make Chatroom", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_MakeChatroomPnl = new GridBagConstraints();
		gbc_MakeChatroomPnl.fill = GridBagConstraints.HORIZONTAL;
		gbc_MakeChatroomPnl.insets = new Insets(0, 0, 0, 5);
		gbc_MakeChatroomPnl.gridx = 1;
		gbc_MakeChatroomPnl.gridy = 0;
		NavBarPnl.add(MakeChatroomPnl, gbc_MakeChatroomPnl);
		GridBagLayout gbl_MakeChatroomPnl = new GridBagLayout();
		gbl_MakeChatroomPnl.columnWidths = new int[] { 117, 0 };
		gbl_MakeChatroomPnl.rowHeights = new int[] { 23, 0, 0, 0 };
		gbl_MakeChatroomPnl.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_MakeChatroomPnl.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		MakeChatroomPnl.setLayout(gbl_MakeChatroomPnl);
		GridBagConstraints gbc_btnCreateChatroom = new GridBagConstraints();
		gbc_btnCreateChatroom.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateChatroom.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCreateChatroom.gridx = 0;
		gbc_btnCreateChatroom.gridy = 1;
		btnCreateChatroom.setToolTipText("Create a room!");
		MakeChatroomPnl.add(btnCreateChatroom, gbc_btnCreateChatroom);
		btnCreateChatroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					view2mainModel.createChatroom(chatroomName.getText());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_chatroomName = new GridBagConstraints();
		gbc_chatroomName.insets = new Insets(0, 0, 5, 0);
		gbc_chatroomName.gridx = 0;
		gbc_chatroomName.gridy = 0;
		chatroomName.setToolTipText("Name of the room you want to create");
		chatroomName.setText("title1");
		chatroomName.setColumns(10);
		MakeChatroomPnl.add(chatroomName, gbc_chatroomName);

		GridBagConstraints gbc_btnTestingAlert = new GridBagConstraints();
		gbc_btnTestingAlert.gridx = 0;
		gbc_btnTestingAlert.gridy = 2;
		btnTestingAlert.setToolTipText("Button for testing alert");
		btnTestingAlert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testAlertWindow("MESSAGE");
			}
		});
		MakeChatroomPnl.add(btnTestingAlert, gbc_btnTestingAlert);
		discoveryPnl = new DiscoveryPanel(discSrvadpt);
		RemoteHostsPnl.setToolTipText("The discovery panel");

		//		//-----------------------------------DISCOVERY SERVER-----------------------------------------
		RemoteHostsPnl
				.setBorder(new TitledBorder(null, "Remote Hosts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		RemoteHostsPnl.add(discoveryPnl);
		GridBagConstraints gbc_RemoteHostsPnl = new GridBagConstraints();
		gbc_RemoteHostsPnl.fill = GridBagConstraints.HORIZONTAL;
		gbc_RemoteHostsPnl.insets = new Insets(0, 0, 0, 5);
		gbc_RemoteHostsPnl.gridx = 2;
		gbc_RemoteHostsPnl.gridy = 0;
		NavBarPnl.add(RemoteHostsPnl, gbc_RemoteHostsPnl);
		//		//-----------------------------------LAST PANELS-------------------------------------------
		GridBagConstraints gbc_lastPnl = new GridBagConstraints();
		gbc_lastPnl.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastPnl.insets = new Insets(0, 0, 0, 5);
		gbc_lastPnl.gridx = 3;
		gbc_lastPnl.gridy = 0;
		NavBarPnl.add(lastPnl, gbc_lastPnl);
		GridBagLayout gbl_lastPnl = new GridBagLayout();
		gbl_lastPnl.columnWidths = new int[] { 194, 0 };
		gbl_lastPnl.rowHeights = new int[] { 55, 0, 0 };
		gbl_lastPnl.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_lastPnl.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		lastPnl.setLayout(gbl_lastPnl);

		//			//---Connect To---
		GridBagConstraints gbc_ConnectToPnl = new GridBagConstraints();
		gbc_ConnectToPnl.insets = new Insets(0, 0, 5, 0);
		gbc_ConnectToPnl.anchor = GridBagConstraints.NORTHWEST;
		gbc_ConnectToPnl.gridx = 0;
		gbc_ConnectToPnl.gridy = 0;
		lastPnl.add(ConnectToPnl, gbc_ConnectToPnl);
		ConnectToPnl
				.setBorder(new TitledBorder(null, "Connect to...", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		userIP.setToolTipText("Enter IP");
		ConnectToPnl.add(userIP);
		userIP.setText("10.122.176.210");
		userIP.setColumns(8);
		btnConnect.setToolTipText("Connect using IP");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2mainModel.connectTo(userIP.getText()); //will automatically display in dropBox
			}
		});

		ConnectToPnl.add(btnConnect);

		//		//---Connected Host---
		GridBagConstraints gbc_ConnectedHostPnl = new GridBagConstraints();
		gbc_ConnectedHostPnl.anchor = GridBagConstraints.NORTHWEST;
		gbc_ConnectedHostPnl.gridx = 0;
		gbc_ConnectedHostPnl.gridy = 1;
		lastPnl.add(ConnectedHostPnl, gbc_ConnectedHostPnl);

		ConnectedHostPnl.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Connected Host", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_ConnectedHostPnl = new GridBagLayout();
		gbl_ConnectedHostPnl.columnWidths = new int[] { 101, 77, 0, 0 };
		gbl_ConnectedHostPnl.rowHeights = new int[] { 0, 0 };
		gbl_ConnectedHostPnl.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_ConnectedHostPnl.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		ConnectedHostPnl.setLayout(gbl_ConnectedHostPnl);
		//		btnNewButton.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		////				view2mainModel.showChatrooms((IUser) currConnList.getSelectedItem());
		//			}
		//		});
		//		ConnectedHostPnl.add(btnNewButton, gbc_btnNewButton);

		GridBagConstraints gbc_currUserList = new GridBagConstraints();
		gbc_currUserList.insets = new Insets(0, 0, 0, 5);
		gbc_currUserList.fill = GridBagConstraints.HORIZONTAL;
		gbc_currUserList.gridx = 0;
		gbc_currUserList.gridy = 0;
		currUserList.setToolTipText("List of users you are connected to");
		currUserList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString() == null) {
					currUserList.removeItem(e.getItem());
				}
				view2mainModel.requestUsers(((UserWrapper) currUserList.getSelectedItem()).get()); //automatically calls refreshNotYetConnectedUsers();
				view2mainModel.requestChatRooms(((UserWrapper) currUserList.getSelectedItem()).get()); //automatically calls refreshNotYetInChatRoom();
			}
		});
		ConnectedHostPnl.add(currUserList, gbc_currUserList);

		// --- Show Chat Room --- 

		GridBagConstraints gbc_btnInvite = new GridBagConstraints();
		gbc_btnInvite.insets = new Insets(0, 0, 0, 5);
		gbc_btnInvite.gridx = 1;
		gbc_btnInvite.gridy = 0;
		btnInvite.setToolTipText("Invite selected user to current room");
		btnInvite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MiniView mView = (MiniView) tabbedPanel.getSelectedComponent();
				view2mainModel.inviteTo(((UserWrapper) currUserList.getSelectedItem()).get(), mView.getRoom());

			}
		});
		ConnectedHostPnl.add(btnInvite, gbc_btnInvite);

		GridBagConstraints gbc_connUsersInfo = new GridBagConstraints();
		gbc_connUsersInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_connUsersInfo.gridx = 4;
		gbc_connUsersInfo.gridy = 0;
		connUsersInfo.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Connected Users's Chatrooms & Friends:", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		NavBarPnl.add(connUsersInfo, gbc_connUsersInfo);
		GridBagLayout gbl_connUsersInfo = new GridBagLayout();
		gbl_connUsersInfo.columnWidths = new int[] { 131, 81, 0 };
		gbl_connUsersInfo.rowHeights = new int[] { 0, 0, 23, 0, 0, 0 };
		gbl_connUsersInfo.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_connUsersInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		connUsersInfo.setLayout(gbl_connUsersInfo);

		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRefresh.gridwidth = 2;
		gbc_btnRefresh.insets = new Insets(0, 0, 5, 0);
		gbc_btnRefresh.gridx = 0;
		gbc_btnRefresh.gridy = 0;
		btnRefresh.setToolTipText("Refresh room you can join list and not yet connected user list");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2mainModel.requestChatRooms(((UserWrapper) currUserList.getSelectedItem()).get()); //automatically calls refreshNotYetInChatRoom();
				view2mainModel.requestUsers(((UserWrapper) currUserList.getSelectedItem()).get()); //automatically calls refreshNotYetConnectedUsers();
			}
		});
		connUsersInfo.add(btnRefresh, gbc_btnRefresh);

		GridBagConstraints gbc_lblChatroom = new GridBagConstraints();
		gbc_lblChatroom.insets = new Insets(0, 0, 5, 5);
		gbc_lblChatroom.gridx = 0;
		gbc_lblChatroom.gridy = 1;
		connUsersInfo.add(lblChatroom, gbc_lblChatroom);
		GridBagConstraints gbc_connUsersCRs = new GridBagConstraints();
		gbc_connUsersCRs.fill = GridBagConstraints.HORIZONTAL;
		gbc_connUsersCRs.insets = new Insets(0, 0, 5, 5);
		gbc_connUsersCRs.gridx = 0;
		gbc_connUsersCRs.gridy = 2;
		connUsersCRs.setToolTipText("List of chat rooms selected peer is in ");
		connUsersInfo.add(connUsersCRs, gbc_connUsersCRs);

		//		btnShowUsers.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				view2mainModel.getUsers((IUser) connectedUserList.getSelectedItem());
		//				
		//			}
		//		});
		GridBagConstraints gbc_btnRequest = new GridBagConstraints();
		gbc_btnRequest.insets = new Insets(0, 0, 5, 0);
		gbc_btnRequest.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRequest.gridx = 1;
		gbc_btnRequest.gridy = 2;
		btnRequest.setToolTipText("Request to join the selected room");
		connUsersInfo.add(btnRequest, gbc_btnRequest);
		btnRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2mainModel.requestToJoin(((UserWrapper) currUserList.getSelectedItem()).get(),
						((ChatRoomWrapper) connUsersCRs.getSelectedItem()).get());
			}
		});

		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		connUsersInfo.add(label, gbc_label);
		//		btnShowChatroom.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				view2mainModel.requestChatRooms((IUser)currUserList.getSelectedItem());
		//			}
		//		});

		GridBagConstraints gbc_usersNotYetConnected = new GridBagConstraints();
		gbc_usersNotYetConnected.fill = GridBagConstraints.HORIZONTAL;
		gbc_usersNotYetConnected.insets = new Insets(0, 0, 0, 5);
		gbc_usersNotYetConnected.gridx = 0;
		gbc_usersNotYetConnected.gridy = 4;
		usersNotYetConnected.setToolTipText("List of users the selected peer is connected to");
		connUsersInfo.add(usersNotYetConnected, gbc_usersNotYetConnected);

		GridBagConstraints gbc_btnConnectTo = new GridBagConstraints();
		gbc_btnConnectTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConnectTo.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnConnectTo.gridx = 1;
		gbc_btnConnectTo.gridy = 4;
		btnConnectTo.setToolTipText("Connect to a selected not you connected user");
		connUsersInfo.add(btnConnectTo, gbc_btnConnectTo);

		btnConnectTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2mainModel.connectTo(((UserWrapper) usersNotYetConnected.getSelectedItem()).get());
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		tabbedPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		MainSplit.setRightComponent(tabbedPanel);
		infoPnl.setToolTipText("Display information");

		tabbedPanel.addTab("Info", null, infoPnl, null);

		infoPnl.add(infoTextPnl);
		btnQuit.setToolTipText("Quit the ChatApp");
		contentPane.add(btnQuit, BorderLayout.NORTH);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view2mainModel.quit();
			}
		});

	}

	/**
	 * start the discovery panel
	 */
	public void startDiscoveryPanel() {
		discoveryPnl.start();
	}

	/**
	 * refresh the list of users you are connected to
	 * @param connStubs connect stub
	 */
	public void refreshDropBox(Collection<IUser> connStubs) {
		currUserList.removeAllItems();
		for (IUser connStub : connStubs) {
			currUserList.insertItemAt(new UserWrapper(connStub), 0);
		}
		//		currUserList.setSelectedIndex(0);

		//view2mainModel.requestUsers((IUser) currUserList.getSelectedItem()); //automatically calls refreshNotYetConnectedUsers();
	}

	/**
	 * add a tab in the view 
	 * @param panel panel in the mini view
	 * @param title title of the tab 
	 */
	public void addTab(MiniView panel, String title) {
		tabbedPanel.addTab(title, null, panel, null);
		tabbedPanel.setSelectedComponent(panel);
	}

	/**
	 * refresh the list of chat that a selected peer is connected to
	 * @param listOfCR list of chat rooms
	 */
	public void refreshNotYetInChatRoom(Collection<IChatRoom> listOfCR) {
		//		for (IChatRoom ChatRoom : listOfCR) System.out.println(ChatRoom.getName());
		connUsersCRs.removeAllItems();
		for (IChatRoom ChatRoom : listOfCR)
			connUsersCRs.insertItemAt(new ChatRoomWrapper(ChatRoom), 0);
		//		connUsersCRs.setSelectedIndex(0);		
	}

	/**
	 * refresh the list of users that a selected peer is connected to
	 * @param collection collection of IUsers
	 */
	public void refreshNotYetConnectedUser(Collection<IUser> collection) {
		System.out.println("refreshNotYetConnectedUser are: " + collection.toString());
		usersNotYetConnected.removeAllItems();
		for (IUser user : collection)
			usersNotYetConnected.insertItemAt(new UserWrapper(user), 0);
		//		usersNotYetConnected.setSelectedIndex(0);
	}

	/**
	 * append info to the view
	 * @param msg message want to append
	 */
	public void appendToInfo(String msg) {
		infoTextPnl.setText(infoTextPnl.getText() + msg + "\n");
	}

	/**
	 * alert window pop up
	 * @param description message you want to display
	 */
	public void alertWindow(String description) {
		System.out.println(description);
		JOptionPane.showMessageDialog(new JFrame(), description, "Request rejected", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * test for alert window
	 * @param description description message you want to display
	 */
	public void testAlertWindow(String description) {
		//for testing purposes, don't delete!
	}

	/**
	 * request a window in view
	 * @param description description of the window
	 * @param title title of the window
	 * @return integer 
	 */
	public int requestWindow(String description, String title) {
		return JOptionPane.showConfirmDialog(null, description, title, JOptionPane.YES_NO_OPTION);
	}

	/**
	 * remove a chat room from view
	 * @param panel the panel the chat room is in
	 * @param title title of the room
	 */
	public void removeCRFromView(MiniView panel, String title) {
		tabbedPanel.remove(panel);
	}

}
