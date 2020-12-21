package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.edge_data;
import org.junit.jupiter.api.Test;

import api.DWGraph_DS.NodeData;

import org.junit.jupiter.api.Test;


import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DS_Test {
    @Test
    void addNodeToGraph() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	graph.addNode(firstKey);
        assertEquals(1, graph.getNode(1).getKey());
        assertEquals(1, graph.getMC());
    }

    @Test
    void addNodeToGraphTwice() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	graph.addNode(firstKey);
        graph.addNode(secondKey);
        assertEquals(graph.getNode(1).getKey(), 1);
        assertEquals(2, graph.getMC());
    }

    @Test
    void getNonExistingNode() {
    	directed_weighted_graph graph = new DWGraph_DS();
        assertNull(graph.getNode(1));
    }

    @Test
    void hasNonExistingEdge() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData();
    	NodeData secondKey = new NodeData();
    	assertEquals(null, graph.getEdge(firstKey.getKey(), secondKey.getKey()));
    }

    @Test
    void hasExistingEdge() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        equals(graph.getEdge(firstKey.getKey(), secondKey.getKey()));
    }

    @Test
    void getNonExistingEdge() {
    	directed_weighted_graph graph = new DWGraph_DS();
        assertEquals(null, graph.getEdge(1, 2));
    }

    @Test
    void getExistingEdge() {
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        assertEquals(weight, graph.getEdge(firstKey.getKey(), secondKey.getKey()).getWeight());
    }

    @Test
    void connectNodes(){
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        assertEquals(3, graph.getMC());
        assertEquals(1, graph.edgeSize());
    }

    @Test
    void connectMultipleNodes(){
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
        NodeData thirdKey = new NodeData(3);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight);
        assertEquals(6, graph.getMC());
        assertEquals(3, graph.edgeSize());
    }
    
    @Test
    void connectNodesTwice(){
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        assertEquals(3, graph.getMC());
        assertEquals(1, graph.edgeSize());
    }

    @Test
    void getConnectedEdge(){
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
        NodeData thirdKey = new NodeData(3);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight);
        Collection<edge_data> connectedNodes = graph.getE(firstKey.getKey());
        assertTrue(connectedNodes.contains(graph.getEdge(firstKey.getKey(),secondKey.getKey())));
        assertTrue(connectedNodes.contains(graph.getEdge(firstKey.getKey(),thirdKey.getKey())));
    }

    @Test
    void removeNode(){
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(3);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight+1);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight+2);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight+3);
        graph.removeNode(firstKey.getKey());
        assertNull(graph.getNode(firstKey.getKey()));
        assertEquals(1, graph.edgeSize());
        assertEquals(null,graph.getEdge(firstKey.getKey(), thirdKey.getKey()));
        assertEquals(null,graph.getEdge(firstKey.getKey(), secondKey.getKey()));
        assertEquals(weight+3,graph.getEdge(secondKey.getKey(), thirdKey.getKey()).getWeight());
    }

    @Test
    void removeEdge(){
    	directed_weighted_graph graph = new DWGraph_DS();
    	NodeData firstKey = new NodeData(1);
    	NodeData secondKey = new NodeData(2);
    	NodeData thirdKey = new NodeData(3);
        int weight = 8;
        graph.addNode(firstKey);
        graph.addNode(secondKey);
        graph.addNode(thirdKey);
        graph.connect(firstKey.getKey(), secondKey.getKey(), weight);
        graph.connect(firstKey.getKey(), thirdKey.getKey(), weight+1);
        graph.connect(secondKey.getKey(), thirdKey.getKey(), weight+2);
        graph.removeEdge(firstKey.getKey(), secondKey.getKey());
        assertEquals(2, graph.edgeSize());
        assertEquals(null,graph.getEdge(firstKey.getKey(), secondKey.getKey()));
        assertEquals(weight+1,graph.getEdge(firstKey.getKey(), thirdKey.getKey()).getWeight());
        assertEquals(weight+2,graph.getEdge(secondKey.getKey(), thirdKey.getKey()).getWeight());
    }

}
