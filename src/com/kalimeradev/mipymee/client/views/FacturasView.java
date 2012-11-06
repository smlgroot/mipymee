package com.kalimeradev.mipymee.client.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.kalimeradev.mipymee.client.ContactDatabase;
import com.kalimeradev.mipymee.client.ContactDatabase.Category;
import com.kalimeradev.mipymee.client.ContactDatabase.ContactInfo;
import com.kalimeradev.mipymee.client.model.ProfileInfo;
import com.kalimeradev.mipymee.client.service.ProfileService;
import com.kalimeradev.mipymee.client.service.ProfileServiceAsync;

public class FacturasView extends FlowPanel {
	private ProfileInfo profileInfo;


	@UiField(provided = true)
	DataGrid<ContactInfo> dataGrid;

	private final ProfileServiceAsync profileService = GWT.create(ProfileService.class);

	public FacturasView(ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;

		init(null);

	}

	private void init(final FlexTable layout) {

		// Create a DataGrid.

		// Set a key provider that provides a unique key for each contact. If key is
		// used to identify contacts when fields (such as the name and address)
		// change.
		dataGrid = new DataGrid<ContactInfo>(ContactDatabase.ContactInfo.KEY_PROVIDER);
		dataGrid.setWidth("800px");
		dataGrid.setHeight("250px");
		// Set the message to display when the table is empty.
		dataGrid.setEmptyTableWidget(new Label("DataGridEmpty"));

		// Attach a column sort handler to the ListDataProvider to sort the list.
		ListHandler<ContactInfo> sortHandler = new ListHandler<ContactInfo>(ContactDatabase.get().getDataProvider().getList());
		dataGrid.addColumnSortHandler(sortHandler);


		// Add a selection model so we can select cells.
		final SelectionModel<ContactInfo> selectionModel = new MultiSelectionModel<ContactInfo>(ContactDatabase.ContactInfo.KEY_PROVIDER);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<ContactInfo> createCheckboxManager());

		// Initialize the columns.
		initTableColumns(selectionModel, sortHandler);

		// Add the CellList to the adapter in the database.
		ContactDatabase.get().addDataDisplay(dataGrid);

		DecoratorPanel decorator= new DecoratorPanel ();
		decorator.add(dataGrid);
		add(decorator);
	}

	private void initTableColumns(final SelectionModel<ContactInfo> selectionModel, ListHandler<ContactInfo> sortHandler) {
		// Checkbox column. This table will uses a checkbox column for selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
		// mouse selection.
		Column<ContactInfo, Boolean> checkColumn = new Column<ContactInfo, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(ContactInfo object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		// First name.
		Column<ContactInfo, String> firstNameColumn = new Column<ContactInfo, String>(new EditTextCell()) {
			@Override
			public String getValue(ContactInfo object) {
				return object.getFirstName();
			}
		};
		firstNameColumn.setSortable(true);
		sortHandler.setComparator(firstNameColumn, new Comparator<ContactInfo>() {
			public int compare(ContactInfo o1, ContactInfo o2) {
				return o1.getFirstName().compareTo(o2.getFirstName());
			}
		});
		dataGrid.addColumn(firstNameColumn, "ColumnFirstName");
		firstNameColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
			public void update(int index, ContactInfo object, String value) {
				// Called when the user changes the value.
				object.setFirstName(value);
				ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(firstNameColumn, 20, Unit.PCT);

		// Last name.
		Column<ContactInfo, String> lastNameColumn = new Column<ContactInfo, String>(new EditTextCell()) {
			@Override
			public String getValue(ContactInfo object) {
				return object.getLastName();
			}
		};
		lastNameColumn.setSortable(true);
		sortHandler.setComparator(lastNameColumn, new Comparator<ContactInfo>() {
			public int compare(ContactInfo o1, ContactInfo o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		dataGrid.addColumn(lastNameColumn, "ColumnLastName");
		lastNameColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
			public void update(int index, ContactInfo object, String value) {
				// Called when the user changes the value.
				object.setLastName(value);
				ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(lastNameColumn, 20, Unit.PCT);

		// Age.
		Column<ContactInfo, Number> ageColumn = new Column<ContactInfo, Number>(new NumberCell()) {
			@Override
			public Number getValue(ContactInfo object) {
				return object.getAge();
			}
		};
		lastNameColumn.setSortable(true);
		sortHandler.setComparator(ageColumn, new Comparator<ContactInfo>() {
			public int compare(ContactInfo o1, ContactInfo o2) {
				return o1.getBirthday().compareTo(o2.getBirthday());
			}
		});
		Header<String> ageFooter = new Header<String>(new TextCell()) {
			@Override
			public String getValue() {
				List<ContactInfo> items = dataGrid.getVisibleItems();
				if (items.size() == 0) {
					return "";
				} else {
					int totalAge = 0;
					for (ContactInfo item : items) {
						totalAge += item.getAge();
					}
					return "Avg: " + totalAge / items.size();
				}
			}
		};
		dataGrid.addColumn(ageColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("ColumnAge")), ageFooter);
		dataGrid.setColumnWidth(ageColumn, 7, Unit.EM);

		// Category.
		final Category[] categories = ContactDatabase.get().queryCategories();
		List<String> categoryNames = new ArrayList<String>();
		for (Category category : categories) {
			categoryNames.add(category.getDisplayName());
		}
		SelectionCell categoryCell = new SelectionCell(categoryNames);
		Column<ContactInfo, String> categoryColumn = new Column<ContactInfo, String>(categoryCell) {
			@Override
			public String getValue(ContactInfo object) {
				return object.getCategory().getDisplayName();
			}
		};
		dataGrid.addColumn(categoryColumn, "ColumnCategory");
		categoryColumn.setFieldUpdater(new FieldUpdater<ContactInfo, String>() {
			public void update(int index, ContactInfo object, String value) {
				for (Category category : categories) {
					if (category.getDisplayName().equals(value)) {
						object.setCategory(category);
					}
				}
				ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(categoryColumn, 130, Unit.PX);

		// Address.
		Column<ContactInfo, String> addressColumn = new Column<ContactInfo, String>(new TextCell()) {
			@Override
			public String getValue(ContactInfo object) {
				return object.getAddress();
			}
		};
		addressColumn.setSortable(true);
		sortHandler.setComparator(addressColumn, new Comparator<ContactInfo>() {
			public int compare(ContactInfo o1, ContactInfo o2) {
				return o1.getAddress().compareTo(o2.getAddress());
			}
		});
		dataGrid.addColumn(addressColumn, "ColumnAddress");
		dataGrid.setColumnWidth(addressColumn, 60, Unit.PCT);
	}
}
