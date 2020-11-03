package dg46_zh16.miniMVC;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Supplier;

import javax.swing.JComponent;

import dg46_zh16.model.*;
import common.*;
import common.msg.ChatRoomDataPacket;
import common.msg.IChatRoomMsg;

/**
 * Instantiates the Model, the MiniModel2ViewAdapter (this gives miniModel access to MainModel as well), and Mini View. 
 * It also installs the miniView into the MainView.
 * 
 *
 */
public class MiniController {

	/**
	 * 
	 */
	private MiniView mView;
	/**
	 * 
	 */
	private MiniModel mModel;
	/**
	 * 
	 */
	private IMain2MiniAdapter main2mini = new IMain2MiniAdapter() {

		public void sendMsg(ChatRoomDataPacket<? extends IChatRoomMsg> msg) {
			mModel.sendMsg(msg);
		}

		@Override
		public IChatRoom getChatRoom() {
			return mModel.getChatRoom();
		}

		@Override
		public UUID getCRID() {
			return mModel.getCRID();
		}

		@Override
		public MiniView getPanel() {
			return mView;
		}

		@Override
		public IChatUser getSelf() {
			return mModel.getSelf();
		}

		@Override
		public IChatUser getSelfStub() {
			return mModel.getSelfStub();
		}

		@Override
		public void exitChatRoom() {
			mModel.exitChatRoom();

		}

	};

	/**
	 * @param mini2main - x
	 * @param chatroomName - x
	 * @param CRID - x
	 * @param members -x
	 */
	public MiniController(IMini2MainAdapter mini2main, String chatroomName, UUID CRID, Collection<IChatUser> members) {
		mView = new MiniView(new ImView2mModelAdapter() {

			@Override
			public void sendText(String text) {
				mModel.sendText(text);
			}

			@Override
			public Collection<IChatUser> getMembers() {
				return mModel.getChatRoom().getChatUsers();
			}

			@Override
			public IChatRoom getRoom() {
				return mModel.getChatRoom();
			}

			@Override
			public void exitRoom() {
				mModel.exitChatRoom();

			}

			@Override
			public void sendUnknownText(String text) {
				mModel.sendUnknownText(text);

			}

			@Override
			public void sendImage(String text) {
				try {
					mModel.sendImage(text);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void sendHackMsg(String text) {
				mModel.sendHackMsg(text);

			}

		}, mini2main, chatroomName);
		mModel = new MiniModel(new ImModel2mViewAdapter() {

			@Override
			public void displayComponent(Supplier<JComponent> componentFac) {
				mView.buildComponent(componentFac);
			}

			@Override
			public void displayText(String text, String sender) {
				mView.displayText(text, sender);

			}

		}, mini2main, chatroomName, CRID, members);
	}

	/**
	 * @return the adapter
	 */
	public IMain2MiniAdapter getMainMVCAdapt() {
		return main2mini;
	}

	/**
	 * 
	 */
	public void start() {
		System.out.println("Starting mModel and mView");
		mModel.start();
		mView.start();
	}

}
