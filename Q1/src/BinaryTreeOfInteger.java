
import java.util.NoSuchElementException;

public class BinaryTreeOfInteger {

    private static final class Node {

        public Node father;
        public Node left;
        public Node right;
        public String name;
        public Integer layer;
        private Integer element;

        public Node(Integer element) {
            father = null;
            left   = null;
            right  = null;
            name   = null;
            layer  = null;
            this.element = element;
        }
    }

    // Atributos
    private int count; //contagem do número de nodos
    private Node root; //referência para o nodo raiz

    // Metodos
    public BinaryTreeOfInteger() {
        count = 0;
        root = null;
    }

    public void clear() {
        count = 0;
        root = null;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return count;
    }

    public Integer getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }
    // Metodo privado que procura por element a partir de target
    // e retorna a referencia para o nodo no qual element esta
    // armazenado. Retorna null se nao encontrar element.
    private Node searchNodeRef(Integer element, Node target) {
        if ( target == null)
            return null;
        // Visita a "raiz"
        if (element.equals(target.element))
            return target; // se achou element na "raiz"

        // Visita subarvore da esquerda
        Node aux = searchNodeRef(element, target.left);

        // Se nao encontrou, visita a subarvore da direita
        if (aux == null)
            aux = searchNodeRef(element, target.right);

        return aux;
    }

    public boolean contains(Integer element) {
        Node nAux = searchNodeRef(element, root);
        return (nAux != null);
    }

    public Integer getParent(Integer element) {
        Node n = this.searchNodeRef(element, root);
        if (n == null) {
            return null;
        } else if (n.father==null) {
            return null;
        }else {
            return n.father.element;
        }
    }

    public void setRoot(Integer element) {
        if (root != null){
            root.element = element;
        }
    }

    public boolean addRoot(Integer element) {
        if (root != null) // se a arvore nao estiver vazia
            return false;
        root = new Node(element);
        root.layer = 0;
        count++;
        return true;
    }

    public boolean addLeft(Integer element, Integer elemFather) {
        Node aux = searchNodeRef(elemFather,root);

        if (aux == null)
            return false;
        // Se elemFather ja tem filho a esquerda
        if (aux.left != null)
            return false;

        // Senao, insere element
        Node n = new Node(element);
        n.father = aux;
        aux.left = n;
        n.layer = n.father.layer + 1;
        count++;
        return true;
    }

    public boolean addRight(Integer element, Integer elemFather) {
        // Primeiro procura por elemFather
        Node aux = searchNodeRef(elemFather,root);

        // Se nao encontrou elemFather
        if (aux == null)
            return false;
        // Se elemFather ja tem filho a direita
        if (aux.right != null)
            return false;

        Node n = new Node(element); // primeiro cria o nodo
        n.father = aux; // faz o novo nodo apontar para o pai
        aux.right = n;// faz o pai apontar para o filho
        // inicializa a camada do novo nodo com base no pai (evita NullPointerException)
        if (n.father != null && n.father.layer != null) {
            n.layer = n.father.layer + 1;
        } else {
            // se por algum motivo o pai não tiver layer, inicializa com 0 para consistência
            n.layer = 0;
        }
        count++; // atualiza count
        return true;
    }

    // Conta o numero de nodos a partir de "n"
    private int countNodes(Node n) {
        if (n==null)
            return 0;
        return 1 + countNodes(n.left) + countNodes(n.right);
    }

// ---------------------------------------------------------------------

    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger lista = new LinkedListOfInteger();
        positionsPreAux(root, lista);
        return lista;
    }

    private void positionsPreAux(Node n, LinkedListOfInteger lista) {
        if (n != null) {
            lista.add(n.element);           // visita raiz
            positionsPreAux(n.left,lista);  // percorre subarvore da esq
            positionsPreAux(n.right,lista); // percorre subarvore da dir
        }
    }

    public String setNameNode(Integer element, String name) {
        Node n = searchNodeRef(element, root);
        n.name = name;
        return n.name;
    }

    public boolean setWinner(Integer element) {
        Node n = searchNodeRef(element, root);
        if (n == null || n.father == null)
            return false; 

        n.father.name = n.name;
        n.father.element = n.element;
        return true;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    public void printPreOrder() {
        System.out.println("Percurso em PRÉ-ORDEM:");
        printPreOrder(root);
        System.out.println();
    }

    private void printPreOrder(Node n) {
        if (n == null) return;
        System.out.print("(" + n.element + (n.name != null ? " - " + n.name : "") + ") ");
        printPreOrder(n.left);
        printPreOrder(n.right);
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public void printPostOrder() {
        System.out.println("Percurso em PÓS-ORDEM:");
        printPostOrder(root);
        System.out.println();
    }

    private void printPostOrder(Node n) {
        if (n == null) return;
        printPostOrder(n.left);
        printPostOrder(n.right);
        System.out.print("(" + n.element + (n.name != null ? " - " + n.name : "") + ") ");
    }    
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public void printBreadOrder() {
        System.out.println("Percurso em LARGURA (usando FilaArray):");

        if (root == null) {
            System.out.println("(árvore vazia)");
            return;
        }

        FilaArray fila = new FilaArray();
        fila.enqueue(root.element);

        while (!fila.isEmpty()) {
            Integer atualElem = fila.dequeue();
            Node atual = searchNodeRef(atualElem, root);

            System.out.print("(" + atual.element + (atual.name != null ? " - " + atual.name : "") + ") ");

            if (atual.left != null)
                fila.enqueue(atual.left.element);
            if (atual.right != null)
                fila.enqueue(atual.right.element);
        }

        System.out.println();
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public int maxHeight() {
        return maxHeight(root);
    }

    private int maxHeight(Node n) {
        if (n == null)
            return -1; // altura de árvore vazia é -1, ou 0 se preferir
        return 1 + Math.max(maxHeight(n.left), maxHeight(n.right));
    }


    public String LCA(Integer jog1, Integer jog2) {
        Node n1 = searchNodeRef(jog1, root);
        Node n2 = searchNodeRef(jog2, root);

        if (n1 == null || n2 == null) {
            return "Um dos jogadores não existe na árvore.";
        }

        Node ancestor = LCA(root, n1, n2);
        if (ancestor == null)
            return "Não há partida comum.";
        
        return "Primeira partida possível entre " +
            (n1.name != null ? n1.name : n1.element) + " e " +
            (n2.name != null ? n2.name : n2.element) +
            " ocorre em (" + ancestor.element +
            (ancestor.name != null ? " - " + ancestor.name : "") + ")";
    }

    private Node LCA(Node root, Node a, Node b) {
        if (root == null || root == a || root == b)
            return root;

        Node left = LCA(root.left, a, b);
        Node right = LCA(root.right, a, b);

        if (left != null && right != null)
            return root;

        return (left != null) ? left : right;
    }

    // Busca element (Integer) de um nodo a partir do nome armazenado
    public Integer findElementByName(String name) {
        return findElementByName(root, name);
    }

    private Integer findElementByName(Node n, String name) {
        if (n == null) return null;
        if (n.name != null && n.name.equals(name))
            return n.element;
        Integer left = findElementByName(n.left, name);
        if (left != null) return left;
        return findElementByName(n.right, name);
    }

    // Retorna o nome associado a um elemento (ou null se não existir)
    public String getName(Integer element) {
        Node n = searchNodeRef(element, root);
        if (n == null) return null;
        return n.name;
    }

    // Conta folhas (nodos sem filhos)
    public int countLeaves() {
        return countLeaves(root);
    }

    private int countLeaves(Node n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return countLeaves(n.left) + countLeaves(n.right);
    }

    // Conta nodos internos (partidas)
    public int countInternalNodes() {
        return countNodes(root) - countLeaves(root);
    }

    // Retorna uma string descrevendo o caminho do jogador até a final
    // e indica onde foi eliminado se aplicável.
    public String pathToFinal(Integer element) {
        Node n = searchNodeRef(element, root);
        if (n == null) return "Jogador não encontrado.";
        String playerName = n.name;
        String displayPlayer = (playerName != null) ? playerName : element.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("Caminho de ").append(displayPlayer).append(" até a final:\n");

        Node cur = n;
        boolean eliminated = false;
        while (cur.father != null) {
            Node parent = cur.father;
            sb.append("Partida (").append(parent.element);
            if (parent.name != null) sb.append(" - ").append(parent.name);
            sb.append(") -> ");

            if (parent.name != null && (playerName == null || !parent.name.equals(playerName))) {
                sb.append("Eliminado aqui por ").append(parent.name).append("\n");
                eliminated = true;
                break;
            } else {
                sb.append("Avançou (ou ainda não decidido)\n");
            }
            cur = parent;
        }

        if (!eliminated) {
            sb.append("Chegou à final (ou ainda está na disputa).\n");
        }
        return sb.toString();
    }

}

