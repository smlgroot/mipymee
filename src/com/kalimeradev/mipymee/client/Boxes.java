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

import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.kalimeradev.mipymee.client.events.BoxListLeafSelectedEvent;
import com.kalimeradev.mipymee.client.events.BoxesUpdateTreeEvent;
import com.kalimeradev.mipymee.client.model.BoxObject;
import com.kalimeradev.mipymee.client.model.ProfileInfo;
import com.kalimeradev.mipymee.client.service.FacturasService;
import com.kalimeradev.mipymee.client.service.FacturasServiceAsync;

/**
 * A tree displaying a set of email folders.
 */
public class Boxes extends Composite {

	private final FacturasServiceAsync facturasService = GWT.create(FacturasService.class);
	private ProfileInfo profileInfo;
	private TreeItem facturas;
	private TreeItem recibos;

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

	/**
	 * Constructs a new mailboxes widget with a bundle of images.
	 * 
	 * @param images
	 *            a bundle that provides the images for this widget
	 */
	public Boxes(final ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;
		final Images images = GWT.create(Images.class);

		Tree tree;
		tree = new Tree(images);
		TreeItem root = new TreeItem(imageItemHTML(images.home(), profileInfo.getEmail()));
		facturas = addImageItem(root, "Facturas", images.inbox());
		recibos = addImageItem(root, "Recibos", images.drafts());

		tree.addItem(root);
		root.setState(true);
		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			public void onSelection(SelectionEvent<TreeItem> event) {
				System.out.println("Selected item:" + event.getSelectedItem().toString());
				AppUtils.EVENT_BUS.fireEvent(new BoxListLeafSelectedEvent(event.getSelectedItem()));
			}
		});
		initWidget(tree);
		//
		updateTree(null);
		//
		// ////
		AppUtils.EVENT_BUS.addHandler(BoxesUpdateTreeEvent.TYPE, new BoxesUpdateTreeEvent.BoxesUpdateTreeEventHandler() {

			public void onEvent(BoxesUpdateTreeEvent event) {
				updateTree(event.getBoxObject());
			}

		});

		// ///////
	}

	public void updateTree(final BoxObject selectDefaultObject) {
		facturasService.retrieveFechas(profileInfo.getEmail(), new AsyncCallback<Map<Long, Long[]>>() {

			public void onSuccess(Map<Long, Long[]> result) {
				//
				//
				facturas.removeItems();
				recibos.removeItems();
				// Years
				Iterator<Long> iteratorYears = result.keySet().iterator();
				while (iteratorYears.hasNext()) {
					Long year = iteratorYears.next();
					TreeItem yearAux = new TreeItem(String.valueOf(year));

					yearAux.setUserObject(new BoxObject(year, 0L));
					facturas.addItem(yearAux);
					// Months
					Long[] monthsArray = result.get(year);
					for (Long month : monthsArray) {
						TreeItem monthAux = new TreeItem(AppUtils.months.get(month));
						monthAux.setUserObject(new BoxObject(year, month));
						yearAux.addItem(monthAux);
						// check if it is the selectDefaultObject item.
						if (selectDefaultObject!=null) {
							System.out.println(selectDefaultObject.getYear().longValue() + "==" + year + "&&" + selectDefaultObject.getMonth().longValue() + "==" + month);
							System.out.println((selectDefaultObject.getYear().longValue() == year) + "&&" + (selectDefaultObject.getMonth().longValue() == month));
						}
						if (selectDefaultObject != null && selectDefaultObject.getYear().longValue() == year && selectDefaultObject.getMonth().longValue() == month) {
							monthAux.getParentItem().getParentItem().setState(true);
							monthAux.getParentItem().setState(true);
							monthAux.setState(true,true);
							monthAux.setSelected(true);
						}
					}
				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
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
