package com.kalimeradev.mipymee.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ProfileServiceAsync {
	void retrieveCurrentUser(AsyncCallback<ProfileInfo> callback);

	void saveUser(ProfileInfo profileInfo, AsyncCallback<Boolean> callback);
}
