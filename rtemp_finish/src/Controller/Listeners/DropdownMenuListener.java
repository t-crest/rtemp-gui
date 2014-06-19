package Controller.Listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;

import Controller.Controller;
import Model.Model;
import Model.TopologyTypes;
import View.View;

public class DropdownMenuListener implements SelectionListener {
	private View view;
	private Model model;
	private Controller controller;

	public DropdownMenuListener(Model model, View view, Controller controller) {
		this.view = view;
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		MenuItem item = (MenuItem) event.widget;

		// CUSTOM LINKS
		if(item.equals(view.viewToolbarPlatform.itemCustom)) {
			model.getAegean().getPlatform().getTopology().setTopoType(TopologyTypes.custom);
		}
		
		// MESH LINKS
		if(item.equals(view.viewToolbarPlatform.itemMesh)) {
			model.getAegean().getPlatform().getTopology().setTopoType(TopologyTypes.mesh);
		}
		
		// BITORUS LINKS
		if(item.equals(view.viewToolbarPlatform.itemBitorus)) {
			model.getAegean().getPlatform().getTopology().setTopoType(TopologyTypes.bitorus);
		}
		
		view.update();
	}
}