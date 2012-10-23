package com.kalimeradev.mipymee.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.kalimeradev.mipymee.client.view.LeftPanelView;
import com.kalimeradev.mipymee.client.view.MainView;
import com.kalimeradev.mipymee.client.view.ProfileView;

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
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		System.out.println("****************");
		greetingService.retrieveCurrentUser(new AsyncCallback<LoginResult>() {

			public void onSuccess(LoginResult result) {
				MainView mainView= new MainView();
//				SimpleLayoutPanel sss = (SimpleLayoutPanel) mainView.getWidget(3);
//				sss.setWidget(profileView);
				RootPanel.get("content").add(mainView);

				
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

}
