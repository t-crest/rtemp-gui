package Controller.Listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import Model.Model;
import Model.PlatformObject;
import Model.IPCores.IPCore;
import View.View;

public class IpListener implements SelectionListener {
	private View view;
	private Model model;
	
	public IpListener(Model model, View view) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	@Override
	public void widgetSelected(SelectionEvent event) {
		Button b = (Button) event.widget;
		if(b.getSelection()) {
			view.setSelectedDetails((IPCore) b.getData("ipcore"));
			view.update();
		}
	}

}
