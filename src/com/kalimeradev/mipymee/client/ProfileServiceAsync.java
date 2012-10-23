package com.kalimeradev.mipymee.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ProfileServiceAsync {
	void retrieveCurrentUser(AsyncCallback<ProfileInfo> callback);
}
