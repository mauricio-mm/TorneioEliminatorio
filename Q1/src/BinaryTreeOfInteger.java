
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


}   
