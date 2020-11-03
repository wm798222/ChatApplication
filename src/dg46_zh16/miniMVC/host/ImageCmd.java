package dg46_zh16.miniMVC.host;

import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JLabel;

import common.msg.AChatMsgCmd;
import common.msg.ChatRoomDataPacket;
import provided.datapacket.IDataPacketID;

/**
 * Image command that you can pass to the user who requested it.
 *
 */
public class ImageCmd extends AChatMsgCmd<IImageData> {

	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 2444830322577925265L;

	/**
	 * Constructor
	 */
	public ImageCmd() {
	}

	@Override
	public Void apply(IDataPacketID index, ChatRoomDataPacket<IImageData> host, Void... params) {
		this.cmd2model.displayComponent(new Supplier<JComponent>() {
			public JComponent get() {
				//				System.out.println("ImageCmd object is: " + host.getData().getImageIcon().getImage());
				return new JLabel(host.getData().getImageIcon());
			}
		}, "title");
		//		this.cmd2model.displayComponent(host.getData().getImageIcon());
		//			}
		//		});

		return null;
	}

}
