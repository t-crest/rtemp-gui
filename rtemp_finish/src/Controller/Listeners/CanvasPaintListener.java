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

package Controller.Listeners;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Rectangle;

import Model.Model;
import Model.Aegean.Link;
import Model.Aegean.Node;
import View.View;

public class CanvasPaintListener implements PaintListener {
	private View view;
	private Model model;
	private PaintEvent event;
	private int cpuSize;
	private double cpuSpacing;
	private int linkSpacing;
	private int linkWidth;


	private int w;
	private int h;

	public CanvasPaintListener(Model model, View view) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void paintControl(PaintEvent event) {
		this.event = event;
		// Sizes of the objects
		cpuSize = view.viewCanvas.CPU_SIZE;
		cpuSpacing = view.viewCanvas.CPU_SPACING;
		linkSpacing = view.viewCanvas.LINK_SPACING;
		linkWidth = view.viewCanvas.LINK_WIDTH;

		// Width and height of the platform
		w = model.getAegean().getPlatform().getWidth();
		h = model.getAegean().getPlatform().getHeight();

		// Draw links
		drawTopology();

		// Draw rectangles
		drawRectangles();

		// Draw node names
		drawNodeNames();

		// Draw SPSSize
		if(view.viewToolbarPlatform.itemShowSpm.getSelection()) {
			drawSpmsize();
		}
	}

	private void drawSpmsize() {
		// Draw square for each node-slot
		setBackgroundColor(SWT.COLOR_BLACK);
		setForegroundColor(SWT.COLOR_RED);

		List<Node> nodes = model.getAegean().getPlatform().getNodes();
		// TODO
		for(Node n : nodes) {
			Point p = model.locToPoint(n.getLoc());
			int x = (int) (p.x*cpuSize*cpuSpacing+cpuSize);
			int y = (int) (p.y*cpuSize*cpuSpacing+cpuSize);
			event.gc.drawText(n.getSpmSize(), x, y, false);
		}


	}

	private void drawRectangles() {
		// Draw square for each node-slot
		event.gc.setLineStyle(SWT.LINE_SOLID);
		event.gc.setLineWidth(4);

		view.viewCanvas.getRectangles().clear();

		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				drawRectangle(i,j);
			}
		}
	}

	private void drawTopology() {
		// Draw topology
		event.gc.setLineStyle(SWT.LINE_SOLID);
		event.gc.setLineCap(SWT.CAP_SQUARE);
		
		// Loop through all links
		List<Link> links = new ArrayList<Link>();

		switch(model.getTopologyType()) {
		case bitorus:
			links.addAll(model.createBitorusLinks());
			break;
		case mesh:
			links.addAll(model.createMeshLinks());
			break;
		case custom:
			links.addAll(model.getAegean().getPlatform().getTopology().getLinks());
			break;
		}

		view.viewCanvas.paths.clear();
		drawLinks(links);
	}
	
	private void drawLinks(List<Link> links) {
		List<Link> selectedLinks = view.viewCanvas.selectedLinks;
		Link selectedLink = view.viewCanvas.selectedLink;
		
		// Draw all links
		setForegroundColor(SWT.COLOR_BLUE);
		setBackgroundColor(SWT.COLOR_BLUE);
		for(Link l : links) {
			if(
					(selectedLink == null && selectedLinks.contains(l)) ||  
					(selectedLink != null && selectedLink.equals(l))
			) {
				// Color for selected links
				setForegroundColor(SWT.COLOR_RED);
				setBackgroundColor(SWT.COLOR_RED);
				event.gc.setLineWidth((int)(linkWidth*1.5));
			} else {
				// Color for other links
				setForegroundColor(SWT.COLOR_BLUE);
				setBackgroundColor(SWT.COLOR_BLUE);
				event.gc.setLineWidth(linkWidth);
			}
			
			view.viewCanvas.paths.add(drawLink(l));
		}
		
		// Draw selected link above the others
		setForegroundColor(SWT.COLOR_RED);
		setBackgroundColor(SWT.COLOR_RED);
		if(selectedLink != null) {
			drawLink(selectedLink);
		}
	}
	
	private Line2D[] drawLink(Link l) {
		Point source = model.locToPoint(l.getSource());
		Point sink = model.locToPoint(l.getSink());

		// Direction of link
		Point direction = new Point(
				(int) Math.signum(source.x - sink.x),
				(int) Math.signum(source.y - sink.y));

		Line2D[] path;

		// Draw links between nodes next to each other
		if(Math.abs(source.x - sink.x) <= 1 && Math.abs(source.y - sink.y) <= 1) {
			path = drawShortLink(source,sink,direction);
		}
		// Draw long vertical links
		else {
			path = drawLongLink(source,sink,direction);
		}
		
		return path;
		
	}

	private Line2D[] drawShortLink(Point source, Point sink, Point direction) {
		// Calculate pixel coordinates
		Point sourceCenter = calculateCenter(source);
		Point sinkCenter = calculateCenter(sink);

		// Move lines to border of node
		sourceCenter.translate(-(cpuSize/2)*direction.x, -(cpuSize/2)*direction.y);
		sinkCenter.translate((cpuSize/2)*direction.x, (cpuSize/2)*direction.y);

		// Move lines according to linkspacing
		sourceCenter.translate(linkSpacing*direction.y, linkSpacing*direction.x);
		sinkCenter.translate(linkSpacing*direction.y, linkSpacing*direction.x);

		// Draw line
		event.gc.drawLine(sourceCenter.x,sourceCenter.y,sinkCenter.x,sinkCenter.y);

		// Draw arrow
		drawArrowhead(sinkCenter, direction);

		Line2D l = new Line2D.Double(sourceCenter.x,sourceCenter.y,sinkCenter.x,sinkCenter.y);

		return new Line2D[] {l};
	}

	private Line2D[] drawLongLink(Point source, Point sink, Point direction) {	
		// Calculate pixel coordinates
		Point sourceCenter = calculateCenter(source);
		Point sinkCenter = calculateCenter(sink);

		// Move lines to border of node
		sourceCenter.translate((cpuSize/2)*direction.x, (cpuSize/2)*direction.y);
		sinkCenter.translate(-(cpuSize/2)*direction.x, -(cpuSize/2)*direction.y);

		// Create curved path based on direction of link
		Path path = new Path(view.getDisplay());

		// Direction of the link (vertical / horizontal)
		int absX = Math.abs(direction.x);
		int absY = Math.abs(direction.y);
		
		// Arc from source
		int arcSize = (int) ((cpuSize*cpuSpacing)/2);
		
		// Adjust arc size to cpuSpacing
		float arcSizeX = (float) (arcSize-absX*arcSize/(5*(cpuSpacing-1.2)));
		float arcSizeY = (float) (arcSize-absY*arcSize/(5*(cpuSpacing-1.2)));
		
		// Center point adjustment to adapt to new arc size
		float centerXdiff = (arcSizeX)/2*Math.abs(direction.x);
		float centerYdiff = (arcSizeY)/2*Math.abs(direction.y);
		
		// Arc from source
		path.addArc(
				sourceCenter.x-centerXdiff,
				sourceCenter.y-centerYdiff,
				arcSizeX,
				arcSizeY,
				90+90*absY,
				-180*direction.x+180*direction.y);

		// Arc from sink
		path.addArc(
				sinkCenter.x-centerXdiff,
				sinkCenter.y-centerYdiff,
				arcSizeX,
				arcSizeY,
				-90+90*absY,
				-180*direction.x+180*direction.y);

		// Draw path
		event.gc.drawPath(path);

		// Draw arrow (negative direction for long links)
		drawArrowhead(sinkCenter, new Point(-direction.x,-direction.y));


		// Generate Line2D array
		float[] floats = path.getPathData().points;
		Line2D[] lines = new Line2D[(floats.length/2)-1];
		
		Point previous = new Point((int)floats[0],(int)floats[1]);
		int next = 0;
		for(int i = 2; i < floats.length; i++) {
			Point current = new Point((int)floats[i],(int)floats[i+1]);
			i=i+1;
			
			lines[next] = new Line2D.Double(previous,current);
			
			previous = current;
			next = next+1;
			
			
		}

		path.dispose();
		return lines;
	}

	private void drawNodeNames() {
		// Draw all names
		List<Node> nodes = model.getAegean().getPlatform().getNodes();
		for(Node n : nodes) {
			setForegroundColor(SWT.COLOR_RED);

			String text =  n.getIpTypeRef();

			// Insert underscores (line breaks) at capital characters
			String newText = "";
			int counter = 0;
			char array[] = text.toCharArray();
			for(int i = 0; i < array.length; i++) {
				char c = array[i];
				if(Character.isUpperCase(c) && counter > 5) {
					newText += "_" + c;
					counter = 0;
				} else {
					newText += c;
					counter++;
				}
			}

			// Node name			
			String[] split = newText.split("_");


			// Center of rectangle
			Point point = calculateCenter(model.locToPoint(n.getLoc()));

			for(int i = 0; i < split.length; i++) {
				String s = split[i];
				int stringLength = event.gc.getFontMetrics().getAverageCharWidth()*s.length();
				int stringHeight = event.gc.getFontMetrics().getHeight();

				int x = point.x-stringLength/2;
				int y = point.y+stringHeight*i-stringHeight*(split.length/2)-(stringHeight/2)*((split.length)%2);
				event.gc.drawText(s, x, y, true);
			}

		}
	}

	private void drawRectangle(int i, int j) {
		setForegroundColor(SWT.COLOR_BLACK);
		setBackgroundColor(SWT.COLOR_WHITE);

		Rectangle r = new Rectangle((int)(i*cpuSize*cpuSpacing+cpuSize), (int)(j*cpuSize*cpuSpacing+cpuSize), cpuSize, cpuSize);

		// Blue background for selected rectangle
		if(r.equals(view.viewCanvas.getSelectedRectangle())) {
			setBackgroundColor(SWT.COLOR_GRAY);
			event.gc.fillRectangle(r);
		}

		// Draw rectangle
		event.gc.drawRectangle(r);

		// Add rectangle to list
		view.viewCanvas.getRectangles().add(r);
	}

	private void drawArrowhead(Point position, Point direction) {
		// Arrow size based on link width
		int arrowSize = linkWidth*6;
		// Calculations for making an arrow with equal side lengths
		int a = arrowSize/2;
		int b = (int) ((Math.sqrt(3)*arrowSize)/2);		

		// Calculate points for polygon based on direction of link
		Point[] points = new Point[3];
		points[0] = new Point(position.x,		position.y);
		points[1] = new Point(position.x+a*direction.y+b*direction.x,		position.y+a*direction.x+b*direction.y);
		points[2] = new Point(position.x-a*direction.y+b*direction.x,		position.y-a*direction.x+b*direction.y);

		// Convert points to int array
		int[] pointArray = new int[points.length*2];
		int counter = 0;
		for(Point p : points) {
			pointArray[counter] = p.x;
			counter++;
			pointArray[counter] = p.y;
			counter++;
		}

		// Draw arrowhead
		event.gc.fillPolygon(pointArray);		
	}

	private Point calculateCenter(Point p) {
		int x = (int) ((cpuSize*cpuSpacing)*p.x+cpuSize*1.5);
		int y = (int) ((cpuSize*cpuSpacing)*p.y+cpuSize*1.5);
		return new Point(x,y);
	}

	private void setBackgroundColor(int color) {
		event.gc.setBackground(event.display.getSystemColor(color));
	}

	private void setForegroundColor(int color) {
		event.gc.setForeground(event.display.getSystemColor(color));
	}

}
