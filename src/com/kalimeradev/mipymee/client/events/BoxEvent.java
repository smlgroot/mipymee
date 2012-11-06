package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.TreeItem;

public class BoxEvent extends GwtEvent<BoxEventHandler> {
	TreeItem selectedItem;

	public static Type<BoxEventHandler> TYPE = new Type<BoxEventHandler>();

	public BoxEvent(TreeItem selectedItem) {
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
		}
	}

	@Override
	public Type<BoxEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BoxEventHandler handler) {
		handler.onLeafSelected(selectedItem);
	}
}