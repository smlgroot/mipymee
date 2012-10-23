package com.kalimeradev.mipymee.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.kalimeradev.mipymee.client.views.MainView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mipymee implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MainView mainView = new MainView();
		RootPanel.get("content").add(mainView);
	}

}
