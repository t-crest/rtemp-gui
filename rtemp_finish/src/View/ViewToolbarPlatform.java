package View;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Model.Model;
import Model.linkConfiguration;

public class ViewToolbarPlatform {
	private View view;
	private Model model;
	private ToolBar toolBar;
	public ToolItem itemNew, itemSettings,itemMake,itemSim,itemSynth,itemAddCpu,itemRemoveCpu,itemShowSpm,itemAddLink,itemRemoveLink,itemCopyLink,itemLinkType, itemClearLinks, itemAddAllToLinks, itemAddAllFromLinks, itemAddAllFromToLinks;
	public MenuItem itemBitorus, itemCustom, itemMesh;
	private List<ToolItem> toolItems = new ArrayList<ToolItem>();

	private Menu dropdown;

	public ViewToolbarPlatform(Model model, final View view) {
		this.view = view;
		this.model = model;

		toolBar = new ToolBar (view.getShell(), SWT.FLAT);

		itemNew = addItem("resources/new_platform.png","New",SWT.PUSH);
		itemSettings = addItem("resources/platform_settings.png","Settings",SWT.PUSH);
		addSeperator();
		itemMake = addItem("resources/platform_make.png","Make",SWT.PUSH);
		itemSim = addItem("resources/platform_sim.png","Sim",SWT.PUSH);
		itemSynth = addItem("resources/platform_synth.png","Synth",SWT.PUSH);
		addSeperator();
		itemAddCpu = addItem("resources/add_cpu.png","Add Node",SWT.CHECK);
		itemRemoveCpu = addItem("resources/remove_cpu.png","Remove Node",SWT.CHECK);
		itemShowSpm = addItem("resources/node_spm.png","Show SPMSize",SWT.CHECK);
		addSeperator();
		itemAddLink = addItem("resources/link_add.png","Add Link",SWT.CHECK);
		itemRemoveLink = addItem("resources/link_delete.png","Remove Link",SWT.CHECK);
		itemClearLinks = addItem("resources/links_clear.png","Clear All Links",SWT.PUSH);
		itemLinkType = addItem("resources/link_edit.png","Link Type",SWT.DROP_DOWN);
		itemCopyLink = addItem("resources/link_copy.png","Copy Links",SWT.PUSH);
		addSeperator();
		itemAddAllFromLinks = addItem("resources/links_from.png","Add Links From Node",SWT.CHECK);
		itemAddAllToLinks = addItem("resources/links_to.png","Add Links To Node",SWT.CHECK);
		itemAddAllFromToLinks = addItem("resources/links_tofrom.png","Add Links To And From Node",SWT.CHECK);
		
		


		dropdown = new Menu(view.getShell(), SWT.POP_UP);;
		view.getShell().setMenu(dropdown);

		itemCustom = new MenuItem(dropdown, SWT.RADIO);
		itemCustom.setText("Custom");
		itemBitorus = new MenuItem(dropdown, SWT.RADIO);
		itemBitorus.setText("Bitorus");
		itemMesh = new MenuItem(dropdown, SWT.RADIO);
		itemMesh.setText("Mesh");
	}

	private void addSeperator() {
		new ToolItem(toolBar,SWT.SEPARATOR);
	}

	private ToolItem addItem(String image, String string, int type) {
		ToolItem item = new ToolItem (toolBar, type);
		item.setImage(new Image(null,image));
		item.setToolTipText(string);
		this.toolItems.add(item);
		return item;
	}

	public ToolBar getToolbar() {
		return this.toolBar;
	}

	public Menu getDropdownMenu() {
		return this.dropdown;
	}



	public void setDropdownMenu(linkConfiguration s) {
		if(s == null) {
			return;
		}
		
		// Deselect all
		for(MenuItem i : dropdown.getItems()) {
			i.setSelection(false);
		}
		
		// Select currect configuration
		switch(s) {
		case custom:
			itemCustom.setSelection(true);
			break;
		case mesh:
			itemMesh.setSelection(true);
			break;
		case bitorus:
			itemBitorus.setSelection(true);
			break;
		default:
			break;
		}
	}

}
