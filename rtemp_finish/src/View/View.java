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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;

import Controller.Listeners.CanvasMouseListener;
import Controller.Listeners.CanvasPaintListener;
import Dialogs.TextInputDialog;
import Model.Model;
import Model.PlatformObject;
import Model.TopologyTypes;
import Model.Aegean.Aegean;
import Model.Aegean.Include;
import Model.IPCores.IPCore;
import Static.OSFinder;
import Static.Settings;


public class View {
	private Model model;

	private Display display;
	private Shell shell;

	public ViewDetails viewDetails;
	public ViewIp viewCpu;
	public ViewCanvas viewCanvas;
	public ViewToolbarPlatform viewToolbarPlatform;
	public ViewToolbarIp viewToolbarCpu;
	public ViewToolbarDetails viewToolbarDetails;
	public ViewMenu viewMenu;

	private PlatformObject selectedDetails;

	/* ********************* CONSTRUCTOR ********************* */

	public View (Model model) throws Exception {
		this.model = model;

		//Main settings
		display = Display.getDefault();
		setShell(new Shell (display));
		getShell().setText("Graphical Configuration for RTEMP");
		getShell().setMinimumSize(400, 100);
		getShell().setLayout(new GridLayout(3,false));

		//Center the in the middel of the screen
		Rectangle bounds = getShell().getDisplay().getPrimaryMonitor().getBounds();
		Rectangle rect = getShell().getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		getShell().setLocation(x, y);
		//getShell().setMaximized(true);

		//Icon 
		
		Image small = new Image(display,ResourceLoader.load("icon_512.png"));
		getShell().setImage(small);

		//Menu
		viewMenu = new ViewMenu(model, this);

		//Label components
		new ViewLabel(this, "IP", SWT.CENTER);
		new ViewLabel(this, "Platform", SWT.CENTER);
		new ViewLabel(this, "Details", SWT.CENTER);

		viewToolbarCpu = new ViewToolbarIp(model,this);

		//creating toolbar and toolitems
		viewToolbarPlatform = new ViewToolbarPlatform(model,this);

		//Label Details
		viewToolbarDetails = new ViewToolbarDetails(model,this);

		// Column 1 - CPUs
		viewCpu = new ViewIp(model, this);

		// Column 2 - Board
		viewCanvas = new ViewCanvas(model, this);

		// Column 3 - Details
		viewDetails = new ViewDetails(model,this);

		update();
	}

	/* ************************** LISTENERS ************************** */

	public void addToolbarListener(SelectionListener listener) {
		for(ToolItem i : viewToolbarPlatform.getToolbar().getItems())
			i.addSelectionListener(listener);
		for(ToolItem i : viewToolbarCpu.getToolbar().getItems())
			i.addSelectionListener(listener);
		for(ToolItem i : viewToolbarDetails.getToolbar().getItems())
			i.addSelectionListener(listener);
	}

	public void addDropdownMenuListener(SelectionListener listener) {
		for(MenuItem i : viewToolbarPlatform.getDropdownMenu().getItems()) {
			i.addSelectionListener(listener);
		}
	}

	public void addDetailsListener(SelectionListener listener) {
		viewDetails.getTree().addSelectionListener(listener);
	}

	public void addMenuListener(SelectionListener listener) {
		for(MenuItem menuItem : viewMenu.getItems()) {
			menuItem.addSelectionListener(listener);
		}
	}

	public void addCanvasListener(CanvasMouseListener listener) {
		this.viewCanvas.getCanvas().addMouseListener(listener);
	}

	public void addCanvasPaintListener(CanvasPaintListener listener) {
		this.viewCanvas.getCanvas().addPaintListener(listener);
	}

	public void addTabListener(SelectionListener listener) {
		viewCpu.getTabs().addSelectionListener(listener);
	}

	/* ************************ DIALOG BOXES ************************* */
	
	public void dialogAboutBox() {
		MessageBox box = new MessageBox(getShell(), SWT.OK);
		box.setText("About");
		box.setMessage("This tool is the product of a software technology project by Peter Gelsbo and Andreas Nordmand Andersen.\n\nThe Icons used are made by Mark James under the Creative Commons Attribution 2.5 License.\nhttp://www.famfamfam.com/lab/icons/silk/");
		box.open();
	}
	public int dialogWarningReplaceCustom() {
		MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
		box.setText("Warning");
		box.setMessage("Are you sure?\nThis action will replace the currently custom link settings.");
		return box.open();
	}
	
	public int dialogWarningIncludeNotAllowed() {
		MessageBox box = new MessageBox(getShell(), SWT.OK);
		box.setText("Warning");
		box.setMessage("You are not allowed to include the same file more than once.");
		return box.open();
		
	}

	public int dialogWarningSure() {
		MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
		box.setText("Warning");
		box.setMessage("Are you sure?\nThis action can not be undone.");
		return box.open();
	}

	public int dialogWarningDimension() {
		MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
		box.setText("Warning");
		box.setMessage("Are you sure?\nAll links and nodes that are affected by this change might get destroyed.");
		return box.open();
	}

	public int dialogWarningSaveIp() {
		MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
		box.setText("Warning");
		box.setMessage("Are you sure?\nAll changes in the currently included IP files will get saved.");
		return box.open();
	}
	
	public int dialogWarningSavePlatform() {
		MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
		box.setText("Warning");
		box.setMessage("Are you sure?\nAll changes in the currently included IP files and platform file will get saved.");
		return box.open();
	}


	public int dialogWarningPlatform() {
		MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
		box.setText("Warning");
		box.setMessage("You will lose you the platform that you have currently open. Are you sure that you want to proceed?");
		return box.open();
	}

	public void dialogErrorFilestructure() {
		MessageBox box = new MessageBox(shell,SWT.OK);
		box.setText("Error");
		box.setMessage("The xml structure was not recognized as a platform");
		box.open();
	}

	public Object[] dialogNewPlatform() {
		TextInputDialog d = new TextInputDialog(getShell(),SWT.OK | SWT.CANCEL);
		d.setText("New Platform");
		d.setMessage("Please insert the height and width of the Platform");
		d.setOptions(SWT.NONE,"Width:", "Height:");
		d.setWidth(250);
		return d.open();
	}

	public Object[] dialogEditSpm() {
		TextInputDialog d = new TextInputDialog(getShell(), SWT.OK | SWT.CANCEL);
		d.setText("Edit SPM size");
		d.setMessage("Please edit SPM size");
		d.setOptions(SWT.NONE, "SPM Size");
		d.setWidth(200);
		return d.open();
	}


	/* *************************** METHODS *************************** */

	public void update() {
		// Repaint canvas
		viewCanvas.repaint();

		// Update IP list
		viewCpu.createButtons();

		// Show settings for selected PlatformObject
		viewDetails.showSettings(selectedDetails);

		// Check current topology type
		TopologyTypes l = model.getTopologyType();
		viewToolbarPlatform.setDropdownMenu(l);

		if(TopologyTypes.custom.equals(l)) {
			this.viewToolbarPlatform.itemAddLink.setEnabled(true);
			this.viewToolbarPlatform.itemRemoveLink.setEnabled(true);
			this.viewToolbarPlatform.itemClearLinks.setEnabled(true);
			this.viewToolbarPlatform.itemAddAllFromLinks.setEnabled(true);
			this.viewToolbarPlatform.itemAddAllFromToLinks.setEnabled(true);
			this.viewToolbarPlatform.itemAddAllToLinks.setEnabled(true);

			this.viewToolbarPlatform.itemCopyLink.setEnabled(false);
		} else {
			this.viewToolbarPlatform.itemAddLink.setEnabled(false);
			this.viewToolbarPlatform.itemRemoveLink.setEnabled(false);
			this.viewToolbarPlatform.itemClearLinks.setEnabled(false);
			this.viewToolbarPlatform.itemAddAllFromLinks.setEnabled(false);
			this.viewToolbarPlatform.itemAddAllFromToLinks.setEnabled(false);
			this.viewToolbarPlatform.itemAddAllToLinks.setEnabled(false);

			this.viewToolbarPlatform.itemCopyLink.setEnabled(true);
		}
		
		//Enables and disables buttons depending on what information there is in the details.
		if(viewDetails.getSelectedObject()!=null){

			if(this.viewDetails.getSelectedObject().getClass().equals(Aegean.class)) {
				this.viewToolbarDetails.itemAddIo.setEnabled(false);
				this.viewToolbarDetails.itemRemoveIo.setEnabled(false);
				this.viewToolbarDetails.itemAddInclude.setEnabled(true);
				this.viewToolbarDetails.itemRemoveInclude.setEnabled(true);
			} 

			if(this.viewDetails.getSelectedObject().getClass().equals(IPCore.class)) {
				this.viewToolbarDetails.itemAddIo.setEnabled(true);
				this.viewToolbarDetails.itemRemoveIo.setEnabled(true);
				this.viewToolbarDetails.itemAddInclude.setEnabled(false);
				this.viewToolbarDetails.itemRemoveInclude.setEnabled(false);
			}
		}


	}

	public void openFile() {
		FileDialog filedialog = new FileDialog(getShell(), SWT.OPEN);
		filedialog.setText("Open file");
		filedialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml"};
		filedialog.setFilterExtensions(filterExt);

		try {
			String readPathWorkspace= Settings.getInstance().getSetting(OSFinder.isWindows() ? "workspaceWindows" :"workspaceMac");
			filedialog.setFilterPath(readPathWorkspace);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		String file = filedialog.open();
		if(!(file==null)){
			if(model.loadPlatform(file)){
				setSelectedDetails(model.getAegean());
				viewCpu.createTabs();
				update();
			} else {
				dialogErrorFilestructure();
			}
		}

	}

	public void saveFile() {
		FileDialog filedialog = new FileDialog(getShell(), SWT.SAVE);
		filedialog.setText("Save " + model.getAegean().getClass().getSimpleName() + " As");
		filedialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml"};
		filedialog.setFilterExtensions(filterExt);

		try {
			String readPathWorkspace= Settings.getInstance().getSetting(OSFinder.isWindows() ? "workspaceWindows" :"workspaceMac");
			filedialog.setFilterPath(readPathWorkspace);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		String file= filedialog.open();
		

		if(!(file==null)){
			model.savePlatform(file);
		}
	}

	public void switchWorkspace() {
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setText("Switch Workspace");
		dialog.setFilterPath("C:/");

		try {
			String readPathWorkspace= Settings.getInstance().getSetting(OSFinder.isWindows() ? "workspaceWindows" :"workspaceMac");
			dialog.setFilterPath(readPathWorkspace);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		String file = dialog.open();

		if(!(file==null))
		{
			try {
				String workspacePath =  OSFinder.isWindows() ? "workspaceWindows" : "workspaceMac";
				Settings.getInstance().setSetting(workspacePath, file).save();
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
		}
	}

	/* ********************* GETTERS AND SETTERS ********************* */

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public Display getDisplay() {
		return display;
	}

	public void createNewPlatform() {
		Object[] result = dialogNewPlatform();
		
		int type = (Integer) result[0];
		String[] values = (String[]) result[1];
		
		int h = 0;
		int w = 0;

		// Make sure that the input values are integers
		try {
			h = Integer.valueOf((String) values[0]);
			w = Integer.valueOf((String) values[1]);
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		}

		if(type == SWT.OK) {

			boolean ok = dialogWarningPlatform() == SWT.OK ? true : false; 

			if (ok) {
				Aegean aegean = new Aegean();
				aegean.getPlatform().setWidth(String.valueOf(h));
				aegean.getPlatform().setHeight(String.valueOf(w));

				aegean.getPlatform().getTopology().setTopoType(TopologyTypes.custom);

				Include i1 = new Include();
				i1.setHref("./ip/ip.xml");

				Include i2 = new Include();
				i2.setHref("./io/dev.xml");

				Include i3 = new Include();
				i3.setHref("./boards/de2-115.xml");

				aegean.getPlatform().getInclude().add(i1);
				aegean.getPlatform().getInclude().add(i2);
				aegean.getPlatform().getInclude().add(i3);

				model.setAegean(aegean);
				setSelectedDetails(model.getAegean());

				model.loadIPCores();
				viewCpu.createTabs();
				update();

			} else {
			}
		}
	}

	public void setSelectedDetails(PlatformObject o) {
		this.selectedDetails = o;
	}

	public void clearToolSelection(ToolItem exception) {
		model.getPointListAddLink().clear();
		model.getPointListRemoveLink().clear();
		for(ToolItem i : viewToolbarPlatform.getToolbar().getItems()) {
			if(!i.equals(exception))
				i.setSelection(false);
		}
	}

}
