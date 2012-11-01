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
import com.google.gwt.i18n.client.NumberFormat;
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
import com.kalimeradev.mipymee.client.model.Factura;

/**
 * A simple example of an 'about' dialog box.
 */
public class MyDialogBox extends DialogBox {

	private final FacturasServiceAsync facturasService = GWT.create(FacturasService.class);

	interface Binder extends UiBinder<Widget, MyDialogBox> {
	}

	private static final Binder binder = GWT.create(Binder.class);

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

	public MyDialogBox() {
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

		Factura factura = new Factura();
		factura.setRfc(rfc.getValue());
		factura.setIva(iva.getValue());
		factura.setTotal(total.getValue());
		factura.setFecha(fecha.getValue());

		// Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Factura>> violations = validator.validate(factura);

		if (violations.size() <= 0) {

			facturasService.saveFactura(factura, "test@example.com", new AsyncCallback<Boolean>() {

				public void onSuccess(Boolean result) {
					messagesLabel.setText("SUCCESS");

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
}
