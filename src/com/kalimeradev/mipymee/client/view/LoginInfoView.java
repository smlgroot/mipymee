package com.kalimeradev.mipymee.client.view;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.kalimeradev.mipymee.client.LoginResult;

public class LoginInfoView extends FlowPanel implements ClickHandler {
    private final SpanElement userElem;

    private final AnchorElement linkElem;

    /**
     * @param author
     *          the current author
     * @param logoutUrl
     *          a url that can be used to logout
     */
    public LoginInfoView( LoginResult info) {
      final Element element = getElement();
      userElem = element.appendChild(Document.get().createSpanElement());
       element.appendChild(Document.get().createBRElement());
      linkElem = element.appendChild(Document.get().createAnchorElement());

      element.setId("login-info");

      userElem.setId("login-info-name");
      userElem.setInnerText(info.getUser());

      linkElem.setId("login-info-link");
      linkElem.setInnerText("sign out");
      linkElem.setHref(info.getUrl());
    }

    public void onClick(ClickEvent event) {
      Window.alert("click");
    }
  }