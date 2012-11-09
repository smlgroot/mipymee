package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class BoxDetailBtnNewEvent extends GwtEvent<BoxDetailBtnNewEvent.BoxDetailBtnNewEventHandler> {

	public interface BoxDetailBtnNewEventHandler extends EventHandler {
		void onEvent(BoxDetailBtnNewEvent event);
	}

	public static Type<BoxDetailBtnNewEventHandler> TYPE = new Type<BoxDetailBtnNewEventHandler>();

	// public static Type<BoxDetailBtnNewEventHandler> getType() {
	// return TYPE;
	// }

	public BoxDetailBtnNewEvent() {

	}

	@Override
	public Type<BoxDetailBtnNewEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BoxDetailBtnNewEventHandler handler) {
		handler.onEvent(this);
	}
}
