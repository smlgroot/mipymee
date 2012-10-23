package com.kalimeradev.mipymee.client.views;

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
		TopMenuView topMenuView= new TopMenuView(); 
		////
		add(topMenuView, DockPanel.NORTH);
		add(new HTML("south"), DockPanel.SOUTH);
		add(new HTML("east"), DockPanel.EAST);
		add(leftPanelView, DockPanel.WEST);

		add(profileView, DockPanel.CENTER);

		setCellWidth(leftPanelView, "210px");
		// Return the content
		ensureDebugId("cwDockPanel");
	}


}
