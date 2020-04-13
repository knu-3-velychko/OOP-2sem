package org.example.algorithm;

public class Edge {
    private final Vertex from, to;
    private final int id;
    private final int weight;

    public Edge(int id, Vertex from, Vertex to) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.weight = from.getDistance(to);
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
