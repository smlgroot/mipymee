package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.TreeItem;

public interface BoxEventHandler extends EventHandler {
	void onLeafSelected(TreeItem selectedItem);
}
