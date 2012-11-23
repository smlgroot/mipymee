/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.kalimeradev.mipymee.client;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.kalimeradev.mipymee.client.Boxes.Images;
import com.kalimeradev.mipymee.client.events.BoxDetailBtnNewEvent;
import com.kalimeradev.mipymee.client.events.BoxDetailEvent;
import com.kalimeradev.mipymee.client.events.BoxDetailEventHandler;
import com.kalimeradev.mipymee.client.events.BoxesUpdateTreeEvent;
import com.kalimeradev.mipymee.client.events.ProfileEvent;
import com.kalimeradev.mipymee.client.model.BoxObject;
import com.kalimeradev.mipymee.client.model.Factura;
import com.kalimeradev.mipymee.client.service.FacturasService;
import com.kalimeradev.mipymee.client.service.FacturasServiceAsync;

/**
 * A composite for displaying the details of an email message.
 */
public class BoxDetail extends ResizeComposite {

	private final FacturasServiceAsync facturasService = GWT.create(FacturasService.class);

	interface Binder extends UiBinder<Widget, BoxDetail> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	Factura factura = null;

	@UiField
	DockLayoutPanel panel;
	@UiField
	TextBox keyName;
	@UiField
	TextBox rfc;
	@UiField
	TextBox iva;
	@UiField
	TextBox total;
	@UiField
	DateBox fecha;
	@UiField
	Button saveButton;
	@UiField
	Button editButton;
	@UiField
	Button cancelButton;
	@UiField
	Element messagesLabel;
	@UiField
	FlexTable flexTable;

	public BoxDetail() {
		// ///////////
		AppUtils.EVENT_BUS.addHandler(BoxDetailEvent.TYPE, new BoxDetailEventHandler() {

			public void onSelectItem(Factura item) {
				setItem(item);
			}
		});
		// ////
		AppUtils.EVENT_BUS.addHandler(BoxDetailBtnNewEvent.TYPE, new BoxDetailBtnNewEvent.BoxDetailBtnNewEventHandler() {

			public void onEvent(BoxDetailBtnNewEvent event) {
				enableNew();
			}

		});

		// ///////
		// ///////////
		initWidget(binder.createAndBindUi(this));
		// /////////
		editButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				enableEdit();
			}
		});
		saveButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				onGuardarClicked();
			}
		});
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (BoxDetail.this.factura != null && BoxDetail.this.factura.getId() != null) {
					enableRead(BoxDetail.this.factura);
				} else {
					panel.setVisible(false);
				}
			}
		});
		// ////////
		editButton.setVisible(false);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		panel.setVisible(false);
		// ////////

	}

	private void setItem(Factura item) {
		factura = item;
		// //
		enableRead(factura);

	}

	void onGuardarClicked() {

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		factura.setRfc(rfc.getValue());
		factura.setIva(iva.getValue());
		factura.setTotal(total.getValue());
		factura.setFechaFactura(fecha.getValue());

		// Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Factura>> violations = validator.validate(factura);

		if (violations.size() <= 0) {

			ProfileEvent profileEvent = new ProfileEvent();
			AppUtils.EVENT_BUS.fireEvent(profileEvent);

			facturasService.saveFactura(factura, profileEvent.getProfileInfo().getEmail(), new AsyncCallback<Factura>() {

				public void onSuccess(Factura result) {
					messagesLabel.setInnerHTML("SUCCESS");
					// AppUtils.EVENT_BUS.fireEvent(new BoxListLeafSelectedEvent(null));
					//
					factura = result;
					enableRead(factura);
					AppUtils.EVENT_BUS.fireEvent(new BoxesUpdateTreeEvent(new BoxObject(factura.getYear(), factura.getMonth())));
					// AppUtils.EVENT_BUS.fireEvent(new BoxListUpdateTableEvent(null));
				}

				public void onFailure(Throwable caught) {
					messagesLabel.setInnerHTML("ERROR");

				}
			});
		} else {
			Iterator<ConstraintViolation<Factura>> iterator = violations.iterator();
			StringBuffer vi = new StringBuffer();
			while (iterator.hasNext()) {
				ConstraintViolation<Factura> violation = iterator.next();
				vi.append(violation.getMessage() + ",");
			}
			messagesLabel.setInnerHTML(vi.toString());
		}
	}

	private void enableNew() {
		factura = new Factura();
		// ///
		keyName.setText("");
		rfc.setText("");
		iva.setText("");
		total.setText("");
		fecha.setValue(null);
		// ////
		enableEdit();
		panel.setVisible(true);
	}

	private void enableEdit() {

		// //
		rfc.setReadOnly(false);
		iva.setReadOnly(false);
		total.setReadOnly(false);
		fecha.setEnabled(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);
		editButton.setVisible(false);
		panel.setVisible(true);
		// ////
	}

	private void enableRead(Factura factura) {

		// ////
		keyName.setText(String.valueOf(factura.getId()));
		rfc.setText(factura.getRfc());
		iva.setText(String.valueOf(factura.getIva()));
		total.setText(String.valueOf(factura.getTotal()));
		fecha.setValue(factura.getFechaFactura());
		// ///

		rfc.setReadOnly(true);
		iva.setReadOnly(true);
		total.setReadOnly(true);
		fecha.setEnabled(false);
		editButton.setVisible(true);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		panel.setVisible(true);
		// ////
		// Table
		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.addStyleName("cw-FlexTable");
		flexTable.setWidth("32em");
		flexTable.setCellSpacing(5);
		flexTable.setCellPadding(3);
		// Add some text
		cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.setHTML(0, 0, "FlexTableDetails");
		cellFormatter.setColSpan(0, 0, 2);

		// Add a button that will add more rows to the table
		Button addRowButton = new Button("addRowButton");
		Button removeRowButton = new Button("removeRowButton");

		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setStyleName("cw-FlexTable-buttonPanel");
		buttonPanel.add(addRowButton);
		buttonPanel.add(removeRowButton);
		flexTable.setWidget(0, 1, buttonPanel);

		cellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);

		// Add two rows to start
		addRow(flexTable);
		addRow(flexTable);

	}
	private static final String CONTEXT_PATH = 
        GWT.getModuleBaseURL().replace(GWT.getModuleName() + "/", "");
	/**
	 * Add a row to the flex table.
	 */
	private void addRow(FlexTable flexTable) {
		int numRows = flexTable.getRowCount();
		//Loads images
		Image image = new Image(CONTEXT_PATH+"/imagesservlet");
		//
		flexTable.setWidget(numRows, 0,image);
		flexTable.setWidget(numRows, 1, new Image(BoxDetail.images.trash()));
		flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
	}

	static Images images = GWT.create(Images.class);

	public interface Images extends ClientBundle, Tree.Resources {
		ImageResource drafts();

		ImageResource home();

		ImageResource inbox();

		ImageResource sent();

		ImageResource trash();

		@Source("noimage.png")
		ImageResource treeLeaf();
	}
}
