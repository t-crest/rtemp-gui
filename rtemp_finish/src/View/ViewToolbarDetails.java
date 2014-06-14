package View;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Model.Model;

public class ViewToolbarDetails {
	private View view;
	private Model model;
	private ToolBar toolBar;
	public ToolItem itemAddIo, itemRemoveIo,itemAddInclude, itemRemoveInclude;

	private List<ToolItem> toolItems = new ArrayList<ToolItem>();

	public ViewToolbarDetails(Model model, View view) {
		this.view = view;
		this.model = model;

		toolBar = new ToolBar (view.getShell(), SWT.FLAT);
		
		itemAddIo = addItem("resources/io_add.png","Add IO");
		itemRemoveIo = addItem("resources/io_delete.png","Remove IO");
		addSeperator();
		itemAddInclude = addItem("resources/include_add.png","Add Include");
		itemRemoveInclude = addItem("resources/include_delete.png","Remove Include");
	}
	
	private void addSeperator() {
		new ToolItem(toolBar,SWT.SEPARATOR);
		
	}
	
	private ToolItem addItem(String image, String string) {
		ToolItem item = new ToolItem (toolBar, SWT.PUSH);
		item.setImage(new Image(null,image));
		item.setToolTipText(string);
		item.setEnabled(false);
		this.toolItems.add(item);
		return item;
	}

	public ToolBar getToolbar() {
		return this.toolBar;
	}

}
