package Model.Aegean;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class Platform  extends PlatformObject {


	
	@Attribute(required=false)
	private String width="0";
	@Attribute(required=false)
	private String height="0";
	@Element(required=false)
	private Topology topology = new Topology();
	@ElementList(required=false)
	private ArrayList<Node> nodes = new ArrayList<Node>();
	@Element
	private Memory memory = new Memory();
	@ElementList(inline=true)
	private List<Include> include  = new ArrayList<Include>(); 

	public int getWidth() {
		return Integer.parseInt(width);
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int getHeight() {
		return Integer.parseInt(height);
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Topology getTopology() {
		return topology;
	}

	public void setTopology(Topology topology) {
		this.topology = topology;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}
	
	public List<Include> getInclude() {
		return include;
	}

	public void setInclude(ArrayList<Include> include) {
		this.include = include;
	}
}
