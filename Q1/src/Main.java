import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static BinaryTreeOfInteger championship = new BinaryTreeOfInteger();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            String option = scanner.nextLine().trim();
            switch (option) {
                case "0":
                    running = false;
                    System.out.println("Saindo...");
                    break;
                case "1":
                    optionCreateTournament();
                    break;
                case "2":
                    optionRegisterWinner();
                    break;
                case "3":
                    optionTraversals();
                    break;
                case "4":
                    optionStructuralQueries();
                    break;
                case "5":
                    optionAnalysisBetweenPlayers();
                    break;
                case "6":
                    optionPathToFinal();
                    break;
                case "7":
                    optionListNodes();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("========== Menu do Torneio ==========");
        System.out.println("0 - Sair");
        System.out.println("1 - Criar torneio a partir de uma lista de nomes (folhas)");
        System.out.println("2 - Registrar vencedor de uma partida (preenche o nodo pai)");
        System.out.println("3 - Percursos (pré-ordem, pós-ordem, largura)");
        System.out.println("4 - Consultas estruturais (altura, nº folhas, nº nós internos)");
        System.out.println("5 - Análise entre jogadores (LCA)");
        System.out.println("6 - Caminho do jogador até a final (e ponto de eliminação)");
        System.out.println("7 - Listar nodos (elemento - nome)");
        System.out.print("Escolha uma opção: ");
    }

    // Opção 1
    private static void optionCreateTournament() {
        System.out.print("Número de participantes (deve ser inteiro positivo): ");
        int n;
        try {
            n = Integer.parseInt(scanner.nextLine().trim());
            if (n <= 0) {
                System.out.println("Número inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        ArrayList<String> names = new ArrayList<>();
        System.out.println("Digite os nomes dos participantes, um por linha (" + n + " nomes):");
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + ") ");
            String name = scanner.nextLine();
            names.add(name);
        }

        createChampionLeagueFromList(championship, names);
        System.out.println("Torneio criado com " + n + " participantes nas folhas.");
    }

    // Cria a árvore num esquema idêntico ao anterior, coloca nomes nas folhas
    private static void createChampionLeagueFromList(BinaryTreeOfInteger championship, ArrayList<String> names) {
        championship.clear();
        int n = names.size();
        championship.addRoot(1);

        for (int i = 1; i <= n * 2; i++) {
            championship.addLeft(2 * i, i);
            championship.addRight(2 * i + 1, i);
        }

        // Atribui nomes às folhas: elementos de n+1 a 2n
        for (int i = 0; i < n; i++) {
            int elem = n + 1 + i;
            championship.setNameNode(elem, names.get(i));
        }
    }

    // Opção 2
    private static void optionRegisterWinner() {
        System.out.print("Informe o vencedor (nome ou elemento): ");
        String input = scanner.nextLine().trim();
        Integer elem = parseElementOrFindByName(input);
        if (elem == null) {
            System.out.println("Jogador não encontrado.");
            return;
        }

        boolean ok = championship.setWinner(elem);
        if (!ok) {
            System.out.println("Não foi possível registrar vencedor (elemento inexistente ou sem pai).\n");
            return;
        }
        System.out.println("Vencedor registrado no pai imediato.");

        System.out.print("Deseja propagar o vencedor até o topo automaticamente? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (resp.equals("s") || resp.equals("sim")) {
            Integer current = elem;
            while (championship.getParent(current) != null) {
                championship.setWinner(current);
                current = championship.getParent(current);
            }
            System.out.println("Propagação concluída até o topo (quando aplicável).\n");
        }
    }

    // Opção 3
    private static void optionTraversals() {
        System.out.println("-- PRÉ-ORDEM --");
        championship.printPreOrder();
        System.out.println("-- PÓS-ORDEM --");
        championship.printPostOrder();
        System.out.println("-- LARGURA --");
        championship.printBreadOrder();
    }

    // Opção 4
    private static void optionStructuralQueries() {
        System.out.println("Altura da árvore: " + championship.maxHeight());
        System.out.println("Número de folhas (participantes): " + championship.countLeaves());
        System.out.println("Número de nós internos (partidas): " + championship.countInternalNodes());
    }

    // Opção 5
    private static void optionAnalysisBetweenPlayers() {
        System.out.print("Informe o primeiro jogador (nome ou elemento): ");
        String a = scanner.nextLine().trim();
        System.out.print("Informe o segundo jogador (nome ou elemento): ");
        String b = scanner.nextLine().trim();

        Integer ea = parseElementOrFindByName(a);
        Integer eb = parseElementOrFindByName(b);
        if (ea == null || eb == null) {
            System.out.println("Um ou ambos os jogadores não foram encontrados.");
            return;
        }

        System.out.println(championship.LCA(ea, eb));
    }

    // Opção 6
    private static void optionPathToFinal() {
        System.out.print("Informe o jogador (nome ou elemento): ");
        String s = scanner.nextLine().trim();
        Integer e = parseElementOrFindByName(s);
        if (e == null) {
            System.out.println("Jogador não encontrado.");
            return;
        }
        System.out.println(championship.pathToFinal(e));
    }

    // Opção 7: lista nodos (element - nome)
    private static void optionListNodes() {
        LinkedListOfInteger lista = championship.positionsPre();
        int size = lista.size();
        if (size == 0) {
            System.out.println("Árvore vazia.");
            return;
        }
        System.out.println("Lista de nodos (elemento - nome):");
        for (int i = 0; i < size; i++) {
            Integer elem = lista.get(i);
            String name = championship.getName(elem);
            System.out.println(elem + " - " + (name != null ? name : "(vazio)") );
        }
    }

    // Tenta converter input para elemento inteiro; se falhar, tenta buscar por nome
    private static Integer parseElementOrFindByName(String input) {
        // tenta inteiro
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // procura por nome
            return championship.findElementByName(input);
        }
    }
}
