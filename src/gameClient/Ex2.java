package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;

public class Ex2 implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	private static HashMap<Integer,LinkedList<node_data>> path=new HashMap<>();
	private static PriorityQueue<double[]>sp=new PriorityQueue<>(Comparator.comparingDouble(o -> (o[2])));
	private static HashMap<Integer,Integer>ageToPok=new HashMap<>();
	private static double ms=100;
	private static directed_weighted_graph gg;
	private static game_service game;


	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
		//Thread time=new Thread(showTime(game));
	}


	/**
	 * run the game
	 * */
	@Override
	public void run() {

			loginFrame f=new loginFrame();
			f.paintComponents(f.getGraphics());
			while(f.isVisible())
				{
					f.listen();
				if(f.getTz()!="" &&f.getL()!=-1) {
					f.setVisible(false);
				}
				}
			String id=f.getTz();
			int scenario_num = f.getL();

			game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
			game.login(Integer.parseInt(id));
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(directed_weighted_graph.class, new json_to_graphGame());
			Gson gson = builder.create();
			gg = gson.fromJson(game.getGraph(), directed_weighted_graph.class);
			init(game);
			DWGraph_Algo ga = new DWGraph_Algo();
			ga.init(gg);//now we can do algo on the game  graph

			game.startGame();
			_win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
			int ind = 0;

/**
 * while the game is running promote the agents to the next node in their path to ideal pokemon
 * */
			while (game.isRunning()) {

				moveAgents(game); //promotes the agents to their next destination

				try {
					{
						if (ind % 1 == 0) {

							_win.repaint();

						}

						Thread.sleep((long) ms);
						ind++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String res = game.toString();

			System.out.println(res);

			System.exit(0);
		}



	/**
	 * moves each agent to the next node in its path to its closes pokemon according its speed
	 * @param game
	 *
	 * */


	private static void moveAgents(game_service game) {
		String lg = game.move(); //move each agent to its next node destination
		List<CL_Agent> age = Arena.getAgents(lg, gg); //get the updated details about the agents from the server
		_ar.setAgents(age); //update the agents in the Arena
		String fs = game.getPokemons(); //get the updated details about the pokemons from the server
		List<CL_Pokemon> poks = Arena.json2Pokemons(fs);//update the pokemons in the Arena
		_ar.setPokemons(poks);
		DWGraph_Algo ga = new DWGraph_Algo();
		ga.init(gg); //now we can do algorithms from DWGraph_Algo on the game graph

		match(ga, poks, age); //computes the distance between each agent and each pokemon and puts in the priority queue(sp)


		int count = 0;
		while (!sp.isEmpty() && count < age.size()) {//pass the priority queue
			double[] arr = sp.poll();// gets the first couple and its distance from thr queue
			int id = (int) arr[1];// in the 1st place- the agent id
			int idPok = (int) arr[0];//in the 0st place- the pokemon id (its index on poks list)
			CL_Pokemon p = poks.get(idPok); //get the actual pokemon
			CL_Agent a = age.get(id); //get the actual agent
			if (!p.isAgent() && !a.isPok()) {//if we have not yet matched the pokemon or the agent in this move

				int src = age.get(id).getSrcNode();// the src node of the edge that the agent is on it
				int dest;
				node_data lastNode;
				dest = p.get_edge().getSrc();//the src node of the edge that the pokemon is on it
				lastNode = gg.getNode(p.get_edge().getDest());//the destination node of the edge that the pokemon is on it
				LinkedList<node_data> track = new LinkedList<>(); //track-the path from the agent to the pokemon
				ageToPok.put(id, idPok);// puts in the ageToPok Hashmap- the agent id(key), and the pokemon id(value)
				if (src != dest)//if the agent is on the src node of the pokemon
				{
					track = (LinkedList<node_data>) ga.shortestPath(src, dest);//the track will be the shortestPath between them

				}
				track.addLast(lastNode);//add to the track the last node in order to the agent will "eat" the pokemon
				path.put(id, track);//add to the path HashMap the agent id(key) and the track to the pokemon(value)
				count++; //count how match agents are taken
				p.setAgent(true);//this pokemon is taken for this move from now
				a.setPok(true);//this agent is taken for this move from now
			}
		}
		sp.clear();//we don't need this queue anymore
		nextNode(game, poks, age); //promote each agent to its next destination

	}


	/**
	 * computes the distance of the shortest path between each agent and each pokemon in the game
	 * puts all the couples of (pokemon,agent) in the priority queue (sp)
	 * the shortest distance couple will be in the first place in the queue
	 * */
	private static void match(dw_graph_algorithms ga,List<CL_Pokemon> poks,List<CL_Agent> age)
	{
		_ar.updateAllEdges(poks);//update the for each pokemon the current edge that is on it
		for (int i = 0; i < poks.size(); i++) {
			CL_Pokemon p = poks.get(i);
			for (int j = 0; j < age.size(); j++) {
				CL_Agent a = age.get(j);
					int dest;
					double[] pokAge = new double[3];
					pokAge[0] = i; //in the 0st place- puts the pokemon id(its index on poks list)
					pokAge[1] = a.getID();//in the 1st place- puts the agent id
					dest = p.get_edge().getSrc(); //will be the src of the edge that the pokemon is on it
					if (a.getSrcNode() == dest)//if the agent is on the src node of the pokemon
						pokAge[2] =a.getLocation().distance(p.getLocation())/a.getSpeed();//just computes the distance between them and divide in the agent speed
					else {
						double srcToPos= gg.getNode(a.getSrcNode()).getLocation().distance(a.getLocation());
						double srcToPok=gg.getNode(p.get_edge().getSrc()).getLocation().distance(p.getLocation());
						Double dist = ga.shortestPathDist(a.getSrcNode(), dest)-srcToPos+srcToPok;//computes the distance shortest path between them
						pokAge[2] = dist/a.getSpeed(); //divide it in the agent speed
					}

					//pokAge for each couple=: {pokemon id, agent id, the distance between them}
					sp.add(pokAge);//adds the couple and the distance to the priority queue(by the lowest distance)
			}
		}
	}

	/**
	 * takes for each agent (=key) in the HashMap (path) the next node in its list (=value)
	 * computes the ideal time to wait to do the next move in the game
	 * @param game
	 * @param poks
	 * @param age
	 * */
	private static void nextNode(game_service game,List<CL_Pokemon> poks,List<CL_Agent> age) {
		double min=Integer.MAX_VALUE;
		double sum=0;
		double avg=Integer.MAX_VALUE;
		int count=0;
		for (int j = 0; j < age.size(); j++) { // pass the agents

				CL_Agent a = age.get(j); //get the actual agent
			    int id = a.getID(); //get the agent id (same like j index)
				if (!path.get(j).isEmpty()) { // if its path isn't empty
					int nxt = path.get(id).removeFirst().getKey();//remove and get the next node from the agent path list
					a.setNextNode(nxt); //set the next node to be it
					a.computeTime(100, poks, ageToPok); //computes the exact time we should wait in order this agent will "eat" the pokemon

					if (path.get(j).isEmpty()) { //if after it the agent have no path anymore= the next step is to it the pokemon
						if (a.getTime() < min) { // get the time we have computed and check if its lower than the min
							min = a.getTime(); // if its lower, now the min will be the time
						}
					}
					else { //if the agent has more nodes in its path after the removing
						count++; //up the counter
						sum += a.getTime(); //add the agent time to the sum
						avg = sum / count;

					}

					game.chooseNextEdge(id, nxt);// let the game know what the next destination node of the agent
					System.out.println("Agent: " + id + ", val: " + a.getValue() + " turned from node: " +a.getSrcNode()+" to node: "+ nxt + " in speed: " + a.getSpeed());

				}
				/*if(count!=0) // if none of the agents needed to eat in its next step
					avg = sum / count; // computes the average time of all the agents we counted*/
				ms = Math.min(min, avg);//update the time that we need to wait to the next move to be the minimum


		}
	}

	/**
	 * init the game: take the elements of the game
	 * and make them visual and convert them to real objects that we can do function with/on them
	 * */
	private void init(game_service game) {
		String fs = game.getPokemons();
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame("test Ex2");
		_win.setGame(game);
		_win.setSize(1000, 700);
		_win.update(_ar);

		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			cl_fs.sort(Comparator.comparingInt(o->(int)o.get_value()));
			_ar.updateAllEdges(cl_fs);

			/**
			 * locate each agent on the edge of pokemons with the highest value for the first place
			 * */
			for(int a = 0;a<rs;a++) {
				CL_Pokemon c = cl_fs.get(a);
				int sn = c.get_edge().getSrc();
				path.put(a,new LinkedList<>());
				game.addAgent(sn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}
