package com.kalimeradev.mipymee.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mipymee implements EntryPoint {

	private final ProfileServiceAsync profileService = GWT.create(ProfileService.class);

	interface Binder extends UiBinder<DockLayoutPanel, Mipymee> {
	}

	interface GlobalResources extends ClientBundle {
		@NotStrict
		@Source("global.css")
		CssResource css();
	}

	// //
	ProfileInfo profileInfo;
	// //
	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	TopPanel topPanel;
	@UiField
	BoxList boxList;
	@UiField
	BoxDetail boxDetail;
	@UiField
	Shortcuts shortcuts;

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

		profileService.retrieveCurrentUser(new AsyncCallback<ProfileInfo>() {

			public void onSuccess(ProfileInfo profileInfo) {
				Mipymee.this.profileInfo = profileInfo;
				init();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	

	private void init() {

		GWT.<GlobalResources> create(GlobalResources.class).css().ensureInjected();
		// Create the UI defined in Mail.ui.xml.
		DockLayoutPanel outer = binder.createAndBindUi(this);
		// ///
		topPanel.setProfileInfo(profileInfo);
		// ///
		boxList.setListener(new BoxList.Listener() {
			public void onItemSelected(BoxItem item) {
				boxDetail.setItem(item);
			}
		});
		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(outer);
	}

	@UiFactory
	Shortcuts makeShortcuts() { // method name is insignificant
		return new Shortcuts(profileInfo);
	}
}
