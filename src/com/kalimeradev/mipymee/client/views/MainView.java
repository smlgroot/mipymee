package com.kalimeradev.mipymee.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.kalimeradev.mipymee.client.ProfileService;
import com.kalimeradev.mipymee.client.ProfileServiceAsync;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

public class MainView extends DockPanel {

	private final ProfileServiceAsync profileService = GWT.create(ProfileService.class);

	public MainView() {

		// Create a Dock Panel
		setStyleName("cw-DockPanel");
		// setSpacing(4);
		// setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		profileService.retrieveCurrentUser(new AsyncCallback<ProfileInfo>() {

			public void onSuccess(ProfileInfo profileInfo) {
				ProfileView profileView = new ProfileView(profileInfo);
				LeftPanelView leftPanelView = new LeftPanelView(Unit.EM);
				TopMenuView topMenuView = new TopMenuView(profileInfo);

				add(topMenuView, DockPanel.NORTH);
				add(new HTML("south"), DockPanel.SOUTH);
				add(new HTML("east"), DockPanel.EAST);
				add(leftPanelView, DockPanel.WEST);

				add(profileView, DockPanel.CENTER);

				setCellWidth(leftPanelView, "210px");
				// Return the content
				ensureDebugId("cwDockPanel");
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

}
