package Controller.Listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MenuItem;

import Controller.Controller;
import Model.Model;
import Model.Static.OSFinder;
import Model.Static.Settings;
import View.View;

public class MenuListener implements SelectionListener {
	private View view;
	private Model model;
	private Controller controller;

	public MenuListener(Model model, View view, Controller controller) {
		this.view = view;
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		MenuItem item = (MenuItem) event.widget;
		
		if(item.equals(view.viewMenu.openItem)) {
			view.openFile();
		}
		
		if(item.equals(view.viewMenu.saveItem)) {
			view.saveFile();
		}
		
		if(item.equals(view.viewMenu.switchItem)) {
			view.switchWorkspace();
		}
		
		if(item.equals(view.viewMenu.newPlatform)) {
			view.createNewPlatform();
		}
		
		if(item.equals(view.viewMenu.removeNode)) {
			this.controller.removeNode();
		}
		
		if(item.equals(view.viewMenu.addNode)) {
			this.controller.addNode();
		}
		
		if(item.equals(view.viewMenu.itemDeselect)) {
			view.clearToolSelection(null);
		}
		
		view.update();
	}
}