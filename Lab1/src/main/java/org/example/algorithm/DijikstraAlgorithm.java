package org.example.algorithm;

import java.util.*;

public class DijikstraAlgorithm {
    private List<Edge> edges;
    private Set<Vertex> coloredNodes = new HashSet<>();
    private Set<Vertex> nodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance = new HashMap<>();

    public DijikstraAlgorithm(Graph graph) {
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public LinkedList<Vertex> getPath(Vertex target){
        LinkedList<Vertex> path=new LinkedList<>();
        Vertex step=target;

    }
}
