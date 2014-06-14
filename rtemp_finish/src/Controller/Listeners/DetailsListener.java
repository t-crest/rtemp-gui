package Controller.Listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import Model.Model;
import Model.PlatformObject;
import Model.TreeObject;
import Model.Aegean.Include;
import Model.Aegean.Link;
import Model.Aegean.Node;
import Model.Aegean.Platform;
import Model.IPCores.IPCore;
import Model.IPCores.IPCores;
import Model.Static.XmlSerializer;
import View.View;

public class DetailsListener implements SelectionListener {
	private View view;
	private Model model;
	private Tree tree;
	private TreeItem[] lastItem = new TreeItem[1];
	private TreeEditor editor;

	public DetailsListener(Model model, View view) {
		this.view = view;
		this.model = model;
		this.tree = view.viewDetails.getTree();

		editor = new TreeEditor(tree);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	@Override
	public void widgetSelected(SelectionEvent event) {
		final TreeItem item = (TreeItem) event.item;

		if(item == null) {
			return;
		}

		// If Link
		TreeObject o = (TreeObject) item.getData("treeObject");
		if(o.platformObject.getClass().equals(Link.class)) {
			view.viewCanvas.selectedLink = (Link) o.platformObject;
			view.viewCanvas.repaint();
		}

		// Do not edit expetions
		boolean b = (Boolean) item.getData("expection");
		if(b) return;

		boolean data = (Boolean) item.getData("header");
		if(data) {
			return;
		}
		//
		if (item != null && item == lastItem [0]) {
			boolean showBorder = true;
			final Composite composite = new Composite (tree, SWT.NONE);
			if (showBorder) composite.setBackground (view.getDisplay().getSystemColor (SWT.COLOR_BLACK));
			final Text text = new Text (composite, SWT.NONE);
			final int inset = showBorder ? 1 : 0;
			composite.addListener (SWT.Resize, new Listener () {
				@Override
				public void handleEvent (Event e) {
					Rectangle rect = composite.getClientArea ();
					text.setBounds (rect.x + inset, rect.y + inset, rect.width - inset * 2, rect.height - inset * 2);
				}
			});
			Listener textListener = new Listener () {
				@Override
				public void handleEvent (final Event e) {
					switch (e.type) {
					case SWT.FocusOut:
						composite.dispose ();
						System.out.println("FocusOut");
						break;
					case SWT.Verify:
						String newText = text.getText ();
						String leftText = newText.substring (0, e.start);
						String rightText = newText.substring (e.end, newText.length ());
						GC gc = new GC (text);
						Point size = gc.textExtent (leftText + e.text + rightText);
						gc.dispose ();
						size = text.computeSize (size.x, SWT.DEFAULT);
						editor.horizontalAlignment = SWT.LEFT;
						Rectangle itemRect = item.getBounds (), rect = tree.getClientArea ();
						editor.minimumWidth = Math.max (size.x, itemRect.width) + inset * 2;
						int left = itemRect.x, right = rect.x + rect.width;
						editor.minimumWidth = Math.min (editor.minimumWidth, right - left);
						editor.minimumHeight = size.y + inset * 2;
						editor.layout ();
						System.out.println("Verify");
						break;
					case SWT.Traverse:
						switch (e.detail) {
						case SWT.TRAVERSE_RETURN:
							changeSetting(item, text.getText());
							//FALL THROUGH
						case SWT.TRAVERSE_ESCAPE:
							composite.dispose ();
							e.doit = false;
						}
						System.out.println("Traverse");
						break;
					}
				}
			};
			text.addListener (SWT.FocusOut, textListener);
			text.addListener (SWT.Traverse, textListener);
			text.addListener (SWT.Verify, textListener);
			editor.setEditor (composite, item,1);
			text.setText (item.getText(1));
			text.selectAll ();
			text.setFocus ();
		}
		lastItem [0] = item;


	}

	private void changeSetting(TreeItem ti, String text) {
		TreeObject o = (TreeObject) ti.getData("treeObject");
		PlatformObject p = o.platformObject;

		//Saving ips before loading the new once if changed from the include
		if(o.platformObject.getClass().equals(Include.class))
		{
			if(view.dialogWarningSaveIp()==SWT.OK){
				if(model.getIpcores()!=null) {
					// If include already exists
					for(Include include : model.getAegean().getPlatform().getInclude())
					{
						if(include.getHref().equals(text)) {
							// Include not allowed. Return.
							view.dialogWarningIncludeNotAllowed();
							return;
						}
					}

					// Save existing ipcores
					model.saveIPCores();

					// Set text in TreeItem
					ti.setText (1,text);
					// Set field
					p.changeField(ti.getText(0),ti.getText(1));
					System.out.println(ti.getText(0) + ","+ti.getText(1));
					// Reload ipcores in model
					model.loadIPCores();
					// Create tabs in view
					view.viewCpu.createTabs();
				}
			} else{
				return;
			}

		} 

		if(o.platformObject.getClass().equals(Platform.class)){
			if(view.dialogWarningDimension()==SWT.OK)
			{

				if(o.attributeName.equals("height"))
				{
					ti.setText (1,text);
					p.changeField(ti.getText(0),ti.getText(1));
					model.destroyLongLinks("height");
				}
				if(o.attributeName.equals("width")){
					ti.setText (1,text);
					p.changeField(ti.getText(0),ti.getText(1));
					model.destroyLongLinks("width");
				}
			} else {
				return;
			}


		}

		//change objects and fields below
		ti.setText (1,text);
		p.changeField(ti.getText(0),ti.getText(1));


		// Change name of nodes, if IPCore name changed
		if(p.getClass().equals(IPCore.class) && o.attributeName.equals("ipType")) {
			for(Node n : model.getNodes()) {
				if(n.getIpTypeRef().equals(o.attributeValue)) {
					n.setIpTypeRef(((IPCore) p).getIpType());
				}
			}
		}

		view.update();
	}

}
