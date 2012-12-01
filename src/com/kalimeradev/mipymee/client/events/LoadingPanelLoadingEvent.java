package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LoadingPanelLoadingEvent extends GwtEvent<LoadingPanelLoadingEvent.LoadingPanelLoadingEventHandler> {
	boolean state;
	int eventId;

	public interface LoadingPanelLoadingEventHandler extends EventHandler {
		void onEvent(LoadingPanelLoadingEvent event);
	}

	public static Type<LoadingPanelLoadingEventHandler> TYPE = new Type<LoadingPanelLoadingEventHandler>();

	public LoadingPanelLoadingEvent() {

	}

	public LoadingPanelLoadingEvent(boolean state, int eventId) {
		this.state = state;
		this.eventId = eventId;
	}

	@Override
	public Type<LoadingPanelLoadingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoadingPanelLoadingEventHandler handler) {
		handler.onEvent(this);
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

}
