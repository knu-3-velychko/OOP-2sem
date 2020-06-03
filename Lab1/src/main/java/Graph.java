import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;

    public Graph(Path path) {
        this.vertices=new ArrayList<>();
        this.edges=new ArrayList<>();
        try {
            String jsonString = Files.readString(path);
            createFromJson(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createFromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        HashMap<Integer, Vertex> idToVertex = new HashMap<>();
        JSONArray jsonVertices = jsonObject.getJSONArray("vertices");
        for (int i = 0; i < jsonVertices.length(); ++i) {
            JSONObject jsonV = jsonVertices.getJSONObject(i);
            int id = jsonV.getInt("id");
            Vertex v = new Vertex(i, jsonV.getInt("x"), jsonV.getInt("y"), jsonV.getInt("z"), Integer.toString(id));
            idToVertex.put(id, v);
            vertices.add(v);
        }

        JSONArray jsonEdges = jsonObject.getJSONArray("edges");
        for (int i = 0; i < jsonEdges.length(); ++i) {
            JSONObject jsonE = jsonEdges.getJSONObject(i);
            int fromId = jsonE.getInt("from"), toId = jsonE.getInt("to");
            Edge e = new Edge(Integer.toString(i << 1), idToVertex.get(fromId), idToVertex.get(toId));
            edges.add(e);
            e = new Edge(Integer.toString(i << 1 | 1), idToVertex.get(toId), idToVertex.get(fromId));
            edges.add(e);
        }
    }

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo) {
        Edge lane = new Edge(laneId, vertices.get(sourceLocNo), vertices.get(destLocNo));
        edges.add(lane);
        lane = new Edge(laneId, vertices.get(destLocNo), vertices.get(sourceLocNo));
        edges.add(lane);
    }
}
