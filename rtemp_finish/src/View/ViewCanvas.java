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

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import Model.Model;
import Model.Aegean.Link;
import Model.Aegean.Node;


public class ViewCanvas {
	private Composite canvas;
	private List<Rectangle> rectangles = new ArrayList<Rectangle>();
	private Rectangle selectedRectangle;
	private View view;
	private Model model;
	public final double CPU_SPACING = 2;
	public final int CPU_SIZE = 60;
	public final int LINK_SPACING = 0;
	public final int LINK_WIDTH = 2;
	public List<Line2D[]> paths = new ArrayList<Line2D[]>();
	public List<Link> selectedLinks = new ArrayList<Link>();
	public Link selectedLink;

	/* ********************* CONSTRUCTOR ********************* */
	public ViewCanvas(Model model, View view) {
		this.view = view;
		this.model = model;

		this.canvas = new Composite(view.getShell(), SWT.BORDER);
		GridData gridDataCanvas = new GridData(SWT.FILL,SWT.FILL,true,true);
		canvas.setLayoutData(gridDataCanvas);

		repaint();	
		
		canvas.setFocus();
	}

	/* ********************* METHODS ********************* */

	public void repaint() {
		Color white = view.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		canvas.setBackground(white);
		canvas.redraw();
	}

	public boolean hasSelection() {
		return this.selectedRectangle != null;
	}

	public Point getSelectedPoint() {
		int index = rectangles.indexOf(selectedRectangle);
		int height = model.getAegean().getPlatform().getHeight();
		if(height!=0){
			return new Point(index/height, index%height);
		}
		return null;
	}

	public Node getSelectedNode() {
		return this.model.getNode(getSelectedPoint());
	}
	
	/* ********************* GETTERS AND SETTERS ********************* */

	public List<Rectangle> getRectangles() {
		return this.rectangles;
	}

	public void setSelectedRectangle(Rectangle r) {
		this.selectedRectangle = r;
	}

	public Composite getCanvas() {
		return this.canvas;
	}

	public Rectangle getSelectedRectangle() {
		return selectedRectangle;
	}

}
