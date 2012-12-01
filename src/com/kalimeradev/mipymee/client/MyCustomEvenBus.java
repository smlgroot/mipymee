package com.kalimeradev.mipymee.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.kalimeradev.mipymee.client.events.LoadingPanelLoadingEvent;

public class MyCustomEvenBus extends SimpleEventBus {
	Map<Integer, Integer> processMap;

	public MyCustomEvenBus() {
		processMap= new HashMap();
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		System.out.println();
		AppUtils.EVENT_BUS.fireEvent(new LoadingPanelLoadingEvent(true,1));
		super.fireEvent(event);
		AppUtils.EVENT_BUS.fireEvent(new LoadingPanelLoadingEvent(false,1));
	}

	@Override
	public <H> HandlerRegistration addHandler(Type<H> type, H handler) {
		System.out.println();
		return super.addHandler(type, handler);
	}
}
