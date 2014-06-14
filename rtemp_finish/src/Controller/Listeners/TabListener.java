package Controller.Listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import Model.Model;
import Model.PlatformObject;
import Model.IPCores.IPCore;
import View.View;

public class TabListener implements SelectionListener {
	private View view;
	private Model model;
	
	public TabListener(Model model, View view) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if(!view.viewCpu.adding)
			view.update();
	}

}
