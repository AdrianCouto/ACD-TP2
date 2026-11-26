package tipoGrafo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdjListGraph implements IGraph {
    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private final List<List<Edge>> adj;
    private final GraphType type;

    public AdjListGraph(int numVertices, GraphType type) {
        if (numVertices < 0) {
            throw new IllegalArgumentException("O número de vértices não pode ser negativo.");
        }
        if (type == null) {
            throw new IllegalArgumentException("O tipo do grafo não pode ser nulo.");
        }
        this.type = type;
        this.vertices = new ArrayList<>(numVertices);
        this.adj = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.vertices.add(new Vertex(i));
            this.adj.add(new ArrayList<>());
        }
        this.edges = new ArrayList<>();
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
        adj.get(u).add(edge);
        edges.add(edge);

        if (type == GraphType.UNDIRECTED && u != v) {
            adj.get(v).add(new Edge(vertices.get(v), vertices.get(u), weight));
        }
    }

    @Override
    public Edge getEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        for (Edge e : adj.get(u)) {
            if (e.getV().getId() == v) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Edge> adj(int u) {
        validateVertex(u);
        return Collections.unmodifiableList(adj.get(u));
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
        sb.append("Lista de Adjacência (").append(type).append("):\n");
        for (int i = 0; i < numVertices(); i++) {
            sb.append(i).append(": ");
            for (Edge e : adj.get(i)) {
                sb.append("[").append(e.getV().getId()).append(" (w=").append(e.getWeight()).append(")] ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}