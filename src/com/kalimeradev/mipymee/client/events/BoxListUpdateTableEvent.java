package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.ibm.icu.util.Calendar;
import com.kalimeradev.mipymee.client.model.BoxObject;

public class BoxListUpdateTableEvent extends GwtEvent<BoxListUpdateTableEvent.BoxListUpdateTableEventHandler> {
	BoxObject boxObject;

	public interface BoxListUpdateTableEventHandler extends EventHandler {
		void onEvent(BoxListUpdateTableEvent event);
	}

	public static Type<BoxListUpdateTableEventHandler> TYPE = new Type<BoxListUpdateTableEventHandler>();

	public BoxListUpdateTableEvent(BoxObject boxObject) {
		//new BoxObject(Long.valueOf(calendar.get(Calendar.YEAR)),Long.valueOf(calendar.get(Calendar.MONTH))))
		this.boxObject = boxObject;
	}

	@Override
	public Type<BoxListUpdateTableEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BoxListUpdateTableEventHandler handler) {
		handler.onEvent(this);
	}

	public BoxObject getBoxObject() {
		return boxObject;
	}

	public void setBoxObject(BoxObject boxObject) {
		this.boxObject = boxObject;
	}
	
	
}
