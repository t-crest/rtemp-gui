package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Aegean.Aegean;
import Model.Aegean.Include;
import Model.Aegean.Link;
import Model.Aegean.Node;
import Model.Aegean.Platform;
import Model.IPCores.IPCore;
import Model.IPCores.IPCores;
import Model.Static.OSFinder;
import Model.Static.Settings;
import Model.Static.XmlSerializer;

public class Model {
	private List<IPCores> ipcores = new ArrayList<IPCores>();
	private Aegean aegean =  new Aegean();

	private String selectedTool="", selectedCpu="";
	private List<Point> pointListAdd = new ArrayList<Point>();
	private List<Point> pointListRemove = new ArrayList<Point>();

	/* ************************* CONSTRUCTOR ************************* */

	public Model()
	{

	}

	/* *************************** METHODS *************************** */
	public boolean loadFile(String file) {
		try {
			setAegean(XmlSerializer.Load(Aegean.class, file));
			
			loadIPCores();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Loading the IPCores using the includes in from the platform.
	public void loadIPCores() {

		//Clear all the existing IPCores that has been previously loaded.
		ipcores.clear();
		for(Include i : getAegean().getPlatform().getInclude()) {

			if(!i.getHref().equals(""))
			{
				String filepath = i.getHref();
				// Remove two first characters
				filepath = filepath.substring(1);

				filepath = "config" + filepath;

				if(OSFinder.isWindows()) {
					filepath = filepath.replace("/", "\\");
				}


				try {
					IPCores ips = XmlSerializer.Load(IPCores.class, filepath);
					ipcores.add(ips);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
		}

	}
	
	//Saving ip cores
	public void saveIPCores() {
		for(IPCores ip : getIpcores()) {
			try {

				XmlSerializer.Save(ip,ip.getAbsolutePath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	//When saving platform the included IPCores get saved as well
	public void savePlatform(PlatformObject o, String file) {
		try {
			XmlSerializer.Save(o, file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		saveIPCores();
	}
	
	public void saveCurrentPlatform() {
		try {
			XmlSerializer.Save(getAegean(), getAegean().getAbsorlutepath());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		saveIPCores();
	}
	
	public linkConfiguration getTopologyType() {
		
		linkConfiguration l = null;
		try {
			l = getAegean().getPlatform().getTopology().getTopoType();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l == null ? linkConfiguration.custom : l;
	}

	public void copyLinkConfiguration() {
		// Clear all links
		getAegean().getPlatform().getTopology().getLinks().clear();
		System.out.println("links cleared");
		
		switch(getTopologyType()) {
		case bitorus:
			getAegean().getPlatform().getTopology().getLinks().addAll(createBitorusLinks());
			getAegean().getPlatform().getTopology().setTopoType(linkConfiguration.custom);
			break;
		case mesh:
			getAegean().getPlatform().getTopology().getLinks().addAll(createMeshLinks());
			getAegean().getPlatform().getTopology().setTopoType(linkConfiguration.custom);
			break;
		}
	}
	
	public List<Link> createBitorusLinks() {
		List<Link> result = new ArrayList<Link>();
		int w = getPlatform().getWidth();
		int h = getPlatform().getHeight();

		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Point[] sinks = new Point[4];
				sinks[0] = new Point(i+1,j);
				sinks[1] = new Point(i-1,j);	
				sinks[2] = new Point(i,j+1);	
				sinks[3] = new Point(i,j-1);	

				for(int k = 0; k < 4; k++) {
					if(sinks[k].x == -1) {
						sinks[k].x = w-1;
					} else
						if(sinks[k].x == w) {
							sinks[k].x = 0;
						}

					if(sinks[k].y == -1) {
						sinks[k].y = h-1;
					} else
						if(sinks[k].y == h) {
							sinks[k].y = 0;
						}
					Link l = new Link();
					l.setSource(pointToLoc(new Point(i,j)));
					l.setSink(pointToLoc(sinks[k]));
					result.add(l);
				}	
			}
		}
	
		return result;
	}
	
	public List<Link> createMeshLinks() {
		List<Link> result = new ArrayList<Link>();
		int w = getPlatform().getWidth();
		int h = getPlatform().getHeight();

		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				Point[] sinks = new Point[4];
				sinks[0] = new Point(i+1,j);
				sinks[1] = new Point(i-1,j);	
				sinks[2] = new Point(i,j+1);	
				sinks[3] = new Point(i,j-1);	

				for(int k = 0; k < 4; k++) {
					if(sinks[k].x < 0 || sinks[k].y < 0 || sinks[k].x >= w || sinks[k].y >= h) {
						continue;
					}
					Link l = new Link();
					l.setSource(pointToLoc(new Point(i,j)));
					l.setSink(pointToLoc(sinks[k]));
					result.add(l);
				}	
			}
		}
	
		return result;
	}
	
	public IPCore cloneIpcore(IPCore original) throws CloneNotSupportedException {
		// Clone IPCore
		IPCore newIpcore = (IPCore) original.clone();
		
		// Change name
		newIpcore.setIpType("Copy_" + newIpcore.getIpType());
		
		// Insert new IPCore in correct list
		for(IPCores ips : ipcores) {
			if(ips.getIpCores().contains(original)) {
				ips.getIpCores().add(newIpcore);
				break;
			}
		}
		
		return newIpcore;
	}
	
	public void removeIpcore(IPCore ipcore) {
		for(IPCores ips : ipcores) {
			if(ips.getIpCores().contains(ipcore)) {
				ips.getIpCores().remove(ipcore);
				break;
			}
		}
	}

	public boolean addLink(Point pointSelected)
	{

		boolean linkHasBeenCreated=false;
		boolean foundExistingLink=false;
		String pointFrom = "";
		String pointTo= "";

		if(pointListAdd.size()==0)
		{
			pointListAdd.add(pointSelected);
		}

		else if(pointListAdd.size()==1 && !pointListAdd.get(0).equals(pointSelected))
		{
			int platformWidth = getAegean().getPlatform().getWidth()-1;
			int platformHeight = getAegean().getPlatform().getHeight()-1;
			
			int tempXplus = (int) pointListAdd.get(0).getX() + 1;
			if(tempXplus > platformWidth)
			{
				tempXplus = 0;
			}
			
			int tempXminus = (int) pointListAdd.get(0).getX() - 1;
			if(tempXminus < 0)
			{
				tempXminus = platformWidth;
			}
			
			int tempYplus = (int) pointListAdd.get(0).getY() + 1;
			if(tempYplus > platformHeight)
			{
				tempYplus = 0;
			}
			
			int tempYminus = (int) pointListAdd.get(0).getY() - 1;
			if(tempYminus < 0)
			{
				tempYminus = platformHeight;
			}
			
			if( ((pointSelected.x==tempXplus || pointSelected.x==tempXminus) && pointSelected.y==pointListAdd.get(0).getY()) || 
				((pointSelected.y==tempYplus || pointSelected.y==tempYminus) && pointSelected.x==pointListAdd.get(0).getX()) ){

				pointListAdd.add(pointSelected);
				pointFrom = pointToLoc(pointListAdd.get(0));
				pointTo = pointToLoc(pointListAdd.get(1));

				for(Link linkTemp : aegean.getPlatform().getTopology().getLinks())
				{
					if(linkTemp.getSource().equals(pointFrom) && linkTemp.getSink().equals(pointTo))
					{
						foundExistingLink = true;
					}
				}

				if(!foundExistingLink)
				{
					Link link = new Link();
					link.setLinkDepth(aegean.getPlatform().getTopology().getLinkDepth());
					link.setSource(pointFrom);
					link.setSink(pointTo);
					aegean.getPlatform().getTopology().getLinks().add(link);
					linkHasBeenCreated = true;
				}
				pointListAdd.clear();

			} else{
				foundExistingLink = true;
				pointListAdd.clear();
				}
		} else { 
			foundExistingLink = true;
			pointListAdd.clear();
		}

		sortLinks();
		
		return (foundExistingLink || linkHasBeenCreated);
	}

	private void sortLinks() {
		
		Collections.sort(aegean.getPlatform().getTopology().getLinks());
	}

	public boolean removeLink(Point pointSelected)
	{
		boolean linkHasBeenRemoved=false;
		boolean wrongRemoveType=false;
		String pointFrom = "";
		String pointTo= "";

		if(pointListRemove.size()==0)
		{
			pointListRemove.add(pointSelected);
		}

		else if(pointListRemove.size()==1 && !pointListRemove.get(0).equals(pointSelected))
		{
			pointListRemove.add(pointSelected);
			pointFrom = pointToLoc(pointListRemove.get(0));
			pointTo = pointToLoc(pointListRemove.get(1));

			for(Link linkTemp : aegean.getPlatform().getTopology().getLinks())
			{
				if(linkTemp.getSource().equals(pointFrom) && linkTemp.getSink().equals(pointTo))
				{
					aegean.getPlatform().getTopology().getLinks().remove(linkTemp);
					linkHasBeenRemoved = true;
					break;
				}
			}
			wrongRemoveType = true;
			pointListRemove.clear();

		} else { 

			wrongRemoveType = true;
			pointListRemove.clear();
		}
		return (linkHasBeenRemoved || wrongRemoveType);
	}

	
	/* ********************* GETTERS AND SETTERS ********************* */

	public void setAegean(Aegean aegean) {
		this.aegean = aegean;
	}

	public Aegean getAegean() {
		return aegean;
	}

	public List<IPCores> getIpcores() {
		return ipcores;
	}

	public String getSelectedTool() {
		return selectedTool;
	}

	public void setSelectedTool(String selectedTool) {
		this.selectedTool = selectedTool;
	}

	public Point locToPoint(String s) {
		return new Point(Integer.parseInt(s.substring(1, 2)), Integer.parseInt(s.substring(3, 4)));
	}
	
	public String pointToLoc(Point p) {
		return "(" + p.x + "," + p.y + ")";
	}
	
	
	
	
	

	public Platform getPlatform() {
		// TODO Auto-generated method stub
		return this.aegean.getPlatform();
	}

	public List<Node> getNodes() {
		// TODO Auto-generated method stub
		return getPlatform().getNodes();
	}

	public List<Link> getLinks() {
		// TODO Auto-generated method stub
		return getPlatform().getTopology().getLinks();
	}

	public void addNode(Node node) {	
		getPlatform().getNodes().add(node);
		Collections.sort(getPlatform().getNodes());
		updateNodeId();
		
	
	}

	private void updateNodeId() {
		for (int i=0; i<getPlatform().getNodes().size();i++){
			getPlatform().getNodes().get(i).setId("pat"+i);
		}
		
	}

	public Node getNode(Point point) {
		if(point!=null){
			for(Node n : getNodes()) {
				
				Point point2 = locToPoint(n.getLoc());
				if(point2.equals(point)) {
					return n;
				}
			}
		}
		return null;
	}

	public void removeNode(Node n) {
		getNodes().remove(n);
		updateNodeId();
	}

	public String getPlatformName() {
		return this.getAegean().getFileName();
	}

	public String getPlatformDir() {
		
		try {			
			return this.getAegean().getAbsorlutepath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "ERROR";
	}

	public List<Point> getPointListAdd() {
		return pointListAdd;
	}
	
	public List<Point> getPointListRemove() {
		return pointListRemove;
	}

	public void addToLinks(Point point) {
	
		int width = aegean.getPlatform().getWidth();
		int height = aegean.getPlatform().getHeight();
		
		addLink(point);
		Point tempPoint = new Point(point.x-1, point.y);
		if(tempPoint.x<0)
		{
			tempPoint.x = width-1;
			addLink(tempPoint);
		}else{addLink(tempPoint);}
		
		addLink(point);
		tempPoint = new Point(point.x+1, point.y);
		if(tempPoint.x>=width)
		{
			tempPoint.x = 0;
			addLink(tempPoint);
		}else{addLink(tempPoint);}
	
		addLink(point);
		tempPoint = new Point(point.x, point.y-1);
		if(tempPoint.y<0)
		{
			tempPoint.y = height-1;
			addLink(tempPoint);
		}else{addLink(tempPoint);}
	
		addLink(point);
		tempPoint = new Point(point.x, point.y+1);
		if(tempPoint.y>=height)
		{
			tempPoint.y = 0;
			addLink(tempPoint);
		} else{addLink(tempPoint);}
	}

	public void addFromLinks(Point point) {
		int width = aegean.getPlatform().getWidth();
		int height = aegean.getPlatform().getHeight();
		
		Point tempPoint = new Point(point.x-1, point.y);
		if(tempPoint.x<0)
		{
			tempPoint.x = width-1;
			addLink(tempPoint);
		}else{addLink(tempPoint);}
		addLink(point);
		
		tempPoint = new Point(point.x+1, point.y);
		if(tempPoint.x>=width)
		{
			tempPoint.x = 0;
			addLink(tempPoint);
		}else{addLink(tempPoint);}
		addLink(point);
	
		tempPoint = new Point(point.x, point.y-1);
		if(tempPoint.y<0)
		{
			tempPoint.y = height-1;
			addLink(tempPoint);
		}else{addLink(tempPoint);}
		addLink(point);
		
		tempPoint = new Point(point.x, point.y+1);
		if(tempPoint.y>=height)
		{
			tempPoint.y = 0;
			addLink(tempPoint);
		} else{addLink(tempPoint);}
		addLink(point);
		
	}

	public void addToFromLinks(Point point) {
		addToLinks(point);
		addFromLinks(point);
		
	}

	public void destroyLongLinks(String direction) {
		
		List<Link> links = getAegean().getPlatform().getTopology().getLinks();
		List<Node> nodes = getAegean().getPlatform().getNodes(); 
		
		if(direction.equals("height"))
		{
			int height = getAegean().getPlatform().getHeight();
			
			for (int i=links.size()-1; i>=0;i--)
			{
				
				int sourceY = locToPoint(links.get(i).getSource()).y;
				int sinkY = locToPoint(links.get(i).getSink()).y;
				
				int differenceY = Math.abs(sourceY-sinkY);
				int differenceSourceYHeight = height-sourceY;
				int differenceSinkYHeight = height-sinkY;
				
				if(differenceY>1 || differenceSourceYHeight<1 || differenceSinkYHeight<1){
					links.remove(i);
				}
				
			}
			
			for (int i=nodes.size()-1; i>=0;i--)
			{
				int nodeY = locToPoint(nodes.get(i).getLoc()).y;
				int differenceYHeight = height-nodeY;
				
				if(differenceYHeight<1){
					nodes.remove(i);
				}
			}
		}
		
		if(direction.equals("width"))
		{
			int width = getAegean().getPlatform().getWidth();
			for (int i=links.size()-1; i>=0;i--)
			{
				int sourceX = locToPoint(links.get(i).getSource()).x;
				int sinkX = locToPoint(links.get(i).getSink()).x;
				int differenceX = Math.abs(sourceX-sinkX);
				int differenceSourceXHeight = width-sourceX;
				int differenceSinkXHeight = width-sinkX;
				
				if(differenceX>1 || differenceSourceXHeight<1 || differenceSinkXHeight<1){
					links.remove(i);
				}
			}
			
			for (int i=nodes.size()-1; i>=0;i--)
			{
				int nodeX = locToPoint(nodes.get(i).getLoc()).x;
				int differenceYWidth = width-nodeX;
				
				if(differenceYWidth<1){
					nodes.remove(i);
				}
			}
			
		}
		
	}

}
