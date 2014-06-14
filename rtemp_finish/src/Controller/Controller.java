package Controller;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.swt.SWT;

import Controller.Listeners.CanvasListener;
import Controller.Listeners.CanvasPaintListener;
import Controller.Listeners.DetailsListener;
import Controller.Listeners.DropdownMenuListener;
import Controller.Listeners.MenuListener;
import Controller.Listeners.TabListener;
import Controller.Listeners.ToolbarListener;
import Dialogs.TextInputDialog;
import Model.Model;
import Model.Aegean.Node;
import Model.IPCores.IPCore;
import View.View;

public class Controller {
	private Model model;
	private View view;

	public Controller(Model model, View view)
	{
		this.model = model;
		this.view = view;

		view.addToolbarListener	(new ToolbarListener	(model,view, this));
		view.addDetailsListener	(new DetailsListener	(model,view));
		view.addMenuListener	(new MenuListener		(model,view, this));
		view.addCanvasListener	(new CanvasListener		(model,view,this));
		view.addCanvasPaintListener (new CanvasPaintListener (model,view));
		view.addDropdownMenuListener(new DropdownMenuListener(model,view,this));
		view.addTabListener		(new TabListener(model,view));

		//open shell
		view.getShell().open();
		while (!view.getShell().isDisposed()) {
			if (!view.getDisplay().readAndDispatch ()) {
				view.getDisplay().sleep ();}
		}
		view.getDisplay().dispose ();
	}

	public void removeNode() {

		Node selectedNode = view.viewCanvas.getSelectedNode();
		if(selectedNode != null){
			model.removeNode(selectedNode);
			view.update();
		}
	}

	public void addNode() {
		Node selectedNode = view.viewCanvas.getSelectedNode();
		IPCore selectedIp = view.viewCpu.getSelectedIpcore();

		if (view.viewCanvas.hasSelection() && selectedIp != null) {
			if(selectedNode != null) {
				model.removeNode(selectedNode);
			}
			Node newNode = new Node();
			newNode.setIpTypeRef(selectedIp.getIpType());
			newNode.setLoc(this.view.viewCanvas.getSelectedPoint());


			if(selectedNode != null)
				newNode.setSpmSize(selectedNode.getSpmSize());

			model.addNode(newNode);
			view.update();
		} 
	}


	public void editSpm() {
		Node selectedNode = view.viewCanvas.getSelectedNode();

		Object[] result = view.dialogEditSpm();
		boolean ok = (Integer)result[0] == SWT.OK ? true : false;
		if(ok) {
			String[] s = (String[])result[1];
			selectedNode.setSpmSize(s[0]);
		}
	}

	public void runCommand(String commandIn) {
		
		String filename= model.getPlatformName().replaceAll(".xml", "");
		String command = "tell application \"Terminal\" to do script " + "\"make -C " + model.getAegean().getParrentFolder()+ " AEGEAN_PLATFORM=" + filename +" "+commandIn + "\"" ;
		
		String[] cmd = { "osascript", "-e", command};
		Process process = null;
		
			try {
				process = Runtime.getRuntime().exec(cmd);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String lsString = "";
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));
			try {
				while ((lsString = bufferedReader.readLine()) != null) {
					System.out.println(lsString);




}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}




}



