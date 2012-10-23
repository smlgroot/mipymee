package com.kalimeradev.mipymee.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kalimeradev.mipymee.client.ProfileInfo;
import com.kalimeradev.mipymee.client.ProfileService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

	

	public ProfileInfo retrieveCurrentUser() {
		ProfileInfo result = null;
		UserService userService = UserServiceFactory.getUserService();
		if (userService != null) {
			result = new ProfileInfo();
			User user = userService.getCurrentUser();
			result.setAuthDomain(user.getAuthDomain());
			result.setEmail(user.getEmail());
			result.setFederatedIdentity(user.getFederatedIdentity());
			result.setUserId(user.getUserId());
			result.setName("");//TODO get it from datastore.
			result.setRfc("");//TODO get it from datastore.
			result.setLogoutUrl(userService.createLogoutURL(userService.createLoginURL("/")));
		}
		return result;
	}


}
