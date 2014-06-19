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
