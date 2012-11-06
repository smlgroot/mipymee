package com.kalimeradev.mipymee.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.kalimeradev.mipymee.client.model.ProfileInfo;
import com.kalimeradev.mipymee.client.service.ProfileService;
import com.kalimeradev.mipymee.client.service.ProfileServiceAsync;

public class MainView extends DockPanel {

	private final ProfileServiceAsync profileService = GWT.create(ProfileService.class);
	FlowPanel centerPanel;

	public MainView() {

		// Create a Dock Panel
		setStyleName("cw-DockPanel");
		// setSpacing(4);
		// setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		profileService.retrieveCurrentUser(new AsyncCallback<ProfileInfo>() {

			public void onSuccess(ProfileInfo profileInfo) {
				//ProfileView profileView = new ProfileView(profileInfo);
				//centerPanel = new FlowPanel();
				//centerPanel.add(profileView);
				LeftPanelView leftPanelView = new LeftPanelView(Unit.EM);
				TopMenuView topMenuView = new TopMenuView(profileInfo, MainView.this);

				add(topMenuView, DockPanel.NORTH);
				add(new HTML("east"), DockPanel.EAST);
				add(new HTML("south"), DockPanel.SOUTH);
				add(leftPanelView, DockPanel.WEST);
				
				centerPanel = new FlowPanel();
				add(centerPanel, DockPanel.CENTER);

				setCellWidth(leftPanelView, "210px");
				setCellWidth(centerPanel, "800px");
				// Return the content
				ensureDebugId("cwDockPanel");
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

}
