package com.kalimeradev.mipymee.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.kalimeradev.mipymee.client.ProfileInfo;
import com.kalimeradev.mipymee.client.ProfileService;
import com.kalimeradev.mipymee.client.ProfileServiceAsync;

public class ProfileView extends DecoratorPanel {
	private ProfileInfo profileInfo;
	private Button btnEdit;
	private HandlerRegistration editHandlerRegistration;

	private final ProfileServiceAsync profileService = GWT.create(ProfileService.class);

	public ProfileView() {

		profileService.retrieveCurrentUser(new AsyncCallback<ProfileInfo>() {

			public void onSuccess(ProfileInfo result) {
				profileInfo = result;
				final FlexTable layout = new FlexTable();
				init(layout);
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void init(final FlexTable layout) {

		// Create a table to layout the form options
		layout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		Label lblTitle = new Label("PROFILE");
		lblTitle.setWidth("250px");
		layout.setWidget(0, 0, lblTitle);
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		// Add some standard form options
		// Email
		layout.setWidget(1, 0, new Label("Email:"));
		// Name
		layout.setWidget(2, 0, new Label("Name:"));
		// RFC
		layout.setWidget(3, 0, new Label("RFC:"));
		// Edit Button
		cellFormatter.setColSpan(4, 0, 2);
		cellFormatter.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);

		enableRead(layout);

		// Wrap the content in a DecoratorPanel
		// DecoratorPanel decPanel = new DecoratorPanel();
		setWidget(layout);
	}

	private void enableRead(final FlexTable layout) {
		// Email
		Label lblEmail = new Label(profileInfo.getEmail());
		layout.setWidget(1, 1, lblEmail);
		// Name
		Label lblName = new Label(profileInfo.getName());
		layout.setWidget(2, 1, lblName);
		// RFC
		Label lblRFC = new Label(profileInfo.getRfc());
		layout.setWidget(3, 1, lblRFC);
		// Edit Button
		btnEdit = new Button("Edit");
		layout.setWidget(4, 0, btnEdit);
		// editHandlerRegistration.removeHandler();
		editHandlerRegistration = btnEdit.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				enableEdit(layout);
			}
		});
	}

	private void enableEdit(final FlexTable layout) {
		// Email
		/*
		 * TextBox txtEmail = new TextBox(); layout.setWidget(1, 1, txtEmail);
		 */
		// Name
		final TextBox txtName = new TextBox();
		txtName.setText(profileInfo.getName());
		layout.setWidget(2, 1, txtName);
		// RFC
		final TextBox txtRFC = new TextBox();
		txtRFC.setText(profileInfo.getRfc());
		layout.setWidget(3, 1, txtRFC);
		// Edit Button
		btnEdit.setText("Save");
		editHandlerRegistration.removeHandler();
		editHandlerRegistration = btnEdit.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				profileInfo.setName(txtName.getText());
				profileInfo.setRfc(txtRFC.getText());
				enableRead(layout);
			}
		});
	}
}