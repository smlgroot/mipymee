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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.kalimeradev.mipymee.client.events.BoxDetailBtnNewEvent;
import com.kalimeradev.mipymee.client.events.BoxDetailEvent;
import com.kalimeradev.mipymee.client.events.BoxListLeafSelectedEvent;
import com.kalimeradev.mipymee.client.events.BoxListLeafSelectedEventHandler;
import com.kalimeradev.mipymee.client.events.BoxListUpdateTableEvent;
import com.kalimeradev.mipymee.client.events.ProfileEvent;
import com.kalimeradev.mipymee.client.model.BoxObject;
import com.kalimeradev.mipymee.client.model.Factura;
import com.kalimeradev.mipymee.client.service.FacturasService;
import com.kalimeradev.mipymee.client.service.FacturasServiceAsync;

/**
 * A composite that displays a list of emails that can be selected.
 */
public class BoxList extends ResizeComposite {

	private final FacturasServiceAsync facturasService = GWT.create(FacturasService.class);
	Factura[] facturaItems;

	/**
	 * Callback when mail items are selected.
	 */

	interface Binder extends UiBinder<Widget, BoxList> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private static final Binder binder = GWT.create(Binder.class);
	static final int VISIBLE_EMAIL_COUNT = 20;
	@UiField
	MenuBar menuBar;
	@UiField
	FlexTable header;
	@UiField
	FlexTable table;
	@UiField
	SelectionStyle selectionStyle;
	@UiField
	MenuItem commandNuevo;
	@UiField
	MenuItem commandBorrar;
	@UiField
	MenuItem commandMover;

	private int startIndex, selectedRow = -1;
	private NavBar navBar;

	public BoxList() {
		// //////////

		// /////////
		initWidget(binder.createAndBindUi(this));
		navBar = new NavBar(this);

		ProfileEvent profileEvent = new ProfileEvent();
		AppUtils.EVENT_BUS.fireEvent(profileEvent);

		iniHandlers(profileEvent.getProfileInfo().getEmail());
		// Commands
		commandNuevo.setCommand(new Command() {

			public void execute() {
				AppUtils.EVENT_BUS.fireEvent(new BoxDetailBtnNewEvent());
			}
		});
		commandMover.setVisible(false);
		commandBorrar.setVisible(false);
		//
	}

	/*
	 * @Override protected void onLoad() { // Select the first row if none is selected. if (selectedRow == -1) { selectRow(0); } }
	 */

	void newer() {
		// Move back a page.
		startIndex -= VISIBLE_EMAIL_COUNT;
		if (startIndex < 0) {
			startIndex = 0;
		} else {
			styleRow(selectedRow, false);
			selectedRow = -1;
			update();
		}
	}

	void older() {
		// Move forward a page.
		startIndex += VISIBLE_EMAIL_COUNT;
		if (startIndex >= facturaItems.length) {
			startIndex -= VISIBLE_EMAIL_COUNT;
		} else {
			styleRow(selectedRow, false);
			selectedRow = -1;
			update();
		}
	}

	@UiHandler("table")
	void onTableClicked(ClickEvent event) {
		// Select the row that was clicked (-1 to account for header row).
		Cell cell = table.getCellForEvent(event);
		if (cell != null) {
			int row = cell.getRowIndex();
			int column = cell.getCellIndex();
			if (column>0) {
				selectRow(row);
			}
		}
	}

	/**
	 * Initializes the table so that it contains enough rows for a full page of emails. Also creates the images that will be used as 'read' flags.
	 */
	private void initTable() {
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "20px");
		header.getColumnFormatter().setWidth(1, "128px");
		header.getColumnFormatter().setWidth(2, "192px");
		header.getColumnFormatter().setWidth(3, "192px");
		header.getColumnFormatter().setWidth(4, "192px");
		header.getColumnFormatter().setWidth(5, "256px");

		CheckBox checkbox = new CheckBox();
		header.setWidget(0, 0, checkbox);
		header.setText(0, 1, "RFC");
		header.setText(0, 2, "IVA");
		header.setText(0, 3, "Total");
		header.setText(0, 4, "Fecha");
		header.setWidget(0, 5, navBar);
		header.getCellFormatter().setHorizontalAlignment(0, 5, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		table.getColumnFormatter().setWidth(0, "20px");
		table.getColumnFormatter().setWidth(1, "128px");
		table.getColumnFormatter().setWidth(2, "192px");
		table.getColumnFormatter().setWidth(3, "192px");
		table.getColumnFormatter().setWidth(4, "192px");
		table.getColumnFormatter().setWidth(5, "256px");

	}

	/**
	 * Selects the given row (relative to the current page).
	 * 
	 * @param row
	 *            the row to be selected
	 */
	private void selectRow(int row) {
		// When a row (other than the first one, which is used as a header) is
		// selected, display its associated MailItem.
		Factura item = facturaItems[startIndex + row];
		if (item == null) {
			return;
		}

		styleRow(selectedRow, false);
		styleRow(row, true);

		selectedRow = row;

		// ///
		AppUtils.EVENT_BUS.fireEvent(new BoxDetailEvent(item));
		// ///
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			String style = selectionStyle.selectedRow();

			if (selected) {
				table.getRowFormatter().addStyleName(row, style);
			} else {
				table.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

	private void update() {

		// Update the older/newer buttons & label.
		int count = facturaItems.length;
		int max = startIndex + VISIBLE_EMAIL_COUNT;
		if (max > count) {
			max = count;
		}
		// Clear table.
		for (int i = 0; i < table.getRowCount(); i++) {
			table.removeRow(i);
		}
		if (count > 0) {
			// Update the nav bar.
			navBar.update(startIndex, count, max);

			// Show the selected emails.
			int i = 0;
			for (; i < VISIBLE_EMAIL_COUNT; ++i) {
				// Don't read past the end.
				if (startIndex + i >= facturaItems.length) {
					break;
				}

				Factura item = facturaItems[startIndex + i];

				// Add a new row to the table, then set each of its columns to the
				// email's sender and subject values.
				CheckBox checkbox = new CheckBox();
				table.setWidget(i, 0, checkbox);
				table.setText(i, 1, item.getRfc());
				table.setText(i, 2, String.valueOf(item.getIva()));
				table.setText(i, 3, String.valueOf(item.getTotal()));
				table.setText(i, 4, String.valueOf(item.getFechaFactura()));
			}
		} else {
			// Clear table.
			for (int i = 0; i < table.getRowCount(); i++) {
				table.removeRow(i);
			}
			table.setText(0, 0, "No se encontraron resultados");
		}
	}

	private void iniHandlers(final String clienteId) {
		// /////

		AppUtils.EVENT_BUS.addHandler(BoxListUpdateTableEvent.TYPE, new BoxListUpdateTableEvent.BoxListUpdateTableEventHandler() {

			public void onEvent(BoxListUpdateTableEvent event) {
				retrieveFacturasByUserId(clienteId, event.getBoxObject());
				update();
			}

		});
		// //
		AppUtils.EVENT_BUS.addHandler(BoxListLeafSelectedEvent.TYPE, new BoxListLeafSelectedEventHandler() {

			public void onLeafSelected(TreeItem selectedItem) {
				if (selectedItem != null) {
					Object object = selectedItem.getUserObject();
					if (object != null) {
						BoxObject boxObject = (BoxObject) object;
						retrieveFacturasByUserId(clienteId, boxObject);
					}
				}
			}

		});
		// //

	}

	private void retrieveFacturasByUserId(String clienteId, BoxObject boxObject) {
		// ///////

		facturasService.retrieveFacturasByUserId(clienteId, boxObject, new AsyncCallback<Factura[]>() {

			public void onSuccess(Factura[] facturas) {
				facturaItems = facturas;
				initTable();
				update();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				caught.printStackTrace();
			}
		});

		// ///////
	}
}
