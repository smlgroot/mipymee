package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.kalimeradev.mipymee.client.model.Factura;

public interface BoxDetailEventHandler extends EventHandler {
	void onSelectItem(Factura item);
}
