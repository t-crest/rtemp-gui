package Controller.Listeners;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;

import Controller.Controller;
import Model.Model;
import Model.Aegean.Link;
import View.View;

public class CanvasMouseListener implements MouseListener {
	private View view;
	private Model model;
	private Controller controller;
	
	public CanvasMouseListener(Model model, View view, Controller controller) {
		this.view = view;
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void mouseDoubleClick(MouseEvent event) {
		int x = event.x;
		int y = event.y;
		
		int size = 20;
		Rectangle2D r = new Rectangle2D.Double(x-size/2,y-size/2,size,size);
		
		List<Line2D[]> paths = view.viewCanvas.paths;
		
		view.viewCanvas.selectedLinks.clear();
		view.viewCanvas.selectedLink = null;
		for(Line2D[] l : paths) {
			for(Line2D line : l) {
				if(line.intersects(r)) {
					int index = paths.indexOf(l);
					view.viewCanvas.selectedLinks.add(model.getAegean().getPlatform().getTopology().getLinks().get(index));
					break;
				}
			}
		}
		view.setSelectedDetails(model.getAegean().getPlatform().getTopology());
		view.update();
		
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		view.viewCanvas.getCanvas().setFocus();
	}

	@Override
	public void mouseUp(MouseEvent e) {
		boolean found = false;
		for(Rectangle r : view.viewCanvas.getRectangles()) {
			if(r.contains(e.x, e.y)) {
				view.viewCanvas.setSelectedRectangle(r);
				found = true;
			}
		}
		
		if(!found) {
			view.viewCanvas.setSelectedRectangle(null);
		}
		
		if(view.viewToolbarPlatform.itemAddCpu.getSelection()) {
			this.controller.addNode();
			
		}
		
		if(view.viewToolbarPlatform.itemRemoveCpu.getSelection()) {
			this.controller.removeNode();
		}
		
		if(view.viewToolbarPlatform.itemShowSpm.getSelection()) {
			this.controller.editSpm();
		}
		
		if(view.viewToolbarPlatform.itemAddLink.getSelection()) {

			if(view.viewCanvas.hasSelection()){
				if(model.addLink(view.viewCanvas.getSelectedPoint()))
				{ 
					view.viewCanvas.setSelectedRectangle(null);
				}
			} 
			else{
				model.getPointListAddLink().clear();
			}
		}

		if(view.viewToolbarPlatform.itemRemoveLink.getSelection()) {
			if(view.viewCanvas.hasSelection()){
				if(model.removeLink(view.viewCanvas.getSelectedPoint()))
				{ 
					view.viewCanvas.setSelectedRectangle(null);
				}
				else{
					model.getPointListAddLink().clear();
				}
			}
		}
		
		//New once links
		if(view.viewToolbarPlatform.itemAddAllFromLinks.getSelection()) {
			if(view.viewCanvas.hasSelection()){
				model.addToLinks(view.viewCanvas.getSelectedPoint());		
			}
		}
		if(view.viewToolbarPlatform.itemAddAllToLinks.getSelection()) {
			if(view.viewCanvas.hasSelection()){
				model.addFromLinks(view.viewCanvas.getSelectedPoint());		
			}
		}
		if(view.viewToolbarPlatform.itemAddAllFromToLinks.getSelection()) {
			if(view.viewCanvas.hasSelection()){
				model.addToFromLinks(view.viewCanvas.getSelectedPoint());		
			}
		}
		
		
		view.update();
	}

}
