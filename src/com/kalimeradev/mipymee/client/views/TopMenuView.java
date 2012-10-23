package com.kalimeradev.mipymee.client.views;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class TopMenuView extends FlowPanel {

	public TopMenuView() {
		setHeight("50px");
		// Create a command that will execute on menu item selection
		Command menuCommand = new Command() {
			private int curPhrase = 0;
			private final String[] phrases = new String[] {
			"a", "b", "c"
			};

			public void execute() {
				Window.alert(phrases[curPhrase]);
				curPhrase = (curPhrase + 1) % phrases.length;
			}
		};

		// Create a menu bar
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		//menu.setWidth("500px");
		menu.setAnimationEnabled(true);

		// Create a sub menu of recent documents
		MenuBar recentDocsMenu = new MenuBar(true);
		String[] recentDocs = new String[] {
		"a", "b", "c", "d"
		};
		for (int i = 0; i < recentDocs.length; i++) {
			recentDocsMenu.addItem(recentDocs[i], menuCommand);
		}

		// Create the file menu
		MenuBar fileMenu = new MenuBar(true);
		fileMenu.setAnimationEnabled(true);
		menu.addItem(new MenuItem("FileCategory", fileMenu));
		String[] fileOptions = new String[] {
		"f1", "f2", "f3", "f4", "f5"
		};
		for (int i = 0; i < fileOptions.length; i++) {
			if (i == 3) {
				fileMenu.addSeparator();
				fileMenu.addItem(fileOptions[i], recentDocsMenu);
				fileMenu.addSeparator();
			} else {
				fileMenu.addItem(fileOptions[i], menuCommand);
			}
		}

		// Create the edit menu
		MenuBar editMenu = new MenuBar(true);
		menu.addItem(new MenuItem("EditCategory", editMenu));
		String[] editOptions = new String[] {
		"e1", "e2"
		};
		for (int i = 0; i < editOptions.length; i++) {
			editMenu.addItem(editOptions[i], menuCommand);
		}

		// Create the GWT menu
		MenuBar gwtMenu = new MenuBar(true);
		menu.addItem(new MenuItem("GWT", true, gwtMenu));
		String[] gwtOptions = new String[] {
		"g1", "g2"
		};
		for (int i = 0; i < gwtOptions.length; i++) {
			gwtMenu.addItem(gwtOptions[i], menuCommand);
		}

		// Create the help menu
		MenuBar helpMenu = new MenuBar(true);
		menu.addSeparator();
		menu.addItem(new MenuItem("HelpCategory", helpMenu));
		String[] helpOptions = new String[] {
		"h1", "h2"
		};
		for (int i = 0; i < helpOptions.length; i++) {
			helpMenu.addItem(helpOptions[i], menuCommand);
		}

		// Return the menu
		//ensureDebugId("cwMenuBar");
		add(menu);
	}

}
