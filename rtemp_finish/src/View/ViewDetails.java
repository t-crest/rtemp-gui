package View;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import Model.Model;
import Model.PlatformObject;
import Model.TreeObject;
import Model.Aegean.Include;
import Model.Aegean.Link;
import Model.IPCores.IO;


public class ViewDetails {
	private Tree tree;
	private View view;
	@SuppressWarnings("unused")
	private Model model;
	private Font boldFont;
	
	private List<TreeObject> treeObjects;
	private PlatformObject selectedObject;
	private List<Class> classExpections = new ArrayList<Class>();
	private List<String> attributeExceptions = new ArrayList<String>();

	/* ********************* CONSTRUCTOR ********************* */

	public ViewDetails(Model model, View view) {
		this.view = view;
		this.model = model;
		
		/*  
		 * Explanation
		 * classExceptions are used to disable editing of classes in the details view
		 * attributeExceptions are used to remove attributes completely from the details view
		 */
		attributeExceptions.add("source");
		attributeExceptions.add("sink");
		attributeExceptions.add("fileName");
		attributeExceptions.add("absolutePath");

		// Initialize tree
		tree = new Tree(view.getShell(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tree.setHeaderVisible(true);

		//Set width
		GridData gridDataDetails = new GridData(SWT.FILL,SWT.FILL,false,true);
		gridDataDetails.widthHint = 250;
		tree.setLayoutData(gridDataDetails);
		tree.setLinesVisible(true);

		//Add colunms
		TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
		column1.setText("Setting");
		column1.setWidth(100);
		TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
		column2.setText("Value");
		column2.setWidth(150);

		//Create bold font
		Font font = view.getDisplay().getSystemFont();
		FontData fd = font.getFontData()[0];
		boldFont = new Font(view.getDisplay(),fd.getName(),fd.getHeight(),SWT.BOLD);


	}

	public void clearSettings() {
		tree.setRedraw(false);
		tree.removeAll();
		tree.setRedraw(true);
	}

	public void showSettings(PlatformObject selectedObject) {
		
		if(selectedObject == null) {
			clearSettings();
			return;
		}
		this.selectedObject = selectedObject;

		//pause drawing and remove content
		tree.setRedraw(false);
		tree.removeAll();

		//get new object list
		treeObjects = selectedObject.getAttributeList();

		//Create new tree items
		TreeObject previousObj = null;
		TreeItem previousHeader = null;
		for(int i = 0; i < treeObjects.size(); i++) {
			TreeObject treeObj = treeObjects.get(i);
			
			// If Link - only show selected links
			if(treeObj.platformObject.getClass().equals(Link.class) && !view.viewCanvas.selectedLinks.contains(treeObj.platformObject)) {
				continue;
			}

			//Create Headers
			if (previousObj == null || !treeObj.platformObject.equals(previousObj.platformObject)) {
				// Create header
				TreeItem headeritem = createHeader(treeObj);
				previousHeader = headeritem;
				
				// Create first subitem
				createSub(treeObj, previousHeader);
				
				// Prepare for next item
				previousObj = treeObj;
			} 
			//Create subitems
			else {
				createSub(treeObj, previousHeader);
			}
		}

		//Draw Tree
		tree.setRedraw(true);
		tree.redraw();
	}
	
	private TreeItem createHeader(TreeObject treeObj) {
		//Create headeritem
		TreeItem headeritem = new TreeItem(tree,SWT.NONE);
		headeritem.setText(new String[] {treeObj.className,""});
		headeritem.setFont(boldFont);
		headeritem.setData("header", true);
		headeritem.setData("treeObject",treeObj);
		headeritem.setData("expection", true);
		
		
		// If Link
		if(treeObj.platformObject.getClass().equals(Link.class)) {
			Link l = (Link) treeObj.platformObject;
			headeritem.setText(1, l.getSource() + "->" + l.getSink());
		}
		
		
		return headeritem;
	}
	
	private void createSub(TreeObject treeObj, TreeItem previousHeader) {
		// Skip if attribute is in attribute exceptions
		if(attributeExceptions.contains(treeObj.attributeName))
			return;
		
		//Create subitem
		TreeItem subitem = new TreeItem(previousHeader,SWT.NONE);
		subitem.setText(new String[] {treeObj.attributeName,treeObj.attributeValue});
		subitem.setData("header", false);
		subitem.setData("treeObject",treeObj);
		subitem.setData("expection", false);
		
		// Set expection if classExcption contains this object class
		if(classExpections.contains(treeObj.platformObject.getClass())) {
			subitem.setData("expection", true);
		}
		
		// Expand header
		previousHeader.setExpanded(true);
	}
	

	public Tree getTree() {
		return tree;
	}

	public List<TreeObject> getList() {
		return treeObjects;
	}

	public IO getSelectedIo() {
		// Return null if tree has no selection
		if(tree.getSelectionCount() == 0)
			return null;
		
		// Get selected TreeItem
		TreeItem item = tree.getSelection()[0];
		
		// Only if item is a header
		if((Boolean) item.getData("header")) {
			
			// Loop through TreeObject list
			for(TreeObject o : treeObjects) {
				
				// Find correct TreeObject
				if(item.getData("treeObject").equals(o) && o.platformObject.getClass().equals(IO.class)) {
					return (IO) o.platformObject;
				}
			}
		}
		return null;
	}
	
	public Include getSelectedInclude() {
		// Return null if tree has no selection
		if(tree.getSelectionCount() == 0)
			return null;
		
		// Get selected TreeItem
		TreeItem item = tree.getSelection()[0];
		
		// Only if item is a header
		if((Boolean) item.getData("header")) {
			
			// Loop through TreeObject list
			for(TreeObject o : treeObjects) {
				
				// Find correct TreeObject
				if(item.getData("treeObject").equals(o) && o.platformObject.getClass().equals(Include.class)) {
					return (Include) o.platformObject;
				}
			}
		}
		return null;
	}
	
	public PlatformObject getSelectedObject()
	{
		return this.selectedObject;
	}
}
