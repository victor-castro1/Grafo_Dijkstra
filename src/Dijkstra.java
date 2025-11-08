import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dijkstra {

    private static Scanner scanner = new Scanner(System.in);

    public static void dijkstraInterativo(int[][] grafo, int origem, int numVertices) {
        System.out.println("\n╔══════════════════════════════════════════");
        System.out.println("║   ALGORITMO DE DIJKSTRA - MODO INTERATIVO ║");
        System.out.println("════════════════════════════════════════════\n");

        // Inicialização
        int[] distancias = new int[numVertices];
        boolean[] visitados = new boolean[numVertices];
        int[] predecessor = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distancias[i] = Integer.MAX_VALUE;
            predecessor[i] = -1;
        }
        distancias[origem] = 0;

        System.out.println(" INICIALIZAÇÃO:");
        System.out.println("   Origem: Vértice " + origem);
        System.out.println("   Distâncias iniciais:");
        mostrarEstado(distancias, visitados, numVertices);
        pausar();

        // Processa cada vértice
        for (int iteracao = 0; iteracao < numVertices; iteracao++) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println(" ITERAÇÃO " + (iteracao + 1) + " de " + numVertices);
            System.out.println("=".repeat(60));

            // Encontra o vértice não visitado com menor distância
            int u = -1;
            int minDist = Integer.MAX_VALUE;

            System.out.println("\n Procurando o vértice NÃO VISITADO com MENOR distância...");
            for (int v = 0; v < numVertices; v++) {
                if (!visitados[v]) {
                    String dist = (distancias[v] == Integer.MAX_VALUE) ? "∞" : String.valueOf(distancias[v]);
                    System.out.println("   Vértice " + v + ": distância = " + dist +
                            (distancias[v] < minDist ? " ← CANDIDATO" : ""));

                    if (distancias[v] < minDist) {
                        minDist = distancias[v];
                        u = v;
                    }
                }
            }

            if (u == -1) {
                System.out.println("\n Não há mais vértices alcançáveis. Finalizando...");
                break;
            }

            System.out.println("\n Escolhido: Vértice " + u + " (distância = " + distancias[u] + ")");
            pausar();

            // Marca como visitado
            visitados[u] = true;
            System.out.println("\n✓ Vértice " + u + " marcado como VISITADO");

            // Atualiza vizinhos
            System.out.println("\n Atualizando distâncias dos VIZINHOS do vértice " + u + ":");
            boolean temVizinhos = false;

            for (int v = 0; v < numVertices; v++) {
                if (grafo[u][v] > 0 && !visitados[v]) {
                    temVizinhos = true;
                    int distAtual = distancias[v];
                    int novaDist = distancias[u] + grafo[u][v];

                    System.out.println("\n   → Vizinho: Vértice " + v);
                    System.out.println("     Peso da aresta (" + u + " → " + v + "): " + grafo[u][v]);
                    System.out.println("     Distância atual de " + v + ": " +
                            (distAtual == Integer.MAX_VALUE ? "∞" : distAtual));
                    System.out.println("     Nova distância: " + distancias[u] + " + " + grafo[u][v] + " = " + novaDist);

                    if (novaDist < distancias[v]) {
                        System.out.println("      " + novaDist + " < " +
                                (distAtual == Integer.MAX_VALUE ? "∞" : distAtual) +
                                " → ATUALIZA!");
                        distancias[v] = novaDist;
                        predecessor[v] = u;
                    } else {
                        System.out.println("      " + novaDist + " >= " + distAtual + " → mantém o antigo");
                    }
                }
            }

            if (!temVizinhos) {
                System.out.println("   (Nenhum vizinho não visitado)");
            }

            System.out.println("\n Estado atual das distâncias:");
            mostrarEstado(distancias, visitados, numVertices);
            pausar();
        }

        // Resultados finais
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ALGORITMO CONCLUÍDO!");
        System.out.println("=".repeat(60));
        imprimirResultadosFinais(distancias, predecessor, origem, numVertices);
    }

    private static void mostrarEstado(int[] distancias, boolean[] visitados, int numVertices) {
        System.out.println("\n   Vértice | Distância | Visitado");
        System.out.println("   " + "-".repeat(35));
        for (int i = 0; i < numVertices; i++) {
            String dist = (distancias[i] == Integer.MAX_VALUE) ? "∞" : String.valueOf(distancias[i]);
            String visit = visitados[i] ? "✓" : "✗";
            System.out.printf("      %d    |    %3s    |    %s\n", i, dist, visit);
        }
    }

    private static void pausar() {
        System.out.println("\n️  Aperte ENTER para continuar...");
        scanner.nextLine();
    }

    private static void imprimirResultadosFinais(int[] distancias, int[] predecessor,
                                                 int origem, int numVertices) {
        System.out.println("\n DISTÂNCIAS MÍNIMAS a partir do vértice " + origem + ":\n");

        for (int i = 0; i < numVertices; i++) {
            if (distancias[i] == Integer.MAX_VALUE) {
                System.out.println("   Vértice " + i + ": INALCANÇÁVEL");
            } else {
                System.out.print("   Vértice " + i + ": distância = " + distancias[i]);

                // Mostra o caminho
                List<Integer> caminho = reconstruirCaminho(predecessor, i);
                if (caminho.size() > 1) {
                    System.out.print(" | Caminho: ");
                    for (int j = 0; j < caminho.size(); j++) {
                        System.out.print(caminho.get(j));
                        if (j < caminho.size() - 1) System.out.print(" → ");
                    }
                }
                System.out.println();
            }
        }

        // Opção para visualizar caminhos
        System.out.println("\n Quer visualizar algum caminho desenhado? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Digite o vértice de DESTINO (0 a " + (numVertices-1) + "): ");
            int destino = scanner.nextInt();
            scanner.nextLine();

            if (destino >= 0 && destino < numVertices && distancias[destino] != Integer.MAX_VALUE) {
                desenharCaminho(predecessor, origem, destino, distancias[destino]);
            } else {
                System.out.println(" Destino inválido ou inalcançável!");
            }
        }
    }

    private static void desenharCaminho(int[] predecessor, int origem, int destino, int distanciaTotal) {
        List<Integer> caminho = reconstruirCaminho(predecessor, destino);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        VISUALIZAÇÃO DO CAMINHO             ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        System.out.println(" Origem: Vértice " + origem);
        System.out.println(" Destino: Vértice " + destino);
        System.out.println(" Distância Total: " + distanciaTotal);
        System.out.println();

        // Desenho em árvore
        System.out.println(" CAMINHO COMPLETO:\n");

        for (int i = 0; i < caminho.size(); i++) {
            int vertice = caminho.get(i);

            // Desenha o vértice
            if (i == 0) {
                System.out.println("    ╔═══════════╗");
                System.out.println("    ║  ORIGEM   ║");
                System.out.println("    ║ Vértice " + vertice + " ║");
                System.out.println("    ╚═══════════╝");
            } else if (i == caminho.size() - 1) {
                System.out.println("    ╔═══════════╗");
                System.out.println("    ║  DESTINO  ║");
                System.out.println("    ║ Vértice " + vertice + " ║");
                System.out.println("    ╚═══════════╝");
            } else {
                System.out.println("    ╔═══════════╗");
                System.out.println("    ║ Vértice " + vertice + " ║");
                System.out.println("    ╚═══════════╝");
            }

            // Desenha a seta entre vértices
            if (i < caminho.size() - 1) {
                System.out.println("         │");
                System.out.println("         │");
                System.out.println("         ▼");
            }
        }

        // Desenho linear alternativo
        System.out.println("\n\n CAMINHO LINEAR:\n");
        System.out.println("    " + "─".repeat(50));
        System.out.print("    ");

        for (int i = 0; i < caminho.size(); i++) {
            if (i == 0) {
                System.out.print(" [" + caminho.get(i) + "]");
            } else if (i == caminho.size() - 1) {
                System.out.print(" ──→  [" + caminho.get(i) + "]");
            } else {
                System.out.print(" ──→ [" + caminho.get(i) + "]");
            }
        }

        System.out.println();
        System.out.println("    " + "─".repeat(50));

        // Legenda
        System.out.println("\n   [ ] = Vértice intermediário");

        // Passo a passo com distâncias
        System.out.println("\n\n PASSOS COM DISTÂNCIAS ACUMULADAS:\n");
        int distAcumulada = 0;

        for (int i = 0; i < caminho.size(); i++) {
            int vertice = caminho.get(i);

            if (i == 0) {
                System.out.println("    Passo " + (i+1) + ": Partida do vértice " + vertice +
                        " (distância = 0)");
            } else {
                int anterior = caminho.get(i-1);
                System.out.println("    Passo " + (i+1) + ": De vértice " + anterior +
                        " → vértice " + vertice);
            }
        }

        System.out.println("\n     Chegou ao destino! Distância total percorrida: " + distanciaTotal);
    }

    private static List<Integer> reconstruirCaminho(int[] predecessor, int destino) {
        List<Integer> caminho = new ArrayList<>();
        int v = destino;
        while (v != -1) {
            caminho.add(0, v);
            v = predecessor[v];
        }
        return caminho;
    }
}