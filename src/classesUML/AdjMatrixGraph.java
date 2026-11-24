package classesUML;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdjMatrixGraph implements IGraph {
    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private final Edge[][] matrix;
    private final GraphType type;

    public AdjMatrixGraph(int numVertices, GraphType type) {
        if (numVertices < 0) {
            throw new IllegalArgumentException("O número de vértices não pode ser negativo.");
        }
        if (type == null) {
            throw new IllegalArgumentException("O tipo do grafo não pode ser nulo.");
        }
        this.type = type;
        this.vertices = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.vertices.add(new Vertex(i));
        }
        this.edges = new ArrayList<>();
        this.matrix = new Edge[numVertices][numVertices];
    }

    @Override
    public GraphType type() {
        return type;
    }

    @Override
    public List<Vertex> vertices() {
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public List<Edge> edges() {
        return Collections.unmodifiableList(edges);
    }

    @Override
    public void addEdge(int u, int v) {
        addEdge(u, v, 1.0);
    }

    @Override
    public void addEdge(int u, int v, double weight) {
        validateVertex(u);
        validateVertex(v);

        Edge edge = new Edge(vertices.get(u), vertices.get(v), weight);
        matrix[u][v] = edge;
        edges.add(edge);

        if (type == GraphType.UNDIRECTED && u != v) {
            matrix[v][u] = new Edge(vertices.get(v), vertices.get(u), weight);
        }
    }

    @Override
    public Edge getEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        return matrix[u][v];
    }

    @Override
    public List<Edge> adj(int u) {
        validateVertex(u);
        List<Edge> neighbors = new ArrayList<>();
        for (int v = 0; v < vertices.size(); v++) {
            if (matrix[u][v] != null) {
                neighbors.add(matrix[u][v]);
            }
        }
        return neighbors;
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    private void validateVertex(int u) {
        if (u < 0 || u >= numVertices()) {
            throw new IndexOutOfBoundsException("Vértice inválido: " + u);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matriz de Adjacência (").append(type).append("):\n");
        for (int i = 0; i < numVertices(); i++) {
            for (int j = 0; j < numVertices(); j++) {
                sb.append(matrix[i][j] != null ? String.format("%6.1f ", matrix[i][j].getWeight()) : "   -   ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}