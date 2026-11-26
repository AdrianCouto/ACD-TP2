import algoritmos.BFS;
import classesUML.AdjListGraph;
import classesUML.AdjMatrixGraph;
import classesUML.GraphType;
import classesUML.IGraph;

public class Main {

    public static void main(String[] args) {
  
        IGraph grafo = criarGrafoExemplo(true);
        int origem = 0;

        System.out.println("==========================================");
        System.out.println("ALGORITMO EM EXECUÇÃO: Busca em Largura (BFS)");
        System.out.println("Vértice de origem: " + origem);
        System.out.println("==========================================");

        System.out.println("--- Grafo de Entrada ---");
        System.out.println(grafo.toString());

        long inicioNs = System.nanoTime();
        BFS.Resultado resultado = BFS.run(grafo, origem);
        long fimNs = System.nanoTime();

        double tempoMs = (fimNs - inicioNs) / 1_000_000.0;

        System.out.println("--- Resultado da BFS ---");
        System.out.println("Ordem de visitação: " + resultado.ordemVisita);

        System.out.println("\nVértice | Nível (distância em arestas) | Predecessor");
        for (int v = 0; v < grafo.numVertices(); v++) {
            System.out.printf("   %d    |               %d                |      %d%n",
                    v, resultado.nivel[v], resultado.predecessor[v]);
        }

        System.out.printf("%nTempo de execução (BFS): %.4f ms (%d ns)%n",
                tempoMs, (fimNs - inicioNs));
    }

    private static IGraph criarGrafoExemplo(boolean usarLista) {
        int numVertices = 6;
        GraphType tipo = GraphType.DIRECTED;

        IGraph g = usarLista
                ? new AdjListGraph(numVertices, tipo)
                : new AdjMatrixGraph(numVertices, tipo);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 5);

        return g;
    }
}