package dg46_zh16.miniMVC;

import java.util.Collection;
import java.util.UUID;

import common.IChatUser;
import dg46_zh16.model.IMain2MiniAdapter;

/**
 * 
 *
 */
public interface IMiniControllerFac {

	/**
	 * 
	 * Interface for IMiniControllerFac
	 * @param mini2main mini model to main model adapter
	 * @param username string for user name
	 * @param CRID unique chat room id
	 * @param members collection of ichat users
	 * @return IMain2MiniAdapter
	 */
	public abstract IMain2MiniAdapter make(IMini2MainAdapter mini2main, String username, UUID CRID,
			Collection<IChatUser> members);

}
