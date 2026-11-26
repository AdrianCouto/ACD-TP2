package algoritmos;

import classesUML.Edge;
import classesUML.IGraph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class BFS {

    public static class Resultado{
        public final List<Integer> ordemVisita;
        public final int[] predecessor;
        public final int[] nivel;

        public Resultado(List<Integer> ordemVisita, int[] predecessor, int[] nivel) {
            this.ordemVisita = ordemVisita;
            this.predecessor = predecessor;
            this.nivel = nivel;
        }
    }

    public static Resultado run(IGraph graph, int origem) {
        int n = graph.numVertices();
        if (origem < 0 || origem >= n) {
            throw new IndexOutOfBoundsException("Vértice de origem inválido");
        }

        boolean[] visitado = new boolean[n];
        int[] predecessor = new int[n];
        int[] nivel = new int[n];
        Arrays.fill(predecessor, -1);
        Arrays.fill(nivel, -1);

        List<Integer> order = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();

        visitado[origem] = true;
        nivel[origem] = 0;
        queue.add(origem);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);

            for (Edge e : graph.adj(u)) {
                int v = e.getV().getId();
                if (!visitado[v]) {
                    visitado[v] = true;
                    predecessor[v] = u;
                    nivel[v] = nivel[u] + 1;
                    queue.add(v);
                }
            }
        }

        return new Resultado(order, predecessor, nivel);
    }
}