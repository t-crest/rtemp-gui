package View;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Model.Model;

public class ViewToolbarIp {
	private View view;
	private Model model;
	private ToolBar toolBar;
	public ToolItem itemCopy, itemDelete;

	private List<ToolItem> toolItems = new ArrayList<ToolItem>();

	public ViewToolbarIp(Model model, View view) {
		this.view = view;
		this.model = model;

		toolBar = new ToolBar (view.getShell(), SWT.FLAT);
		
		itemCopy = addItem("copy_cpu.png","Copy IP");
		itemDelete = addItem("delete_cpu.png","Delete IP");
	}
	
	private void addSeperator() {
		new ToolItem(toolBar,SWT.SEPARATOR);
		
	}
	
	private ToolItem addItem(String image, String string) {
		ToolItem item = new ToolItem (toolBar, SWT.PUSH);
		item.setImage(new Image(null,ResourceLoader.load(image)));
		item.setToolTipText(string);
		this.toolItems.add(item);
		return item;
	}

	public ToolBar getToolbar() {
		return this.toolBar;
	}

}
