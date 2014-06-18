package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Aegean.Aegean;
import Model.Aegean.Include;
import Model.Aegean.Link;
import Model.Aegean.Node;
import Model.IPCores.IPCore;
import Model.IPCores.IPCores;
import Model.Static.OSFinder;
import Model.Static.XmlSerializer;

public class Model {
	private List<IPCores> ipcores = new ArrayList<IPCores>();
	private Aegean aegean =  new Aegean();
	private List<Point> pointListAddLink = new ArrayList<Point>();
	private List<Point> pointListRemoveLink = new ArrayList<Point>();

	/* ************************* CONSTRUCTOR ************************* */

	public Model()
	{

	}

	/* *************************** METHODS *************************** */

	//Loading platform with include IPCores.
	public boolean loadPlatform(String file) {
		try {
			setAegean(XmlSerializer.Load(Aegean.class, file));

			loadIPCores();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//Saving platform with the IPCores. With new platformname.
	public void savePlatform(String file) {
		try {
			XmlSerializer.Save(getAegean(), file);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		saveIPCores();
	}

	//Saving platform on the currently open one with the included IPCores
	public void saveCurrentPlatform() {
		savePlatform(getAegean().getAbsorlutepath());
	}

	//Loading the IPCores from the includes in the platform.
	public void loadIPCores() {
		System.out.println("Loading ip cores");
		//Clear all the existing IPCores that has been previously loaded.
		ipcores.clear();
		for(Include i : getAegean().getPlatform().getInclude()) {

			if(!i.getHref().equals(""))
			{
				String filepath = i.getHref();
				// Remove two first characters
				filepath = filepath.substring(1);

				filepath = getAegean().getParrentFolder() + "/config" + filepath;

				System.out.println(OSFinder.isWindows());
				if(OSFinder.isWindows()) {
					filepath = filepath.replace("/", "\\");
					System.out.println(filepath);
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

	//Saving IPCores
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

	//Get the topology type
	public TopologyTypes getTopologyType() {

		TopologyTypes l = null;
		try {
			l = getAegean().getPlatform().getTopology().getTopoType();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l == null ? TopologyTypes.custom : l;
	}

	//Copy the link configuration
	public void copyTopologyTypes() {
		// Clear all links
		getAegean().getPlatform().getTopology().getLinks().clear();
		System.out.println("links cleared");

		switch(getTopologyType()) {
		case bitorus:
			getAegean().getPlatform().getTopology().getLinks().addAll(createBitorusLinks());
			getAegean().getPlatform().getTopology().setTopoType(TopologyTypes.custom);
			break;
		case mesh:
			getAegean().getPlatform().getTopology().getLinks().addAll(createMeshLinks());
			getAegean().getPlatform().getTopology().setTopoType(TopologyTypes.custom);
			break;
		}
	}

	//Creating bitorus links
	public List<Link> createBitorusLinks() {
		List<Link> result = new ArrayList<Link>();
		int w = getAegean().getPlatform().getWidth();
		int h = getAegean().getPlatform().getHeight();

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

	//Creating mesh links
	public List<Link> createMeshLinks() {
		List<Link> result = new ArrayList<Link>();
		int w = getAegean().getPlatform().getWidth();
		int h = getAegean().getPlatform().getHeight();

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

	//Adds a link
	public boolean addLink(Point pointSelected)
	{

		boolean linkHasBeenCreated=false;
		boolean foundExistingLink=false;
		String pointFrom = "";
		String pointTo= "";

		if(pointListAddLink.size()==0)
		{
			pointListAddLink.add(pointSelected);
		}

		else if(pointListAddLink.size()==1 && !pointListAddLink.get(0).equals(pointSelected))
		{
			int platformWidth = getAegean().getPlatform().getWidth()-1;
			int platformHeight = getAegean().getPlatform().getHeight()-1;

			int tempXplus = (int) pointListAddLink.get(0).getX() + 1;
			if(tempXplus > platformWidth)
			{
				tempXplus = 0;
			}

			int tempXminus = (int) pointListAddLink.get(0).getX() - 1;
			if(tempXminus < 0)
			{
				tempXminus = platformWidth;
			}

			int tempYplus = (int) pointListAddLink.get(0).getY() + 1;
			if(tempYplus > platformHeight)
			{
				tempYplus = 0;
			}

			int tempYminus = (int) pointListAddLink.get(0).getY() - 1;
			if(tempYminus < 0)
			{
				tempYminus = platformHeight;
			}

			if( ((pointSelected.x==tempXplus || pointSelected.x==tempXminus) && pointSelected.y==pointListAddLink.get(0).getY()) || 
					((pointSelected.y==tempYplus || pointSelected.y==tempYminus) && pointSelected.x==pointListAddLink.get(0).getX()) ){

				pointListAddLink.add(pointSelected);
				pointFrom = pointToLoc(pointListAddLink.get(0));
				pointTo = pointToLoc(pointListAddLink.get(1));

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
				pointListAddLink.clear();

			} else{
				foundExistingLink = true;
				pointListAddLink.clear();
			}
		} else { 
			foundExistingLink = true;
			pointListAddLink.clear();
		}

		sortLinks();

		return (foundExistingLink || linkHasBeenCreated);
	}

	//Sorting links after the point location
	private void sortLinks() {

		Collections.sort(aegean.getPlatform().getTopology().getLinks());
	}

	//Removing a link
	public boolean removeLink(Point pointSelected)
	{
		boolean linkHasBeenRemoved=false;
		boolean wrongRemoveType=false;
		String pointFrom = "";
		String pointTo= "";

		if(pointListRemoveLink.size()==0)
		{
			pointListRemoveLink.add(pointSelected);
		}

		else if(pointListRemoveLink.size()==1 && !pointListRemoveLink.get(0).equals(pointSelected))
		{
			pointListRemoveLink.add(pointSelected);
			pointFrom = pointToLoc(pointListRemoveLink.get(0));
			pointTo = pointToLoc(pointListRemoveLink.get(1));

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
			pointListRemoveLink.clear();

		} else { 

			wrongRemoveType = true;
			pointListRemoveLink.clear();
		}
		return (linkHasBeenRemoved || wrongRemoveType);
	}

	//Adding links to a node, from the surrounding nodes
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

	//Adding links from a node, to the surrounding nodes
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

	//Adding links to and from a node, to the surrounding nodes
	public void addToFromLinks(Point point) {
		addToLinks(point);
		addFromLinks(point);

	}

	//used to destroy long links
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

	//Making a string location to point
	public Point locToPoint(String s) {
		return new Point(Integer.parseInt(s.substring(1, 2)), Integer.parseInt(s.substring(3, 4)));
	}

	//making a point to string location
	public String pointToLoc(Point p) {
		return "(" + p.x + "," + p.y + ")";
	}

	//Cloning an existing IPCore
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

	//Removes an IPCore
	public void removeIpcore(IPCore ipcore) {
		for(IPCores ips : ipcores) {
			if(ips.getIpCores().contains(ipcore)) {
				ips.getIpCores().remove(ipcore);
				break;
			}
		}
	}

	//Add a node
	public void addNode(Node node) {	
		getAegean().getPlatform().getNodes().add(node);
		Collections.sort(getAegean().getPlatform().getNodes());
		updateNodeId();


	}

	//Updating node id
	private void updateNodeId() {
		for (int i=0; i<getAegean().getPlatform().getNodes().size();i++){
			getAegean().getPlatform().getNodes().get(i).setId("pat"+i);
		}

	}

	//Remove a node
	public void removeNode(Node n) {
		getAegean().getPlatform().getNodes().remove(n);
		updateNodeId();
	}


	/* ********************* GETTERS AND SETTERS ********************* */

	//Get the list of points you are adding links between
	public List<Point> getPointListAddLink() {
		return pointListAddLink;
	}

	//get a list of points you are removing links between
	public List<Point> getPointListRemoveLink() {
		return pointListRemoveLink;
	}

	//Returning a node
	public Node getNode(Point point) {
		if(point!=null){
			for(Node n : getAegean().getPlatform().getNodes()) {

				Point point2 = locToPoint(n.getLoc());
				if(point2.equals(point)) {
					return n;
				}
			}
		}
		return null;
	}

	//Setting the aegean object
	public void setAegean(Aegean aegean) {
		this.aegean = aegean;
	}

	//return the aegean object
	public Aegean getAegean() {
		return aegean;
	}

	//Returning the IPCores
	public List<IPCores> getIpcores() {
		return ipcores;
	}

}
