import org.json.JSONStringer;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

// wrapper on Session object.
public class WebSocketSession {
    private final Session session;
    private final Graph graph;
    private final DijkstraAlgorithm dijkstra;

    public Session getSession() {
        return session;
    }

    WebSocketSession(Session session) {
        this.session = session;
        this.graph = new Graph(Paths.get("C:\\Users\\Taya\\Documents\\Projects\\OOP-2sem\\lab1\\src\\main\\resources\\graph.json"));
        this.dijkstra = new DijkstraAlgorithm(this.graph);
        sendToClient(graph);
    }


    public void makeSearch(int start, int end) {
        dijkstra.setEdges(graph);
        dijkstra.execute(graph.getVertices().get(start));
        List<Vertex> path = dijkstra.getPath(graph.getVertices().get(end));
        sendToClient(path);
    }

    void sendToClient(List<Vertex> path) {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("command").value("path");
        stringer.key("count").value(String.valueOf(path.size()));

        for (int i = 0; i < path.size(); i++) {
            stringer.key("vertex" + i).value(String.valueOf(path.get(i).getId()));
        }
        stringer.endObject();

        try {
            session.getBasicRemote().sendText(stringer.toString());
        } catch (IOException ignored) {
        }
    }

    void sendToClient(Graph graph) {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("command").value("graph");
        stringer.key("countVertex").value(String.valueOf(graph.getVertices().size()));
        for (int i = 0; i < graph.getVertices().size(); i++) {
            stringer.key("vertex" + i + 'X').value(String.valueOf(graph.getVertices().get(i).getP().getX()));
            stringer.key("vertex" + i + 'Y').value(String.valueOf(graph.getVertices().get(i).getP().getY()));
            stringer.key("vertex" + i + 'Z').value(String.valueOf(graph.getVertices().get(i).getP().getZ()));
        }
        stringer.key("countLine").value(String.valueOf(graph.getEdges().size()));
        for (int i = 0; i < graph.getEdges().size(); i++) {
            stringer.key("line" + i + 's').value(String.valueOf(graph.getEdges().get(i).getDestination().getId()));
            stringer.key("line" + i + 'e').value(String.valueOf(graph.getEdges().get(i).getSource().getId()));
        }
        stringer.endObject();
        System.out.println(stringer.toString());
        try {
            session.getBasicRemote().sendText(stringer.toString());
        } catch (IOException ignored) {
        }
    }

    void sendToClient(String command) {
        String json = new JSONStringer()
                .object()
                .key("command")
                .value(command)
                .endObject()
                .toString();
        try {
            session.getBasicRemote().sendText(json);
        } catch (IOException ignored) {
        }
    }
}
