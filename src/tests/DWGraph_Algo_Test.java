package tests;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.node_data;
import org.junit.jupiter.api.Test;

import api.DWGraph_DS.NodeData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_Algo_Test {
    @Test
    void copyGraph() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	DWGraph_DS.NodeData firstKey = new NodeData(0);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(3);  
        double weight = 15 ;
        graph.addNode( firstKey);		
        graph.addNode(secondKey);
        graph.addNode(thirdKey);	
        graph.connect(firstKey.getKey(), secondKey.getKey(), 5);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), 8);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), 9);
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        directed_weighted_graph copy = graphAlgo.copy();
        assertEquals(3, copy.edgeSize());
        assertEquals(5, copy.getEdge(firstKey.getKey(), secondKey.getKey()).getWeight());
        assertEquals(8, copy.getEdge(firstKey.getKey(), thirdKey.getKey()).getWeight());
        assertEquals(9, copy.getEdge(secondKey.getKey(), thirdKey.getKey()).getWeight());
    }

    @Test
    void loadGraph() {

    }

    @Test
    void connectedGraph() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(3);  
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight + 1);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight + 2);
        graph.connect(thirdKey.getKey(), firstKey.getKey(), weight + 3);
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        assertTrue(graphAlgo.isConnected());
    }

    @Test
    void notConnectedGraph() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(3);  
    	NodeData FourKey = new NodeData(4);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(FourKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight + 1);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight + 2);
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        assertFalse(graphAlgo.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph graph = new DWGraph_DS();
        NodeData firstKey = new NodeData(1);
        NodeData secondKey = new NodeData(2);
        NodeData thirdKey = new NodeData(5);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight + 1);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight + 20);
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        assertEquals(weight + 1, graphAlgo.shortestPathDist(firstKey.getKey(), thirdKey.getKey()));
    }

    @Test
    void shortestPath() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(5);  
    	NodeData FourKey = new NodeData(4);
    	int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(FourKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight + 1);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight + 20);
        graph.connect(firstKey.getKey(),FourKey.getKey(),7);
        graph.connect(FourKey.getKey(),thirdKey.getKey(),1);
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        List<node_data> shortestPath = graphAlgo.shortestPath(firstKey.getKey(), thirdKey.getKey());
        node_data[] expectedPath = new node_data[2];
        expectedPath[0] = graph.getNode(FourKey.getKey());
        expectedPath[1] = graph.getNode(thirdKey.getKey());
        assertArrayEquals(expectedPath, shortestPath.toArray());
    }


    @Test
    void saveAndLoad() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(5);  
    	NodeData FourKey = new NodeData(4);
    	int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.addNode(FourKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight + 1);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight + 20);
        DWGraph_Algo graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        graphAlgo.save("my_file.json");
        DWGraph_Algo graphAlgo2 = new DWGraph_Algo();
        graphAlgo2.load("my_file.json");
        directed_weighted_graph copy = graphAlgo2.getGraph();
        assertEquals(4, copy.nodeSize());
        assertEquals(3, copy.edgeSize());
        assertEquals(weight, copy.getEdge(firstKey.getKey(), secondKey.getKey()).getWeight());
        assertEquals(weight + 1, copy.getEdge(firstKey.getKey(), thirdKey.getKey()).getWeight());
        assertEquals(weight + 20, copy.getEdge(secondKey.getKey(), thirdKey.getKey()).getWeight());
    }
}
