package dg46_zh16.miniMVC;

import java.util.function.Supplier;

import javax.swing.JComponent;

/**
 * 
 * Interview for mini model to mini view adapter
 *
 */
public interface ImModel2mViewAdapter {
	/**
	 * displaytext on mini view
	 * @param text string text
	 * @param sender - sender
	 */
	public void displayText(String text, String sender);

	/**
	 * display component on mini view
	 * @param componentFac - factory
	 */
	public void displayComponent(Supplier<JComponent> componentFac);

}
