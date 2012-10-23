package com.kalimeradev.mipymee.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LeftPanelView extends StackLayoutPanel {

	public LeftPanelView(Unit unit) {
		super(unit);
		// Get the images.
		 Images images = (Images) GWT.create(Images.class);

		// Create a new stack layout panel.
		setPixelSize(200, 400);

		// Add the Mail folders.
		Widget mailHeader = createHeaderWidget("PanelMailHeader", images.mailgroup());
		add(createMailItem(images), mailHeader, 4);

		// Add a list of filters.
		Widget filtersHeader = createHeaderWidget("PanelFiltersHeader", images.filtersgroup());
		add(createFiltersItem(), filtersHeader, 4);

		// Add a list of contacts.
		Widget contactsHeader = createHeaderWidget("PanelContactsHeader", images.contactsgroup());
		add(createContactsItem(images), contactsHeader, 4);

		// Return the stack panel.
		ensureDebugId("cwStackLayoutPanel");

	}

	private void addItem(TreeItem root, ImageResource image, String label) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.append(SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(image).getHTML()));
		sb.appendEscaped(" ").appendEscaped(label);
		root.addItem(sb.toSafeHtml());
	}

	private Widget createHeaderWidget(String text, ImageResource image) {
		// Add the image and text to a horizontal panel
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setHeight("100%");
		hPanel.setSpacing(0);
		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel.add(new Image(image));
		HTML headerText = new HTML(text);
		headerText.setStyleName("cw-StackPanelHeader");
		hPanel.add(headerText);
		return new SimplePanel(hPanel);
	}

	private Widget createContactsItem(Images images) {
		// Create a popup to show the contact info when a contact is clicked
		HorizontalPanel contactPopupContainer = new HorizontalPanel();
		contactPopupContainer.setSpacing(5);
		contactPopupContainer.add(new Image(images.defaultContact()));
		final HTML contactInfo = new HTML();
		contactPopupContainer.add(contactInfo);
		final PopupPanel contactPopup = new PopupPanel(true, false);
		contactPopup.setWidget(contactPopupContainer);

		// Create the list of contacts
		VerticalPanel contactsPanel = new VerticalPanel();
		contactsPanel.setSpacing(4);
		String[] contactNames = new String []{"c1","c2","c3"};
		String[] contactEmails = new String []{"e1","e2","e3"};
		for (int i = 0; i < contactNames.length; i++) {
			final String contactName = contactNames[i];
			final String contactEmail = contactEmails[i];
			final Anchor contactLink = new Anchor(contactName);
			contactsPanel.add(contactLink);

			// Open the contact info popup when the user clicks a contact
			contactLink.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					// Set the info about the contact
					SafeHtmlBuilder sb = new SafeHtmlBuilder();
					sb.appendEscaped(contactName);
					sb.appendHtmlConstant("<br><i>");
					sb.appendEscaped(contactEmail);
					sb.appendHtmlConstant("</i>");
					contactInfo.setHTML(sb.toSafeHtml());

					// Show the popup of contact info
					int left = contactLink.getAbsoluteLeft() + 14;
					int top = contactLink.getAbsoluteTop() + 14;
					contactPopup.setPopupPosition(left, top);
					contactPopup.show();
				}
			});
		}
		return new SimplePanel(contactsPanel);
	}

	private Widget createFiltersItem() {
		VerticalPanel filtersPanel = new VerticalPanel();
		filtersPanel.setSpacing(4);
		for (String filter : new String[]{"a","b","c"}) {
			filtersPanel.add(new CheckBox(filter));
		}
		return new SimplePanel(filtersPanel);
	}

	private Widget createMailItem(Images images) {
		Tree mailPanel = new Tree(images);
		TreeItem mailPanelRoot = mailPanel.addItem("foo@example.com");
		String[] mailFolders = new String[]{"mf1","mf2","mf3","m4","m5"};
		addItem(mailPanelRoot, images.inbox(), mailFolders[0]);
		addItem(mailPanelRoot, images.drafts(), mailFolders[1]);
		addItem(mailPanelRoot, images.templates(), mailFolders[2]);
		addItem(mailPanelRoot, images.sent(), mailFolders[3]);
		addItem(mailPanelRoot, images.trash(), mailFolders[4]);
		mailPanelRoot.setState(true);
		return mailPanel;
	}
	 public interface Images extends Tree.Resources {
		    ImageResource contactsgroup();

		    ImageResource defaultContact();

		    ImageResource drafts();

		    ImageResource filtersgroup();

		    ImageResource inbox();

		    ImageResource mailgroup();

		    ImageResource sent();

		    ImageResource templates();

		    ImageResource trash();

		    /**
		     * Use noimage.png, which is a blank 1x1 image.
		     */
		    @Source("noimage.png")
		    ImageResource treeLeaf();
		  }
}
