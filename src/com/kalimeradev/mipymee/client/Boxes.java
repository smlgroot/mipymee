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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.kalimeradev.mipymee.client.events.BoxEvent;
import com.kalimeradev.mipymee.client.events.BoxEventHandler;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

/**
 * A tree displaying a set of email folders.
 */
public class Boxes extends Composite {

	/**
	 * Specifies the images that will be bundled for this Composite and specify that tree's images should also be included in the same bundle.
	 */
	public interface Images extends ClientBundle, Tree.Resources {
		ImageResource drafts();

		ImageResource home();

		ImageResource inbox();

		ImageResource sent();

		ImageResource trash();

		@Source("noimage.png")
		ImageResource treeLeaf();
	}

	public Tree tree;

	/**
	 * Constructs a new mailboxes widget with a bundle of images.
	 * 
	 * @param images
	 *            a bundle that provides the images for this widget
	 */
	public Boxes(ProfileInfo profileInfo) {
		Images images = GWT.create(Images.class);

		tree = new Tree(images);
		TreeItem root = new TreeItem(imageItemHTML(images.home(), profileInfo.getEmail()));
		/////
		TreeItem facturas = addImageItem(root, "Facturas", images.inbox());
		TreeItem yearA = new TreeItem("2012");
		TreeItem october= new TreeItem("October");
		yearA.addItem(october);
		facturas.addItem(yearA);
		facturas.addItem("2013");
		/////
		tree.addItem(root);

		
		addImageItem(root, "Recibos", images.drafts());

		root.setState(true);
		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			
			public void onSelection(SelectionEvent<TreeItem> event) {
				System.out.println("Selected item:"+event.getSelectedItem().toString());
				AppUtils.EVENT_BUS.fireEvent(new BoxEvent(event.getSelectedItem()));
			}
		});
		initWidget(tree);
	}

	/**
	 * A helper method to simplify adding tree items that have attached images. {@link #addImageItem(TreeItem, String, ImageResource) code}
	 * 
	 * @param root
	 *            the tree item to which the new item will be added.
	 * @param title
	 *            the text associated with this item.
	 */
	private TreeItem addImageItem(TreeItem root, String title, ImageResource imageProto) {
		TreeItem item = new TreeItem(imageItemHTML(imageProto, title));
		root.addItem(item);
		return item;
	}

	/**
	 * Generates HTML for a tree item with an attached icon.
	 * 
	 * @param imageProto
	 *            the image prototype to use
	 * @param title
	 *            the title of the item
	 * @return the resultant HTML
	 */
	private String imageItemHTML(ImageResource imageProto, String title) {
		return AbstractImagePrototype.create(imageProto).getHTML() + " " + title;
	}

	
}
