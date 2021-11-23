# EX2 - Pokemon Game
## Oriya Alperin | Dvir Hackmon
This project has 2 parts:
1. Directed weighted graph and its algorithms
2. Pokemon game that runs on the graph and uses its algorithms

### first part
In this part, we have developed a directed weighted graph consisting of nodes (vertices) and edges.
**classes**
* **NodeData**
This class represents a single node (by implementing the interface 'node_data'). Node is consisting of id and position, and some extra information about it.
* **EdgeData**
This class represents a single Edge (by implementing the interface 'edge_data'). Edge is consisting of two nodes- source node and destination node (because the graph is directed), and weight (because the graph is weighted), and some extra information about it.
* **DWgraph_DS**
This class represents a directed weighted graph (by implementing the interface 'directed_weighted_graph'). The graph consists of nodes list and edges list.
* **DWGraph_Algo**
This class consists of algorithms that are run on a directed weighted graph (by implementing the interface 'dw_graph_algorithms'), such as DFS- to determine if the graph is strongly connected, Dijkstra- to find the shortest path. In addition, there are JSON methods - for saving and loading graphs, and init & copy methods.

### second part
This part is focusing on the Pokemon game, whose goal is for the "agents" to eat as many Pokemons as possible in as little time and steps as possible.
The game runs on a directed-weighted graph. The agents were programmed with an algorithm (which uses Dijkstra) to find the shortest path to achieving a high score.
**classes**
* **json_to_graph**
Parses a JSON file graph "to DWGraph_DS" class graph.
* **json_to_graphGame**
Parses a json file graph to "directed_weighted_graph" class graph.
* **connected**
In "isConnected" function we need to store extra data about the vertices.
This class saves this data.
* **CL_Pokemon**
Represents the Pokemon object.
Each Pokemon has a position, the edge it is on, and value (points it's worth).
The Pokemon main use in the game is its position. The position will help us to find the closest agent to "eat" the Pokemon. 
* **CL_Agent**
Represents Agent object.
Each Agent has a position, speed level(which can grow), and value (score).
The Agent's main use in the game is its position and speed. Those will help us to calculate the distances between it and the Pokemons,
and then to find the ideal Pokemon that it should "eat" to earn points as much as possible in as little time and steps as possible.
* **Arena**
Most Arena class functions are used for converting the outputs (JSON strings) that the game server is returning and parsing them to objects (like Pokemons list, Agents list, the graph itself..)
In addition, updates for each Pokemon the current edge it is on.
* **MyFrame**
Used for GUI.
The game objects in visual elements: graph, Pokemons, Agents.
Updates once at a time the Agent's data changes  (value and speed).
* **Ex2**
Used for running the game: visual and logical.
•Login to the game and choose a level (1-22)
•Algorithm that matches between Agent and Pokemon.
 The algorithm idea:
  1. calculate the distances of the shortest paths between each agent and pokemon (account for the agent speed).
  2. match between the shortest distance agent and the Pokemon of all the (agent, pokemon) couples.
  3. for each agent will be saved its ideal path to its Pokemon target.
  4. after all agents have been matched, they need to move their next vertex in their current path.
  5. in each "move" of the game, all 1-3 steps will happen again to make sure that if one agent is available now and closer to a Pokemon that other agent matched to it in the last "move", the available agent will go to this pokemon, and the other agent will find another pokemon to go for.
