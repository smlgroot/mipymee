package com.kalimeradev.mipymee.client.view;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;

public class ProfileView extends DecoratorPanel  {
	public ProfileView() {

	    // Create a table to layout the form options
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    // Add a title to the form
	    layout.setHTML(0, 0, "Panel Form Title");
	    cellFormatter.setColSpan(0, 0, 2);
	    cellFormatter.setHorizontalAlignment(
	        0, 0, HasHorizontalAlignment.ALIGN_CENTER);

	    // Add some standard form options
	    layout.setHTML(1, 0, "Panel Form Name");
	    layout.setWidget(1, 1, new TextBox());
	    layout.setHTML(2, 0, "Panel Form Description");
	    layout.setWidget(2, 1, new TextBox());

	    // Wrap the content in a DecoratorPanel
	    //DecoratorPanel decPanel = new DecoratorPanel();
	    setWidget(layout);

	}
}
