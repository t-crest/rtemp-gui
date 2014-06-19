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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import Controller.Listeners.IpListener;
import Model.Model;
import Model.IPCores.IPCore;
import Model.IPCores.IPCores;


public class ViewIp {
	private View view;
	private Model model;
	private List<Button> buttons = new ArrayList<Button>();
	private TabFolder tabs;
	public boolean adding;

	/* ********************* CONSTRUCTOR ********************* */

	public ViewIp(Model model, View view) {
		this.view = view;
		this.model = model;
		
		tabs = new TabFolder(view.getShell(),SWT.NONE);
		GridData gd = new GridData(SWT.FILL,SWT.FILL,false,true);
		gd.widthHint = 200;
		tabs.setLayoutData(gd);

		createTabs();
	}

	public void createButtons() {
		TabItem[] items = tabs.getSelection();
		
		if(items.length == 0) {
			return;
		}
		TabItem item = items[0];
		Composite c = (Composite) item.getData("composite");
		IPCores ipcores = (IPCores) item.getData("ipcores");
		
		
		//pause composite redraw
		c.setRedraw(false);
		
		
		for(int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			// Get IPCore from button
			IPCore buttonIp = (IPCore) b.getData("ipcore");
			
			// Check if IPCore has been deleted
			if(!ipcores.getIpCores().contains(buttonIp)) {
				buttons.remove(b);
				b.dispose();
			}
			// Refresh text on all buttons
			else {
				b.setText(buttonIp.getIpType());
			}
		}

		//Making buttons using the CPUs from the IPcore list
		for(final IPCore ip : ipcores.getIpCores()) {
			// Only create new button if button.ipcore does not already exist
			boolean found = false;
			for(Button b : buttons) {
				IPCore buttonIp = (IPCore) b.getData("ipcore");
				
				if(buttonIp.equals(ip)) {
					found = true;
					break;
				}
			}

			if(!found) {
				//Make button
				final Button button = new Button(c, SWT.RADIO);
				button.setText(ip.getIpType());
				
				// Configure height and width of buttons
				GridData gridData = new GridData(SWT.FILL,SWT.FILL,true,false);
				gridData.minimumHeight = 50;
				gridData.minimumWidth = 150;
				gridData.heightHint = 50;
				
				button.setLayoutData(gridData);
				button.setAlignment(SWT.CENTER);
				button.setData("ipcore",ip);

				// Set button hovertext / tooltip
				String tooltip = "";
				// IPCore might not have defined patmos
				try {
					tooltip = ip.getPatmos().getDescription();
				} catch (Exception e1) {
					// e1.printStackTrace();
				}
				button.setToolTipText(tooltip);
				buttons.add(button);

				//Adding listener
				button.addSelectionListener(new IpListener(model, view));
			}

		}

		//Drawing the buttons
		c.setRedraw(true);
		c.layout();
		c.redraw();
		((ScrolledComposite) c.getParent()).setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public List<Button> getButtons() {
		return this.buttons;
	}

	public IPCore getSelectedIpcore() {
		// Find selected ip core
		for(Button b : getButtons()) {
			if(b.getSelection()) {
				return (IPCore) b.getData("ipcore");
			}
		}

		// return null if not found
		return null;
	}

	public void setSelectedIpcore(IPCore newIpcore) {
		for(Button b : buttons) {
			if(b.getData("ipcore").equals(newIpcore)) {
				b.setSelection(true);
				view.viewDetails.showSettings(newIpcore);
			}
		}
	}
	
	public TabFolder getTabs() {
		return this.tabs;
	}

	public void createTabs() {
		
		for(TabItem i : tabs.getItems()) {
			i.dispose();
		}
		
		adding = true;
		
		for(IPCores ips : model.getIpcores()) {
			TabItem item = new TabItem (this.tabs,SWT.NONE);
			
			String filename = ips.getFileName();
			item.setText(filename);
			item.setData("ipcores",ips);
			
			ScrolledComposite sc = new ScrolledComposite(tabs,SWT.V_SCROLL);
			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);
			item.setControl(sc);
			
			Composite c = new Composite(sc,SWT.NONE);
			item.setData("composite",c);
			sc.setContent(c);
			
			GridLayout rl = new GridLayout(1,true);
			c.setLayout(rl);
			
			createButtons();
		}
		
		adding = false;		
	}
}
