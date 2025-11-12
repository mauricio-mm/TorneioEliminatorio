public class Main {
    public static void main(String[] args) {

        GeneralTreeOfInteger arv = new GeneralTreeOfInteger();
        //raiz
        // We'll replace this example with an interactive menu below
        Menu.run();

    }
}

class Menu {
    private static java.util.Scanner scanner = new java.util.Scanner(System.in);
    private static GeneralTreeOfInteger tree = new GeneralTreeOfInteger();
    private static int nextId = 1;

    public static void run() {
        // initialize with root "App"
        tree.add(nextId++, null);
        tree.setName(1, "App");

        boolean running = true;
        while (running) {
            printMenu();
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "0": running = false; break;
                case "1": createRoot(); break;
                case "2": insertItem(); break;
                case "3": moveSubtree(); break;
                case "4": removeSubtree(); break;
                case "5": queries(); break;
                case "6": traversals(); break;
                case "7": lcaAndPath(); break;
                case "8": checkConsistency(); break;
                case "9": listNodes(); break;
                default: System.out.println("Opção inválida");
            }
            System.out.println();
        }
        System.out.println("Saindo...");
    }

    private static void printMenu() {
        System.out.println("--- Menu da Árvore Geral ---");
        System.out.println("0 - Sair");
        System.out.println("1 - Criar menu inicial (substitui árvore por raiz 'App')");
        System.out.println("2 - Inserir item (informe nome e pai)");
        System.out.println("3 - Mover subárvore (re-root local)");
        System.out.println("4 - Remover subárvore");
        System.out.println("5 - Consultas estruturais (altura, grau máximo, #folhas, #nós)");
        System.out.println("6 - Percursos (pré, pós, largura)");
        System.out.println("7 - LCA(x,y) e Caminho(x,y)");
        System.out.println("8 - Verificador de consistência");
        System.out.println("9 - Listar nodos (id - nome)");
        System.out.print("Escolha uma opção: ");
    }

    private static void createRoot() {
        tree = new GeneralTreeOfInteger();
        nextId = 1;
        tree.add(nextId++, null);
        tree.setName(1, "App");
        System.out.println("Árvore reinicializada com raiz 'App' (id=1).");
    }

    private static void insertItem() {
        System.out.print("Nome do novo item: ");
        String name = scanner.nextLine().trim();
        System.out.print("Pai (id ou nome): ");
        String parent = scanner.nextLine().trim();
        Integer pid = parseElementOrFindByName(parent);
        if (pid == null) {
            System.out.println("Pai não encontrado.");
            return;
        }
        int id = nextId++;
        if (tree.add(id, pid)) {
            tree.setName(id, name);
            System.out.println("Item inserido: " + id + " - " + name);
        } else {
            System.out.println("Falha ao inserir item.");
        }
    }

    private static void moveSubtree() {
        System.out.print("Elemento a mover (id ou nome): ");
        String s = scanner.nextLine().trim();
        Integer elem = parseElementOrFindByName(s);
        if (elem == null) { System.out.println("Elemento não encontrado."); return; }
        System.out.print("Novo pai (id ou nome): ");
        String t = scanner.nextLine().trim();
        Integer newFather = parseElementOrFindByName(t);
        if (newFather == null) { System.out.println("Novo pai não encontrado."); return; }
        boolean ok = tree.moveSubtree(elem, newFather);
        System.out.println(ok ? "Movido com sucesso." : "Falha ao mover (possível ciclo ou raiz).");
    }

    private static void removeSubtree() {
        System.out.print("Elemento a remover (id ou nome): ");
        String s = scanner.nextLine().trim();
        Integer elem = parseElementOrFindByName(s);
        if (elem == null) { System.out.println("Elemento não encontrado."); return; }
        boolean ok = tree.removeBranch(elem);
        System.out.println(ok ? "Removido com sucesso." : "Falha ao remover.");
    }

    private static void queries() {
        System.out.println("Altura: " + tree.height());
        System.out.println("Grau máximo: " + tree.maxDegree());
        System.out.println("# folhas: " + tree.countLeaves());
        int total = tree.size();
        int leaves = tree.countLeaves();
        System.out.println("# nós internos: " + (total - leaves));
    }

    private static void traversals() {
        System.out.println("-- Pré-ordem --");
        System.out.println(tree.positionsPre());
        System.out.println("-- Pós-ordem --");
        System.out.println(tree.positionsPos());
        System.out.println("-- Largura --");
        System.out.println(tree.positionsWidth());
    }

    private static void lcaAndPath() {
        System.out.print("Primeiro elemento (id ou nome): ");
        String a = scanner.nextLine().trim();
        System.out.print("Segundo elemento (id or nome): ");
        String b = scanner.nextLine().trim();
        Integer ea = parseElementOrFindByName(a);
        Integer eb = parseElementOrFindByName(b);
        if (ea == null || eb == null) { System.out.println("Um ou ambos não encontrados."); return; }
        Integer lca = tree.LCA(ea, eb);
        System.out.println("LCA: " + (lca != null ? (lca + " - " + tree.getName(lca)) : "(nenhum)"));
        System.out.print("Deseja ver caminho entre os dois? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (resp.equals("s") || resp.equals("sim")) {
            java.util.LinkedList<Integer> caminho = tree.caminho(ea, eb);
            System.out.println(caminho);
        }
    }

    private static void checkConsistency() {
        boolean ok = tree.isConsistent();
        System.out.println(ok ? "Árvore consistente." : "Inconsistência detectada.");
    }

    private static void listNodes() {
        java.util.LinkedList<Integer> pre = tree.positionsPre();
        System.out.println("Nodos (id - nome):");
        for (Integer id : pre) {
            System.out.println(id + " - " + (tree.getName(id) != null ? tree.getName(id) : "(sem nome)"));
        }
    }

    private static Integer parseElementOrFindByName(String input) {
        try { return Integer.parseInt(input); } catch (NumberFormatException e) { return tree.findElementByName(input); }
    }
}