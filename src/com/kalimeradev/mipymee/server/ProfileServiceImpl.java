package com.kalimeradev.mipymee.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kalimeradev.mipymee.client.ProfileService;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

	public ProfileInfo retrieveCurrentUser() {
		ProfileInfo result = null;
		UserService userService = UserServiceFactory.getUserService();
		if (userService != null) {
			User user = userService.getCurrentUser();
			Entity entity = null;
			// Load from data store
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key key = KeyFactory.createKey("User", user.getEmail());
			try {
				entity = datastore.get(key);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			//
			result = new ProfileInfo();
			result.setAuthDomain(user.getAuthDomain());
			result.setEmail(user.getEmail());
			result.setFederatedIdentity(user.getFederatedIdentity());
			result.setUserId(user.getUserId());
			if (entity != null) {
				result.setName((String) entity.getProperty("name"));
				result.setRfc((String) entity.getProperty("rfc"));
			}
			result.setLogoutUrl(userService.createLogoutURL(userService.createLoginURL("/")));

		}
		return result;
	}

	public boolean saveUser(ProfileInfo profileInfo) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity user = new Entity("User", profileInfo.getEmail());
		user.setProperty("name", profileInfo.getName());
		user.setProperty("rfc", profileInfo.getRfc());

		datastore.put(user);

		return true;
	}

}
