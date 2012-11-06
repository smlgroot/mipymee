package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.kalimeradev.mipymee.client.model.Factura;

public class BoxDetailEvent extends GwtEvent<BoxDetailEventHandler> {
	Factura selectedItem;

	public static Type<BoxDetailEventHandler> TYPE = new Type<BoxDetailEventHandler>();

	public BoxDetailEvent(Factura item) {
			this.selectedItem = item;
	}

	@Override
	public Type<BoxDetailEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BoxDetailEventHandler handler) {
		handler.onSelectItem(selectedItem);
	}
}