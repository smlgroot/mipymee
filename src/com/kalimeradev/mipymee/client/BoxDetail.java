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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.kalimeradev.mipymee.client.events.BoxDetailEvent;
import com.kalimeradev.mipymee.client.events.BoxDetailEventHandler;
import com.kalimeradev.mipymee.client.events.BoxEvent;
import com.kalimeradev.mipymee.client.events.ProfileEvent;
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
	TextBox rfc;
	@UiField
	TextBox iva;
	@UiField
	TextBox total;
	@UiField
	DateBox fecha;
	@UiField
	Button guardarButton;
	@UiField
	Element messagesLabel;
	@UiField
	HTML body;

	private HandlerRegistration enableReadHandler;

	private HandlerRegistration guardarHandler;

	public BoxDetail() {
		// ///////////
		AppUtils.EVENT_BUS.addHandler(BoxDetailEvent.TYPE, new BoxDetailEventHandler() {

			public void onSelectItem(Factura item) {
				setItem(item);
			}
		});
		// ///////////
		initWidget(binder.createAndBindUi(this));
		panel.setVisible(false);
		//////////
		
	}

	private void setItem(Factura item) {
		factura = item;
		// //
		enableRead(factura);
		// WARNING: For the purposes of this demo, we're using HTML directly, on
		// the assumption that the "server" would have appropriately scrubbed the
		// HTML. Failure to do so would open your application to XSS attacks.
		body.setHTML("TEMP TEMP.");
	}

	void onGuardarClicked() {

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		factura.setRfc(rfc.getValue());
		factura.setIva(iva.getValue());
		factura.setTotal(total.getValue());
		factura.setFecha(fecha.getValue());

		// Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Factura>> violations = validator.validate(factura);

		if (violations.size() <= 0) {

			ProfileEvent profileEvent = new ProfileEvent();
			AppUtils.EVENT_BUS.fireEvent(profileEvent);

			facturasService.saveFactura(factura, profileEvent.getProfileInfo().getEmail(), new AsyncCallback<String>() {

				public void onSuccess(String result) {
					messagesLabel.setInnerHTML("SUCCESS");
					AppUtils.EVENT_BUS.fireEvent(new BoxEvent(null));
					enableRead(factura);
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

	private void enableEdit() {
		enableReadHandler.removeHandler();
		guardarHandler = guardarButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				onGuardarClicked();
			}
		});
		// //
		rfc.setReadOnly(false);
		iva.setReadOnly(false);
		total.setReadOnly(false);
		fecha.setEnabled(true);
		guardarButton.setText("Guardar");
		// ////
	}

	private void enableRead(Factura factura) {
		if (guardarHandler != null) {
			guardarHandler.removeHandler();
		}
		// ////
		rfc.setText(factura.getRfc());
		iva.setText(String.valueOf(factura.getIva()));
		total.setText(String.valueOf(factura.getTotal()));
		fecha.setValue(factura.getFecha());
		// ///

		rfc.setReadOnly(true);
		iva.setReadOnly(true);
		total.setReadOnly(true);
		fecha.setEnabled(false);
		guardarButton.setText("Editar");
		// ////
		enableReadHandler = guardarButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				enableEdit();
			}
		});

		// messagesLabel;
	}
}
