package com.kalimeradev.mipymee.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("profile")
public interface ProfileService extends RemoteService {
	ProfileInfo retrieveCurrentUser(); 
	boolean saveUser(ProfileInfo profileInfo);
}
