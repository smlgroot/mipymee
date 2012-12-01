/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.kalimeradev.mipymee.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.kalimeradev.mipymee.client.events.LoadingPanelLoadingEvent;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
public class LoadingPanel extends Composite {
	Map<Integer, Integer> processMap;

	interface Binder extends UiBinder<Widget, LoadingPanel> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	public interface Images extends ClientBundle {
		@Source("014.gif")
		ImageResource loadingImg();
	}

	interface SelectionStyle extends CssResource {
		String loadingTrue();

		String loadingFalse();
	}

	@UiField
	FlowPanel bar;

	@UiField
	SelectionStyle selectionStyle;

	public LoadingPanel() {
		processMap = new HashMap<Integer, Integer>();
		final Images images = GWT.create(Images.class);
		// final Animation customAnimation = new CustomAnimation();
		//
		AppUtils.EVENT_BUS.addHandler(LoadingPanelLoadingEvent.TYPE, new LoadingPanelLoadingEvent.LoadingPanelLoadingEventHandler() {
			public void onEvent(LoadingPanelLoadingEvent event) {
				int widgetCount;
				int processIndex;
				if (event.isState()) {

					widgetCount = bar.getWidgetCount();
					if (!processMap.containsKey(event.getEventId())) {
						bar.add(new Image(images.loadingImg()));
					}
					processMap.put(event.getEventId(), widgetCount);
					
					//
					//
					// bar.setClassName(selectionStyle.loadingTrue());
				} else {
					processIndex = processMap.get(event.getEventId());
					processMap.remove(event.getEventId());
					bar.remove(processIndex);
					// bar.setClassName(selectionStyle.loadingFalse());
				}
			}
		});
		//

		initWidget(binder.createAndBindUi(this));

		// bar.setClassName(selectionStyle.loadingFalse());
	}

	// public class CustomAnimation extends Animation {
	// boolean flag;
	//
	// @Override
	// protected void onUpdate(double progress) {
	// System.out.println(progress);
	// if (flag) {
	// bar.setClassName(selectionStyle.loadingTrueA());
	// } else {
	// bar.setClassName(selectionStyle.loadingTrueB());
	// }
	// flag = !flag;
	// }
	//
	// }
}
