/*
    Copyright 2014 Peter Gelsbo and Andreas Nordmand Andersen

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package View;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import Model.Model;


public class ViewMenu {
	private Menu menu;
	@SuppressWarnings("unused")
	private View view;
	@SuppressWarnings("unused")
	private Model model;
	
	private List<MenuItem> items = new ArrayList<MenuItem>();
	public MenuItem openItem, saveItem, switchItem, removeNode, addNode, newPlatform, itemDeselect,itemAbout;

	public ViewMenu(final Model model, final View view) throws Exception {
		this.view=view;
		this.model = model;

		//Create menu
		this.menu = new Menu (view.getShell(), SWT.BAR);
		view.getShell().setMenuBar (menu);

		//Create menu items
		MenuItem fileItem = new MenuItem (menu, SWT.CASCADE);
		fileItem.setText ("&File");
		
		//Create submenu
		Menu submenu = new Menu (view.getShell(), SWT.DROP_DOWN);
		fileItem.setMenu(submenu);
		
		openItem = addItem(submenu, "Open &File\tCtrl+O", SWT.MOD1 + 'O');
		saveItem = addItem(submenu,"Save &As\tCtrl+S",SWT.MOD1 + 'S');
		switchItem = addItem(submenu,"Switch &Workspace\tCtrl+W",SWT.MOD1 + 'W');


		fileItem = new MenuItem (menu, SWT.CASCADE);
		fileItem.setText ("&Edit");
		submenu = new Menu (view.getShell(), SWT.DROP_DOWN);
		fileItem.setMenu (submenu);
		
		newPlatform = addItem(submenu,"New &platform\tCtrl+N",SWT.MOD1 + 'N');
		addSeperator(submenu);
		removeNode = addItem(submenu,"Remove &item\tDelete",SWT.DEL);
		addNode = addItem(submenu,"Add &item\t+",'+');
		addSeperator(submenu);
		itemDeselect = addItem(submenu,"Deselect &tools\tEsc",SWT.ESC);
		addSeperator(submenu);
		itemAbout = addItem(submenu,"About",SWT.NONE);
	}

	private MenuItem addItem(Menu submenu, String itemText, int acc) {
		MenuItem item = new MenuItem (submenu, SWT.PUSH);
		item.setText (itemText);
		item.setAccelerator (acc);
		this.items.add(item);
		return item;
	}
	
	public Menu getMenu()
	{
		return menu;
	}

	public List<MenuItem> getItems() {
		return this.items;
	}
	
	private void addSeperator(Menu parent) {
		new MenuItem(parent,SWT.SEPARATOR);
	}

}
