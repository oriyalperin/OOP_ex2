package api;

import java.io.*;
import java.util.*;
import com.google.gson.*;


public class DWGraph_Algo implements dw_graph_algorithms{
	private  directed_weighted_graph g ;
	static int Time = 0;

	@Override
	public void init(directed_weighted_graph g) {
		this.g=g;

	}

	@Override
	public directed_weighted_graph getGraph() {
		return this.g;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph NewGraph = new DWGraph_DS();
		for (node_data nodeToCopy : this.g.getV()) {
			int NodeKey = nodeToCopy.getKey();
			node_data AllNewNode = this.g.getNode(NodeKey);
			NewGraph.addNode(AllNewNode);
			String nodeInfo = nodeToCopy.getInfo();
			NewGraph.getNode(NodeKey).setInfo(nodeInfo);
		}
		for (node_data nodeToCopy : this.g.getV()) {
			int NodeKey = nodeToCopy.getKey();
			for (edge_data edgeToCopy : this.g.getE(NodeKey)) {
				NewGraph.connect(NodeKey, edgeToCopy.getDest(), edgeToCopy.getWeight());
			}
		}
		return NewGraph;
	}

	@Override
	public boolean isConnected() {
		if (this.g.nodeSize() == 0 || this.g.nodeSize() == 1) {//if the g is null or onr he isConnected
			return true;
		}
		else
		{
			connected c=new connected(g.getV());
			resetDiscLowSm(c);
			LinkedList<LinkedList<Integer>> l=SCC(c);

			if(l.size()==1)
				return true;
			else
				return false;

		}

	}
	protected void resetDiscLowSm(connected c) {


		for (connected.ver nd : c.getHM().values()) {
			nd.setDisc(-1);
			nd.setDisc( -1);
			nd.setLow (-1);
			nd.setSm( false);
		}
	}
	void SCCUtil(int u, Stack<Integer> st, LinkedList<LinkedList<Integer>>com,connected c)
	{

		connected.ver uFields=c.getNode(u);
		uFields.setDisc(Time);
		uFields.setLow(Time);
		Time += 1;
		uFields.setSm(true);
		st.push(u);
		connected.ver niFields;
		for(edge_data edge: g.getE(u))
		{

			niFields=c.getNode(edge.getDest());
			if (niFields.getDisc() == -1)
			{
				SCCUtil(niFields.getKey(), st,com,c);

				uFields.setLow(Math.min(uFields.getLow(),niFields.getLow()));
			}
			else if (niFields.isSm() == true)
			{


				uFields.setLow(Math.min(uFields.getLow(), niFields.getDisc()));
			}
		}

		int w = -1;
		LinkedList<Integer>list=new LinkedList<>();
		if (uFields.getLow() == uFields.getDisc())
		{
			while (w != u)
			{

				w = st.pop();
				connected.ver wn=c.getNode(w);
				list.add(w);
				wn.setSm(false);

			}
			com.add(list);
		}
	}


	LinkedList<LinkedList<Integer>> SCC(connected c)
	{
		LinkedList<LinkedList<Integer>>com=new LinkedList<>();
		Stack<Integer> st = new Stack<Integer>();

		for(node_data nodes: g.getV())
		{
			int key=nodes.getKey();
			if( c.getNode(key).getDisc() == -1)
				SCCUtil(key, st,com,c);
		}
		return com;
	}




	@Override
	public double shortestPathDist(int src, int dest) {
		HashMap<Integer, node_data> nodesMap = new HashMap<Integer, node_data>();
		for (node_data nodeData : g.getV()) {
			nodeData.setWeight(Integer.MAX_VALUE);
		}
		node_data start = g.getNode(src);
		start.setWeight(0);
		nodesMap.put(src, start);
		while (!nodesMap.isEmpty()) {
			node_data currentNode = this.findMinimumDistanceNode(nodesMap);
			if (currentNode.getKey() == dest) {
				break;
			}
			int currentNodeKey = currentNode.getKey();
			double currentNodeDistance = currentNode.getWeight();
			nodesMap.remove(currentNodeKey);

			for (edge_data e : g.getE(currentNodeKey)) {
				Integer neighbor=e.getDest();
				double distanceToNeighbor = g.getEdge(currentNodeKey, neighbor).getWeight() + currentNodeDistance;
				node_data nei=g.getNode(neighbor);

				if (distanceToNeighbor < nei.getWeight() ) {
					nei.setWeight(distanceToNeighbor);
					nodesMap.put(neighbor, nei);
				}
			}
		}

		double distance = g.getNode(dest).getWeight();

		return distance;
	}

	private node_data findMinimumDistanceNode(HashMap<Integer, node_data> nodesMap) {
		node_data minimumDistanceNode = null;
		double minimumDistance = Integer.MAX_VALUE;
		for (node_data node : nodesMap.values()) {
			if (node.getWeight() <= minimumDistance) {
				minimumDistanceNode = node;
				minimumDistance = node.getWeight();
			}
		}

		return minimumDistanceNode;
	}




@Override
public List<node_data> shortestPath(int src, int dest) {
	HashMap<Integer, node_data> nodesMap = new HashMap<Integer, node_data>();
	for (node_data nodeData : g.getV()) {
		nodeData.setWeight(Integer.MAX_VALUE);
	}
	HashMap<Integer, LinkedList<node_data>> shortestPathMap = new HashMap<>();
	for (node_data nodeData : g.getV()) {
		shortestPathMap.put(nodeData.getKey(), new LinkedList<>());
	}
	node_data start = g.getNode(src);
	start.setWeight(0);
	nodesMap.put(src, start);
	while (!nodesMap.isEmpty()) {
		node_data currentNode = this.findMinimumDistanceNode(nodesMap);
		if (currentNode.getKey() == dest) {
			break;
		}
		int currentNodeKey = currentNode.getKey();
		double currentNodeDistance = currentNode.getWeight();
		nodesMap.remove(currentNodeKey);
		LinkedList<node_data> currentNodePath;
		 currentNodePath= shortestPathMap.get(currentNodeKey);
		for (edge_data n : (g.getE(currentNodeKey))) {
			int neighbor=n.getDest();
			double distanceToNeighbor = g.getEdge(currentNodeKey, neighbor).getWeight() + currentNodeDistance;
			node_data nei = g.getNode(neighbor);
			if (distanceToNeighbor < nei.getWeight() ) {
				LinkedList<node_data> currentNeighborPath = new LinkedList<>(currentNodePath);
				currentNeighborPath.add(nei);
				shortestPathMap.put(nei.getKey(), currentNeighborPath);
				nei.setWeight(distanceToNeighbor);
				nodesMap.put(nei.getKey(), nei);
			}
		}
	}

	return shortestPathMap.get(dest);
}

	@Override
	public boolean save(String file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(g);
		//System.out.println(json);
		try {

			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(json);
			pw.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean load(String file) {
			try
			{
				GsonBuilder builder = new GsonBuilder();
				builder.registerTypeAdapter(DWGraph_DS.class, new json_to_graph());
				Gson gson = builder.create();
				FileReader reader = new FileReader(file);
				g= gson.fromJson(reader, DWGraph_DS.class);
				return true;
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return false;
	}
	
	}