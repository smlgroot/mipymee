package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.TreeItem;

public class BoxListLeafSelectedEvent extends GwtEvent<BoxListLeafSelectedEventHandler> {
	TreeItem selectedItem;

	public static Type<BoxListLeafSelectedEventHandler> TYPE = new Type<BoxListLeafSelectedEventHandler>();

	public BoxListLeafSelectedEvent(TreeItem selectedItem) {
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
		}
	}

	@Override
	public Type<BoxListLeafSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BoxListLeafSelectedEventHandler handler) {
		handler.onLeafSelected(selectedItem);
	}
}