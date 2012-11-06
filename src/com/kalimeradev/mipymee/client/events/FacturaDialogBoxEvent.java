package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class FacturaDialogBoxEvent extends GwtEvent<FacturaDialogBoxEventHandler> {

	public static Type<FacturaDialogBoxEventHandler> TYPE = new Type<FacturaDialogBoxEventHandler>();
	String facturaId;

	public FacturaDialogBoxEvent(String facturaId) {
		this.facturaId = facturaId;
	}

	@Override
	public Type<FacturaDialogBoxEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FacturaDialogBoxEventHandler handler) {
		handler.onRequestFactura(facturaId);
	}

}