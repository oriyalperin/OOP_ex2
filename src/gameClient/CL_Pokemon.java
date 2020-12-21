package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private boolean agent;

	/**
	 * create a new pokemon
	 * */
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}
	/**
	 * returns if there is agent that on it's way to eat this pokemon in a specific move
	 * */
	public boolean isAgent() {
		return agent;
	}
	/**
	 * set "agent" to be true if this pokemon matched to agent in this move
	 * we will never set "agent" to be false because it reset (to be false) in every move
	 * */
	public void setAgent(boolean b)
	{
		this.agent=b;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return _pos;
	}

	/**
	 * get the type of the pokemon-
	 * if it's < 0, then the src of the edge of the pokemon is lower node number
	 * if it's > 0, then the src of the edge of the pokemon is higher node number
	 * */
	public int getType() {return _type;}

	public double get_value() {
		return _value;
	}
}
