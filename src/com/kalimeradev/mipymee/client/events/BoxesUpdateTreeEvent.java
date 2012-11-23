package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.kalimeradev.mipymee.client.model.BoxObject;

public class BoxesUpdateTreeEvent extends GwtEvent<BoxesUpdateTreeEvent.BoxesUpdateTreeEventHandler> {
	BoxObject boxObject ;
	
	public interface BoxesUpdateTreeEventHandler extends EventHandler {
		void onEvent(BoxesUpdateTreeEvent event);
	}

	public static Type<BoxesUpdateTreeEventHandler> TYPE = new Type<BoxesUpdateTreeEventHandler>();

	public BoxesUpdateTreeEvent() {

	}
	public BoxesUpdateTreeEvent(BoxObject boxObject ) {
		this.boxObject=boxObject;
	}
	@Override
	public Type<BoxesUpdateTreeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BoxesUpdateTreeEventHandler handler) {
		handler.onEvent(this);
	}
	public BoxObject getBoxObject() {
		return boxObject;
	}
	public void setBoxObject(BoxObject boxObject) {
		this.boxObject = boxObject;
	}
	
}
