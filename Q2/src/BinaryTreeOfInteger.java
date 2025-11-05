
import java.util.NoSuchElementException;

public class BinaryTreeOfInteger {

    private static final class Node {

        public Node father;
        public Node left;
        public Node right;
        public String name;
        private Integer element;

        public Node(Integer element) {
            father = null;
            left   = null;
            right  = null; 
            name   = null;           
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
            lista.add(n.element);           
            positionsPreAux(n.left,lista);  
            positionsPreAux(n.right,lista); 
        }
    }

    public void setName(Integer element, String name) {
        Node n = searchNodeRef(element,root);
        n.name = name;
    }
}   
