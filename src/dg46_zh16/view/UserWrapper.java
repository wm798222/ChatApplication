package dg46_zh16.view;

import java.rmi.RemoteException;

import common.IUser;

/**
 * User class wrapper 
 *
 */
public class UserWrapper {

	/**
	 * field for user
	 */
	IUser user;

	/**
	 * Constructor
	 * @param user input user
	 */
	public UserWrapper(IUser user) {
		this.user = user;
	}

	/**
	 * Get name 
	 */
	public String toString() {
		try {
			return user.getName();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Getter for User
	 * @return the IUser object of the user
	 */
	public IUser get() {
		return user;
	}
}
