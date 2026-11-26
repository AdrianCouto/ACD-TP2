import tipoGrafo.AdjListGraph;
import tipoGrafo.AdjMatrixGraph;
import tipoGrafo.GraphType;
import tipoGrafo.IGraph;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    public static IGraph carregaArq(String nomeArq, boolean useMatrix) throws IOException{
        IGraph grafo = null;

        try(BufferedReader br = new BufferedReader(new FileReader(nomeArq))){
            String linha;
            
            // ignora linhas de comentários e vazias
            while((linha = br.readLine()) != null){
                linha = linha.trim();
                
                if(linha.isEmpty() || linha.startsWith("c")){
                    continue;
                }
                
                // separa por espaço ou tab
                String[] partes = linha.split("\\s+");

                if(partes[0].equals("p")){
                    int numVertices = Integer.parseInt(partes[2]);
                    if(useMatrix == true){
                        grafo = new AdjMatrixGraph(numVertices, GraphType.DIRECTED);
                    }
                    else{
                        grafo = new AdjListGraph(numVertices, GraphType.DIRECTED);
                    }
                }else if(partes[0].equals("a")){
                    if(grafo == null){
                        throw new IllegalStateException("O grafo não foi inicializado antes de adicionar arestas.");
                    }

                    int u = Integer.parseInt(partes[1])-1;
                    int v = Integer.parseInt(partes[2])-1;
                    double peso = Double.parseDouble(partes[3]);

                    grafo.addEdge(u, v, peso);
                }
            }
        }
        
        if(grafo == null){
            throw new IllegalArgumentException("[ ERRO ] Arquivo inválido: linha não encontrada.");
        }

        return grafo;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Digite o caminho do arquivo do grafo: ");
        String arq = scan.nextLine();

        try{
            IGraph grafo = Parser.carregaArq(arq, true);

            System.out.println("Grafo carregado com sucesso!");
            System.out.println("Número de vértices: " + grafo.numVertices());
            System.out.println("Número de arestas: " + grafo.numEdges());
            System.out.println("Tipo do grafo: " + grafo.type());
            System.out.println("Lista de vértices: " + grafo.vertices());
            System.out.println("É matriz de adjacência? " + String.valueOf(grafo instanceof AdjMatrixGraph).substring(0, 1).toUpperCase());
            
        }catch(IOException e){
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }finally{
            scan.close();
        }
    }
}