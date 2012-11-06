package com.kalimeradev.mipymee.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

public class ProfileEvent extends GwtEvent<ProfileEventHandler> {
	TreeItem selectedItem;
	private ProfileInfo profileInfo;

	public static Type<ProfileEventHandler> TYPE = new Type<ProfileEventHandler>();

	@Override
	public Type<ProfileEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ProfileEventHandler handler) {
		this.profileInfo = handler.onRequestLoggedUser();
	}

	public ProfileInfo getProfileInfo() {
		return profileInfo;
	}

	public void setProfileInfo(ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;
	}

}