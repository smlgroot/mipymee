package com.kalimeradev.mipymee.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

public class MainView extends DockPanel {

	public MainView() {

		// Create a Dock Panel
		setStyleName("cw-DockPanel");
		//setSpacing(4);
		//setHorizontalAlignment(DockPanel.ALIGN_CENTER);

		// Add text all around
		////
		ProfileView profileView = new ProfileView();
		LeftPanelView leftPanelView = new LeftPanelView(Unit.EM); 
		////
		add(new HTML("north"), DockPanel.NORTH);//0
		add(new HTML("south"), DockPanel.SOUTH);//1
		add(new HTML("east"), DockPanel.EAST);//2
		add(leftPanelView, DockPanel.WEST);//3

		// Add scrollable text in the center
		add(profileView, DockPanel.CENTER);

		// Return the content
		ensureDebugId("cwDockPanel");
	}


}
