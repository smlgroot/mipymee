package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

public interface FacturaDialogBoxEventHandler extends EventHandler {
	void onRequestFactura(String facturaId);
}
