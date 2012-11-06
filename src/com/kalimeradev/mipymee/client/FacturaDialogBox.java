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
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.kalimeradev.mipymee.client.events.BoxEvent;
import com.kalimeradev.mipymee.client.events.FacturaDialogBoxEvent;
import com.kalimeradev.mipymee.client.events.FacturaDialogBoxEventHandler;
import com.kalimeradev.mipymee.client.events.ProfileEvent;
import com.kalimeradev.mipymee.client.model.Factura;
import com.kalimeradev.mipymee.client.service.FacturasService;
import com.kalimeradev.mipymee.client.service.FacturasServiceAsync;

/**
 * A simple example of an 'about' dialog box.
 */
public class FacturaDialogBox extends DialogBox {

	private final FacturasServiceAsync facturasService = GWT.create(FacturasService.class);

	interface Binder extends UiBinder<Widget, FacturaDialogBox> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	Factura factura = null;

	@UiField
	TextBox rfc;
	@UiField
	TextBox iva;
	@UiField
	TextBox total;
	@UiField
	DateBox fecha;

	@UiField
	Button closeButton;
	@UiField
	Button guardarButton;
	@UiField
	Label messagesLabel;

	public FacturaDialogBox() {
		// //
		AppUtils.EVENT_BUS.addHandler(FacturaDialogBoxEvent.TYPE, new FacturaDialogBoxEventHandler() {

			public void onRequestFactura(String facturaId) {
				facturasService.retrieveFacturaByFacturaId(facturaId, new AsyncCallback<Factura>() {

					public void onSuccess(Factura result) {
						enbableRead(result);
					}

					public void onFailure(Throwable caught) {
						messagesLabel.setText("Error al leer la factura.");
					}
				});

				factura = new Factura();
			}

		});
		// //
		// Use this opportunity to set the dialog's caption.
		setText("Nuevo");
		setWidget(binder.createAndBindUi(this));

		setAnimationEnabled(true);
		setGlassEnabled(true);
	}

	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent preview) {
		super.onPreviewNativeEvent(preview);

		NativeEvent evt = preview.getNativeEvent();
		if (evt.getType().equals("keydown")) {
			// Use the popup's key preview hooks to close the dialog when either
			// enter or escape is pressed.
			switch (evt.getKeyCode()) {
			case KeyCodes.KEY_ENTER:
			case KeyCodes.KEY_ESCAPE:
				hide();
				break;
			}
		}
	}

	@UiHandler("closeButton")
	void onSignOutClicked(ClickEvent event) {
		hide();
	}

	@UiHandler("guardarButton")
	void onGuardarClicked(ClickEvent event) {

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
					messagesLabel.setText("SUCCESS");
					enbableRead(null);
					AppUtils.EVENT_BUS.fireEvent(new BoxEvent(null));
				}

				public void onFailure(Throwable caught) {
					messagesLabel.setText("ERROR");

				}
			});
		} else {
			Iterator<ConstraintViolation<Factura>> iterator = violations.iterator();
			StringBuffer vi = new StringBuffer();
			while (iterator.hasNext()) {
				ConstraintViolation<Factura> violation = iterator.next();
				vi.append(violation.getMessage() + "<br/>");
			}
			messagesLabel.setText(vi.toString());
		}
		// hide();
	}

	private void enableEdit() {

		rfc.setReadOnly(false);
		iva.setReadOnly(false);
		total.setReadOnly(false);
		fecha.setEnabled(true);

		closeButton.setText("Cancelar");
		guardarButton.setText("Guardar");
		messagesLabel.setText("");
	}

	private void enbableRead(Factura factura) {
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

		closeButton.setText("Cerrar");
		guardarButton.setText("Editar");
		// messagesLabel;
	}
}
