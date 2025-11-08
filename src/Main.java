import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║        DIJKSTRA - MODO INTERATIVO          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Grafo de exemplo
        int numVertices = 6;
        int[][] grafo = {
                {0, 4, 2, 0, 0, 0},
                {4, 0, 1, 5, 0, 0},
                {2, 1, 0, 8, 10, 0},
                {0, 5, 8, 0, 2, 6},
                {0, 0, 10, 2, 0, 3},
                {0, 0, 0, 6, 3, 0}
        };

        System.out.println("GRAFO (Matriz de Adjacência):");
        System.out.println("   (0 = sem conexão)\n");
        System.out.print("     ");
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%3d ", i);
        }
        System.out.println();
        System.out.println("   " + "-".repeat(numVertices * 4 + 3));

        for (int i = 0; i < numVertices; i++) {
            System.out.printf(" %d | ", i);
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%3d ", grafo[i][j]);
            }
            System.out.println();
        }

        // Escolhe origem
        System.out.print("\nDigite o vértice de ORIGEM (0 a " + (numVertices-1) + "): ");
        int origem = scanner.nextInt();
        scanner.nextLine(); // Limpa buffer

        if (origem < 0 || origem >= numVertices) {
            System.out.println(" Vértice inválido! Usando 0 como padrão.");
            origem = 0;
        }

        // Executa Dijkstra interativo
        Dijkstra.dijkstraInterativo(grafo, origem, numVertices);

        // Opção de executar novamente
        System.out.print("\n\n Quer executar com outro vértice de origem? (s/n): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            main(args);
        } else {
            System.out.println("\n Valeu Irmão, Tamo Junto :)");
            scanner.close();
        }
    }
}
