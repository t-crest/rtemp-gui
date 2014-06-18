package Controller.Listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Controller.Controller;
import Dialogs.DoubleInputDialog;
import Model.Model;
import Model.TopologyTypes;
import Model.Aegean.Aegean;
import Model.Aegean.Include;
import Model.IPCores.IO;
import Model.IPCores.IPCore;
import View.View;

public class ToolbarListener implements SelectionListener {
	private View view;
	private Model model;
	private Controller controller;

	public ToolbarListener(Model model, View view, Controller controller) {
		this.view = view;
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	@Override
	public void widgetSelected(SelectionEvent event) {
		ToolItem item = (ToolItem) event.widget;

		// Deselect all items
		view.clearToolSelection(item);

		// Copy IP
		if(item.equals(this.view.viewToolbarCpu.itemCopy)) {
			IPCore selected = view.viewCpu.getSelectedIpcore();
			IPCore newIpcore = null;
			if(selected != null) {
				try {
					newIpcore = model.cloneIpcore(selected);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				if(newIpcore != null) {
					view.viewCpu.setSelectedIpcore(newIpcore);
				}
			}
		}

		// Delete IP
		if(item.equals(this.view.viewToolbarCpu.itemDelete)) {
			IPCore selected = view.viewCpu.getSelectedIpcore();

			if(selected != null) {
				boolean ok = view.dialogWarningSure() == SWT.OK ? true : false;


				if(ok) {
					model.removeIpcore(selected);
					view.setSelectedDetails(null);
				}
			}
		}

		// New platform
		if(item.equals(this.view.viewToolbarPlatform.itemNew)) {
			view.createNewPlatform();
		}

		//Platform settings
		if(item.equals(this.view.viewToolbarPlatform.itemSettings)) {
			view.setSelectedDetails(model.getAegean());
		}

		//Platform "Make"
		if(item.equals(this.view.viewToolbarPlatform.itemMake)) {
			if(view.dialogWarningSavePlatform()==SWT.OK){
				model.saveCurrentPlatform();
				this.controller.runCommand("platform");
			}
		}

		//Platform "Sim"
		if(item.equals(this.view.viewToolbarPlatform.itemSim)) {
			this.controller.runCommand("sim");
		}

		//Platform "Synth"
		if(item.equals(this.view.viewToolbarPlatform.itemSynth)) {
			this.controller.runCommand("synth");
		}

		// Remove node
		if(item.equals(this.view.viewToolbarPlatform.itemRemoveCpu)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}

		// Add node
		if(item.equals(this.view.viewToolbarPlatform.itemAddCpu)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}

		// Show SPM
		if(item.equals(this.view.viewToolbarPlatform.itemShowSpm)) {
			// Nothing here
		}

		// Add link
		if(item.equals(this.view.viewToolbarPlatform.itemAddLink)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}

		// Remove link
		if(item.equals(this.view.viewToolbarPlatform.itemRemoveLink)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}

		// Link type
		if(item.equals(this.view.viewToolbarPlatform.itemLinkType)) {
			int x = item.getBounds().x;
			int y = item.getBounds().height;
			ToolBar toolBar = item.getParent();
			Point point = toolBar.toDisplay(new Point(x, y));
			view.viewToolbarPlatform.getDropdownMenu().setLocation(point.x, point.y);
			view.viewToolbarPlatform.getDropdownMenu().setVisible(true);
		}

		// Copy link
		if(item.equals(this.view.viewToolbarPlatform.itemCopyLink)) {
			if(view.dialogWarningReplaceCustom()==SWT.OK)
			{
				this.model.copyTopologyTypes();
			}
		}

		//Clear links
		if(item.equals(this.view.viewToolbarPlatform.itemClearLinks)) {
			if(view.dialogWarningSure() == SWT.OK) {
				model.getAegean().getPlatform().getTopology().getLinks().clear();
			}
		}

		//Add all links itemAddAllToLinks
		if(item.equals(this.view.viewToolbarPlatform.itemAddAllToLinks)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}

		//Add all links itemAddAllFromLinks 
		if(item.equals(this.view.viewToolbarPlatform.itemAddAllFromLinks)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}

		//Add all links itemAddAllFromToLinks
		if(item.equals(this.view.viewToolbarPlatform.itemAddAllFromToLinks)) {
			// Clear selected rectangle
			view.viewCanvas.setSelectedRectangle(null);
		}


		// Add IO
		if(item.equals(this.view.viewToolbarDetails.itemAddIo)) {
			IPCore selectedIp = view.viewCpu.getSelectedIpcore();
			if(selectedIp!= null) {
				if(selectedIp.getPatmos()!=null)
				{
					selectedIp.getPatmos().getIos().add(new IO());
				}
			}
		}

		// Remove IO
		if(item.equals(this.view.viewToolbarDetails.itemRemoveIo)) {
			IPCore selectedIp = view.viewCpu.getSelectedIpcore();
			IO selectedIo = view.viewDetails.getSelectedIo();

			if(selectedIo != null && selectedIp != null) {
				selectedIp.getPatmos().getIos().remove(selectedIo);
			}
		}

		// Add Include
		if(item.equals(this.view.viewToolbarDetails.itemAddInclude)) {
			Aegean aegean = model.getAegean();
			if(aegean != null) {
				aegean.getPlatform().getInclude().add(new Include());
			}
		}

		// Remove Include
		if(item.equals(this.view.viewToolbarDetails.itemRemoveInclude)) {
			Include selectedInclude = view.viewDetails.getSelectedInclude();


			if(selectedInclude != null) {
				model.getAegean().getPlatform().getInclude().remove(selectedInclude);
				model.loadIPCores();
				view.viewCpu.createTabs();
			}
		}

		// Always update view
		view.update();
	}
}