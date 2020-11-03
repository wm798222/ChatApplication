package dg46_zh16.miniMVC.host;

import javax.swing.ImageIcon;

/**
 * Class for ImageData that instantiate the IImageData interface
 *
 */
public class ImageData implements IImageData {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2555830322577925265L;

	/**
	 * field for imageIcon
	 */
	ImageIcon imageIcon;

	/**
	 * Constructor
	 * @param imageIcon the imageIcon that contains the image
	 */
	public ImageData(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;

	}

	/**
	 * Getter for ImageIcon
	 */
	@Override
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

}
