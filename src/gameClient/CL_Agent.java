package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.awt.font.ImageGraphicAttribute;
import java.util.HashMap;
import java.util.List;

public class CL_Agent {
		public static final double EPS = 0.00002;
		private int _id;
		private geo_location _pos;
		private double _speed;
		private edge_data _curr_edge;
		private node_data _curr_node;
		private directed_weighted_graph _gg; //the graph of the game that the agent is play on
		private double time; //the exact time the agent need to wait in order to move the next node in it's path
		private double _value; //the amount of point this agent earned
		private boolean pok; //if this agent found a pokemon to go to in this move

		/**
		 *constructor for agent
		 * reset the graph that the agent belong to
		 * reset the agent money
		 * reset the start node that the agent is on it
		 *
		 */
		public CL_Agent(directed_weighted_graph g, int start_node) {
			_gg = g;
			_value=0;
			_curr_node = _gg.getNode(start_node);
			_pos = _curr_node.getLocation();
			_id = -1;
			_speed=0;
		}

		/**
		 * returns if the agent is already matched to pokemon in the move
		 *
		 * */
	public boolean isPok() {
		return pok;
	}

	/**
	 * set "pok" to be true if the agent matched to pokemon in the move
	 * we will never set "pok" to be false because it reset (to be false) in every move
	 * */
	public void setPok(boolean pok) {
		this.pok = pok;
	}


	/**
	 * update agent details according the changes in the game
	 * (from json string to CL_Agent fields)
	 * */
	public void update(String json) {
			JSONObject line;
			try {
				line = new JSONObject(json);
				JSONObject ttt = line.getJSONObject("Agent");
				int id = ttt.getInt("id");
				if(id==this.getID() || this.getID() == -1) {
					if(this.getID() == -1) {_id = id;}
					double speed = ttt.getDouble("speed");
					String p = ttt.getString("pos");
					Point3D pp = new Point3D(p);
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					double value = ttt.getDouble("value");
					this._pos = pp;
					this.setCurrNode(src);
					this._speed=speed;
					this.setNextNode(dest);
					this._value=value;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * gets the last node that the agent was in
		 * */
		public int getSrcNode() {return this._curr_node.getKey();}

		/**
		 * convert the CL_Agent fields to a json file
		 * */
		public String toJSON() {
			int d = this.getNextNode();
			String ans = "{\"Agent\":{"
					+ "\"id\":"+this._id+","
					+ "\"value\":"+this._value+","
					+ "\"src\":"+this._curr_node.getKey()+","
					+ "\"dest\":"+d+","
					+ "\"speed\":"+this.getSpeed()+","
					+ "\"pos\":\""+_pos.toString()+"\""
					+ "}"
					+ "}";
			return ans;	
		}


		//private void setMoney(double v) {_value = v;}

	/**
	 * sets the current edge to be the edge between current node and dest
	 * @param dest
	 * */
		public boolean setNextNode(int dest) {
			boolean ans = false;
			int src = this._curr_node.getKey();
			this._curr_edge = _gg.getEdge(src, dest);
			if(_curr_edge!=null) {
				ans=true;
			}
			else {_curr_edge = null;}
			return ans;
		}

		/**
		 * sets the current node to be src
		 * @param src
		 * */
		private void setCurrNode(int src) {
			this._curr_node = _gg.getNode(src);
		}


		public String toString() {
			return toJSON();
		}

		public int getID() {
			return this._id;
		}
	
		public geo_location getLocation() {
			return _pos;
		}


		public double getValue() {
			return this._value;
		}


		public int getNextNode() {
			int ans;
			if(this._curr_edge==null) {
				ans = -1;}
			else {
				ans = this._curr_edge.getDest();
			}
			return ans;
		}

		public double getSpeed() {
			return this._speed;
		}


		/**
		 * computes for the agent the exact time to wait for the next move
		 * according the agent speed and the distance between it to the next node in it's path
		 * @param poks
		 * @param ageToPok
		 * */
		public void computeTime(long ddtt, List<CL_Pokemon> poks, HashMap<Integer,Integer> ageToPok) {
		double ddt = ddtt;
		if (this._curr_edge != null) {
			double w = _curr_edge.getWeight();
			geo_location dest = _gg.getNode(_curr_edge.getDest()).getLocation();
			geo_location src = _gg.getNode(_curr_edge.getSrc()).getLocation();
			double de = src.distance(dest);
			double dist = _pos.distance(dest);
			CL_Pokemon p = poks.get( ageToPok.get(_id));
			if (p.get_edge().getSrc() == _curr_edge.getSrc() && p.get_edge().getDest() == _curr_edge.getDest()) {

					dist = this._pos.distance(p.getLocation());
			}


			double norm = dist / de;
			double dt = w * norm / this.getSpeed();
			ddt =  (1000.0 * dt);

		}
		this.setTime(ddt);
	}

		public double getTime() {
			return time;
		}
		public void setTime(double _sg_dt) {
			this.time = _sg_dt;
		}
	}
