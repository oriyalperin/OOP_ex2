package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;




public class DWGraph_DS implements directed_weighted_graph {
	private HashMap<Integer,node_data> Nodes= new HashMap(); //HashMap to all nodes

	//all the edges: from node(key) to (value :node(key), and actual edge between them )
	private HashMap<Integer,HashMap<Integer,edge_data>> Edges= new HashMap();

	//each node(key) has list(value) of the nodes witch are the source of the edge(and not the destination like in Edges HashMap
	private HashMap<Integer,ArrayList<Integer> > successors=new HashMap<>();
	private	int edgeCount;//how many edges
	private int ModeCount;//how many operations on the graph
	private static int keyCount=0;


	/**
	 * returns the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */

	@Override
	public node_data getNode(int key) {
		return Nodes.get(key); //get the node with this Key
	}

	/**
	 * returns the data of the edge (src,dest), null if none.
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		if(Edges!=null&&Edges.containsKey(src)&&Edges.get(src).containsKey(dest)) {
			return Edges.get(src).get(dest);// we need to ensure if this edge is real , if yes return the edge
		}
		return null;
	}

	/**
	 * adds a new node to the graph with the given node_data.
	 * Note: this method should run in O(1) time.
	 * @param n
	 */
	@Override
	public void addNode(node_data n) {
			if(n!=null) {
				this.Nodes.put(n.getKey(), n);//put in the HashMap (nodes)
				HashMap<Integer,edge_data>ni=new HashMap<>();//and create a new HashMap on HashMap edges for him
				this.Edges.put(n.getKey(),ni);
				this.successors.put(n.getKey(),new ArrayList<>());//put in the HashMap (successors)
				this.ModeCount++;//up the operations on the graph
			}
	}


	@Override
	public void connect(int src, int dest, double w) {
		if(src==dest) { //if src==dest so we need to out
			return;
		}
		
		if(getEdge(src,dest)==null) {//if this edge doesn't exist
			edge_data edgeSrcDest = new EdgeData(src,dest,w);//create a new edge
			this.Edges.get(src).put(dest, edgeSrcDest);//put in the map edges
			this.successors.get(dest).add(src);//put the nodes in successors map
			this.ModeCount++;//up the operations on the graph
			this.edgeCount++;// up the edges on the graph

		}
		
	/*	else{//if this edge is exists we need to replace the weight
			(EdgeData)this.Edges.get(src).get(dest).se
			this.ModeCount++;
		}*/
	}

	@Override
	public Collection<node_data> getV() {
		return Nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return Edges.get(node_id).values();

	}

	public HashMap<Integer,edge_data> getNi(int node_id) {
		return Edges.get(node_id);

	}
	@Override
	public node_data removeNode(int key) {
		if (!this.Nodes.containsKey(key)) { 
			return null;
		}
		node_data nodeR = this.Nodes.get(key);

		this.Nodes.remove(key);//remove the node from nodes map
		this.edgeCount-=Edges.get(key).size(); //sub the number edges(from the node we want to remove) from edges size
		edgeCount-=successors.get(key).size();

		for(Integer KeysOfNode: this.successors.get(key)) {
			//remove from the edges map all the edges witch the node that we want to remove is the destination of them
				Edges.get(KeysOfNode).remove(key);
				edgeCount--;//sub 1 from the number of edges
			}
		successors.remove(key);//remove the node from the successors map
		this.Edges.remove(key);
		this.ModeCount++;
		return nodeR;
		
		
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(getEdge(src,dest)==null) {
			return null;
		}
		else {
			edge_data RemoveEdge = this.Edges.get(src).remove(dest);
			this.edgeCount--;
			this.ModeCount++;

			//edge_data RemoveEdge = null;
			return RemoveEdge;
		}
	}

	@Override
	public int nodeSize() {
		return this.Nodes.size();
	}

	@Override
	public int edgeSize() {
		return this.edgeCount;
	}

	@Override
	public int getMC() {
		return this.ModeCount;
	}



	
	public static class NodeData implements node_data, Serializable {
		private int id;
		private geo_location pos;
		private int tag;
		private double weight=Double.MAX_VALUE;
		private String Info="";


		public NodeData(int id,int tag,double w,String i)
		{
			this.id=id;

			this.Info=i;
			this.tag=tag;
		}
		public NodeData()
		{
			id=keyCount++;

		}
		public NodeData(int key)
		{
			id=key;

		}
		public NodeData(int id,geo_location pos)
		{
			this.id=id;
			this.pos=pos;


		}


		@Override
		public int getKey() {
			return this.id;
		}

		@Override
		public geo_location getLocation() {
			// TODO Auto-generated method stub
			return  pos;
		}

		@Override
		public void setLocation(geo_location p) {
			this.pos = p;
		}

		@Override
		public double getWeight() {
			return this.weight;
		}

		@Override
		public void setWeight(double w) {
			this.weight=w;
			
		}

		@Override
		public String getInfo() {
			return this.Info;
		}

		@Override
		public void setInfo(String s) {
			this.Info=s;
			
		}

		@Override
		public int getTag() {
			return this.tag;
		}

		@Override
		public void setTag(int t) {
			this.tag=t;
			
		}



		public String toString() {
			return "id: "+id;
		}
	}
	
		public static class EdgeData implements edge_data{
			private int src;
			private int dest;
			private String Info;
			private int tag=-1;
			private double w;
			
			public EdgeData(int src, int dest, double w) {
				this.src=src;
				this.dest=dest;
				this.w=w;
			}
			public EdgeData(int src, int dest, double w,String info) {
				this.src=src;
				this.dest=dest;
				this.w=w;
				this.Info=info;
			}
			
			public EdgeData(EdgeData other) {
				this.src=other.src;
				this.dest=other.dest;
				this.w=other.w;
			}
			@Override
			public int getSrc() {
				return this.src;
			}

			@Override
			public int getDest() {
				return this.dest;
			}

			@Override
			public double getWeight() {
				return this.w;
			}

			public void setW(double w) {
				this.w = w;
			}

			@Override
			public String getInfo() {
				return this.Info;
			}

			@Override
			public void setInfo(String s) {
				this.Info=s;
			}

			@Override
			public int getTag() {
				return this.tag;
			}

			@Override
			public void setTag(int t) {
				this.tag=t;
			}
			
		}
	
	
}
