import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {
    private final Vertex vertexFrom = new Vertex(1, 1, 1, 1, "vertex");
    private final Vertex vertexTo = new Vertex(2, 2, 2, 2, "edge");

    private final Edge edge = new Edge("1", vertexFrom, vertexTo);

    private final List<Vertex> vertexList = Arrays.asList(vertexFrom, vertexTo);
    private final List<Edge> edgeList = Arrays.asList(edge);

    private final Graph defaultGraph = new Graph(Paths.get("C:\\Users\\Taya\\Documents\\Projects\\OOP-2sem\\lab1\\src\\main\\resources\\graph.json"));
    private final Graph filledGraph = new Graph(vertexList, edgeList);


    @Test
    public void getVertexes() {
        assertNotNull(this.defaultGraph.getVertices());
        assertFalse(this.defaultGraph.getVertices().isEmpty());

        assertNotNull(this.filledGraph.getVertices());
        assertFalse(this.filledGraph.getVertices().isEmpty());
        assertEquals(vertexList.size(), filledGraph.getVertices().size());
        assertEquals(vertexFrom, filledGraph.getVertices().get(0));
        assertEquals(vertexTo, filledGraph.getVertices().get(1));
    }

    @Test
    public void getEdges() {
        assertNotNull(this.defaultGraph.getEdges());
        assertFalse(this.defaultGraph.getEdges().isEmpty());

        assertNotNull(this.filledGraph.getEdges());
        assertFalse(this.filledGraph.getEdges().isEmpty());
        assertEquals(edgeList.size(), filledGraph.getEdges().size());
        assertEquals(edge, filledGraph.getEdges().get(0));
    }
}