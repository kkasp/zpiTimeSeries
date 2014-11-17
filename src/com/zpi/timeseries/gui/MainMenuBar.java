package com.zpi.timeseries.gui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class MainMenuBar extends MenuBar{
	
	ActionListener _listener;
	private Menu fileMenu;
	
	public MainMenuBar(ActionListener listener) {
		super();
		_listener = listener;
		
		fileMenu = new Menu("Plik");
		add(fileMenu);
		
		MenuItem item;
		fileMenu.add(item = new MenuItem(MainMenuCommands.closeTxt));
		item.addActionListener(_listener);
		
	}

}
