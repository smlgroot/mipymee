package com.kalimeradev.mipymee.client.views;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.kalimeradev.mipymee.client.model.ProfileInfo;

public class TopMenuView extends FlowPanel {

	public TopMenuView(final ProfileInfo profileInfo) {
		setHeight("50px");
		// Create a command that will execute on menu item selection
		Command menuCommand = new Command() {
			private int curPhrase = 0;
			private final String[] phrases = new String[] {
			"a", "b", "c"
			};

			public void execute() {
				boolean result = Window.confirm("Salir?");

				if (result) {
					Window.Location.replace(profileInfo.getLogoutUrl());
				}
				/*
				 * Window.alert(phrases[curPhrase]); curPhrase = (curPhrase + 1) % phrases.length;
				 */
			}
		};

		// Create a menu bar
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		// menu.setWidth("500px");
		menu.setAnimationEnabled(true);

		// Create a sub menu of recent documents
		MenuBar nuevoSubMenu = new MenuBar(true);
		nuevoSubMenu.addItem("Factura", menuCommand);
		nuevoSubMenu.addItem("Recibo", menuCommand);
		nuevoSubMenu.addItem("Contador", menuCommand);

		// Create the file menu
		MenuBar fileMenu = new MenuBar(true);
		fileMenu.setAnimationEnabled(true);
		fileMenu.addItem("Profile",new  ProfileCommand(this));
		menu.addItem(new MenuItem("Archivo", fileMenu));
		fileMenu.addItem("Nuevo", nuevoSubMenu);
		fileMenu.addSeparator();
		fileMenu.addItem("Salir", menuCommand);

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
		// ensureDebugId("cwMenuBar");
		add(menu);
	}

	class ProfileCommand implements Command {

		public ProfileCommand(TopMenuView topMenuView) {
			Widget asasd = topMenuView.getParent();
			System.out.println(asasd);
		}

		public void execute() {
			// TODO Auto-generated method stub

		}
	}
}
