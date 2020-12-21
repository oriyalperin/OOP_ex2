package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 *
 */
public class Arena {
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1;
	private directed_weighted_graph _gg; // the graph og the game
	private List<CL_Agent> _agents; //
	private List<CL_Pokemon> _pokemons;
	private List<String> _info;
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	public Arena() {;
		_info = new ArrayList<>();
	}



	public void setPokemons(List<CL_Pokemon> f) {
		this._pokemons = f;
	}//set the pokemons list
	public void setAgents(List<CL_Agent> f) {
		this._agents = f;
	}//set the agents list
	public void setGraph(directed_weighted_graph g) {this._gg =g;}//set the graph of the game

	public List<CL_Agent> getAgents() {return _agents;} //get the agents list
	public List<CL_Pokemon> getPokemons() {return _pokemons;}//get the pokemons list
	
	public directed_weighted_graph getGraph() {
		return _gg;
	}//get the graph of the game
	public List<String> get_info() {
		return _info;
	}//get the info of the game


	public static List<CL_Agent> getAgents(String aa, directed_weighted_graph gg) { //convert the json file to agents list
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
		try {
			JSONObject ttt = new JSONObject(aa);
			JSONArray ags = ttt.getJSONArray("Agents");
			for(int i=0;i<ags.length();i++) {
				CL_Agent c = new CL_Agent(gg,0);
				c.update(ags.get(i).toString());
				ans.add(c);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ans;
	}
	/**
	 * convert the json file to pokemons list*/
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try {
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons");
			for(int i=0;i<ags.length();i++) {
				JSONObject pp = ags.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);

				ans.add(f);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans;
	}

	/**update the current edge for each pokemon in the game*/
	public void updateAllEdges(List<CL_Pokemon> poks) {


		Iterator<node_data> itr = _gg.getV().iterator();
		while (itr.hasNext()) {
			node_data v = itr.next();
			Iterator<edge_data> iter = _gg.getE(v.getKey()).iterator();
			while (iter.hasNext()) {
				edge_data e = iter.next();
				for (int i = 0; i < poks.size(); i++) {
					CL_Pokemon p = poks.get(i);
					boolean f = Arena.updateE(p, e, _gg);
					if (f) {
						p.set_edge(e);

					}
				}
			}

		}
	}


	private static boolean updateE(CL_Pokemon p,edge_data e, directed_weighted_graph g){ //update the current edge for specific pokemon
		return  isOnEdge(p.getLocation(), e,p.getType(), g);
	}

	/**
	 * check if the pokemon on edge by src and dest geo location
	 * */
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest ) {

		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>d1-EPS2) {ans = true;}
		return ans;
	}

	/**get the geo location of src & dest of edge*/
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}

	/**check if pokemon is on specific edge*/
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src) {return false;} //according the game settings
		if(type>0 && src>dest) {return false;}
		return isOnEdge(p,src, dest, g);
	}

	/**
	 * get the 2D range of the graph*/
	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}

	/**
	 * get the range
	 * */
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

}
