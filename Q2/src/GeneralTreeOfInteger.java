import java.util.LinkedList;

public class GeneralTreeOfInteger {

    private class Node {

        public Node father;
        public Integer element;
        public LinkedList<Node> subtrees;

        public Node(Integer element) {
            father = null;
            this.element = element;
            subtrees = new LinkedList<>();
            // optional human-readable name for UI
            this.name = null;
        }
        public String name;
        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }

        private boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }

        public Node getSubtree(int i) {
            if ((i < 0) || (i >= subtrees.size())) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }
        public int getSubtreesSize() {
            return subtrees.size();
        }
    }

    private Node root;
    private int count;


    public GeneralTreeOfInteger() {
        root = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    private Node searchNodeRef(Integer elem, Node n) {
        if (n == null)
            return null;

        if (elem.equals(n.element))
            return n;

        Node aux = null;
        for (int i=0; i<n.getSubtreesSize(); i++) {
            aux = searchNodeRef(elem, n.getSubtree(i));
            if (aux != null)
                return aux;
        }
        return aux;
    }

    // --- Naming support ---
    public boolean setName(Integer elem, String name) {
        Node n = searchNodeRef(elem, root);
        if (n == null) return false;
        n.name = name;
        return true;
    }

    public String getName(Integer elem) {
        Node n = searchNodeRef(elem, root);
        if (n == null) return null;
        return n.name;
    }

    public Integer findElementByName(String name) {
        if (root == null) return null;
        return findElementByNameAux(root, name);
    }

    private Integer findElementByNameAux(Node n, String name) {
        if (n == null) return null;
        if (n.name != null && n.name.equals(name)) return n.element;
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            Integer found = findElementByNameAux(n.getSubtree(i), name);
            if (found != null) return found;
        }
        return null;
    }

    // --- Consistency checker: single root and no cycles, and count matches node count ---
    public boolean isConsistent() {
        if (root == null) return true; // empty tree considered consistent
        java.util.HashSet<Integer> visited = new java.util.HashSet<>();
        try {
            int c = isConsistentAux(root, visited);
            return (c == count);
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private int isConsistentAux(Node n, java.util.HashSet<Integer> visited) {
        if (n == null) return 0;
        if (visited.contains(n.element)) throw new IllegalStateException("Cycle detected");
        visited.add(n.element);
        int total = 1;
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            Node child = n.getSubtree(i);
            if (child.father != n) throw new IllegalStateException("Parent link inconsistent");
            total += isConsistentAux(child, visited);
        }
        return total;
    }

    public boolean add(Integer elem, Integer elemFather) {

        Node n = new Node(elem);

        if (elemFather == null) { 
            if (root != null) { 
                root.father = n;
                n.addSubtree(root);
            }
            root = n;
            count++;
            return true;
        }
        else {
            Node aux = searchNodeRef(elemFather, root);
            if (aux != null) { 
            
                n.father = aux;
                aux.addSubtree(n);
                count++;
                return true;
            }
        }
        return false;
    }

    public boolean contains (Integer elem) {
            Node aux = searchNodeRef(elem, root);
            return (aux != null); 
        }

    public LinkedList<Integer> positionsWidth() {
        LinkedList<Integer> lista = new LinkedList<>();
        if (root != null) {
            Queue<Node> fila = new Queue<>();

            fila.enqueue(root);

            while (!fila.isEmpty()) {        
                Node aux = fila.dequeue();            
                lista.add(aux.element);                
                for (int i=0; i<aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
        }
        return lista;
    }

    public LinkedList<Integer> positionsPre() {
        LinkedList<Integer> lista = new LinkedList<>();
        positionsPreAux(root,lista);
        return lista;
    }
    private void positionsPreAux(Node n, LinkedList<Integer> lista) {
        if (n != null) {
            lista.add(n.element);            
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i), lista);
            }
        }
    }

    public LinkedList<Integer> positionsPos() {
        LinkedList<Integer> lista = new LinkedList<>();
        positionsPosAux(root, lista);
        return lista;
    }

    private void positionsPosAux(Node n, LinkedList<Integer> lista) {
        if( n != null ) {            
            for (int i=0; i<n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i),lista);
            }            
            lista.add(n.element);
        }
    }

    public int level(Integer element) {
        // IMPLEMENTE ESTE METODO !!
        return 0;

    }

    public void geraNodosDOT(Node n)
    {
        System.out.println("node [shape = circle];\n");

        LinkedList<Integer> L = new LinkedList<>();
        //L = positionsWidth();
        L = positionsPre();

        for (int i = 0; i< L.size(); i++ )
        {
            // node1 [label = "1"]
            System.out.println("node" + L.get(i) + " [label = \"" +  L.get(i) + "\"]") ;
        }
    }

    public void geraConexoesDOT(Node n)
    {
        for (int i=0; i<n.getSubtreesSize(); i++)
        {
            Node aux = n.getSubtree(i);
            System.out.println("node" + n.element + " -> " + "node" + aux.element + ";");
            geraConexoesDOT(aux);
        }
    }

    public void geraDOT()
    {
        if (root != null) {
            System.out.println("digraph g { \n");
            // node [style=filled];

            geraNodosDOT(root);

            geraConexoesDOT(root);
            System.out.println("}\n");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // MOVER SUBARVORE NUMERO 3
    public boolean moveSubtree(Integer elem, Integer newFather) {

        if (root == null || root.element.equals(elem))
            return false;

        Node nodeToMove = searchNodeRef(elem, root);
        Node newFatherNode = searchNodeRef(newFather, root);

        if (nodeToMove == null || newFatherNode == null)
            return false;

        if (isDescendant(nodeToMove, newFatherNode))
            return false;

        Node oldFather = nodeToMove.father;
        if (oldFather != null)
            oldFather.removeSubtree(nodeToMove);

        newFatherNode.addSubtree(nodeToMove);
        nodeToMove.father = newFatherNode;

        return true;
    }
    
    // Verifica se target é descendente de node
    private boolean isDescendant(Node node, Node target) {
        if (node == null || target == null)
            return false;

        if (node == target)
            return true;

        for (int i = 0; i < node.getSubtreesSize(); i++) {
            if (isDescendant(node.getSubtree(i), target))
                return true;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // REMOVER SUBARVORE NUMERO 4
        public boolean removeBranch(Integer element) {
    
        if (root == null)
            return false;

        if (root.element.equals(element)) {
            root = null;
            count = 0;
            return true;
        }

        Node target = searchNodeRef(element, root);
        if (target == null)
            return false;

        Node father = target.father;
        if (father == null)
            return false;
        
        int removedNodes = countNodes(target);
        
        father.removeSubtree(target);
        count -= removedNodes;

        return true;
    }

    private int countNodes(Node n) {
        if (n == null)
            return 0;

        int total = 1; 
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            total += countNodes(n.getSubtree(i));
        }
        return total;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSULTAS NUMERO 5
    public int height() {
        return heightAux(root);
    }

    private int heightAux(Node n) {
        if (n == null)
            return -1; 
        if (n.getSubtreesSize() == 0)
            return 0; 

        int max = -1;
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            int h = heightAux(n.getSubtree(i));
            if (h > max)
                max = h;
        }
        return max + 1;
    }

    public int maxDegree() {
        return maxDegreeAux(root);
    }

    private int maxDegreeAux(Node n) {
        if (n == null)
            return 0;

        int max = n.getSubtreesSize();

        for (int i = 0; i < n.getSubtreesSize(); i++) {
            int childDegree = maxDegreeAux(n.getSubtree(i));
            if (childDegree > max)
                max = childDegree;
        }
        return max;
    }

    public int countLeaves() {
        return countLeavesAux(root);
    }

    private int countLeavesAux(Node n) {
        if (n == null)
            return 0;

        if (n.getSubtreesSize() == 0)
            return 1;

        int total = 0;
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            total += countLeavesAux(n.getSubtree(i));
        }
        return total;
    }

    public Integer LCA(Integer x, Integer y) {
        Node nodeX = searchNodeRef(x, root);
        Node nodeY = searchNodeRef(y, root);

        if (nodeX == null || nodeY == null)
            return null; 

        LinkedList<Node> pathX = getPathToRoot(nodeX);
        LinkedList<Node> pathY = getPathToRoot(nodeY);

        Node lca = null;
        int i = pathX.size() - 1;
        int j = pathY.size() - 1;

        while (i >= 0 && j >= 0 && pathX.get(i) == pathY.get(j)) {
            lca = pathX.get(i);
            i--;
            j--;
        }

        return (lca != null) ? lca.element : null;
    }

    private LinkedList<Node> getPathToRoot(Node n) {
        LinkedList<Node> path = new LinkedList<>();
        while (n != null) {
            path.add(n);
            n = n.father;
        }
        return path;
    }

    public LinkedList<Integer> caminho(Integer x, Integer y) {
        Node nodeX = searchNodeRef(x, root);
        Node nodeY = searchNodeRef(y, root);

        if (nodeX == null || nodeY == null)
            return null;
      
        LinkedList<Node> pathX = getPathToRoot(nodeX);
        LinkedList<Node> pathY = getPathToRoot(nodeY);

        Node lca = null;
        int i = pathX.size() - 1;
        int j = pathY.size() - 1;

        while (i >= 0 && j >= 0 && pathX.get(i) == pathY.get(j)) {
            lca = pathX.get(i);
            i--;
            j--;
        }

        LinkedList<Integer> result = new LinkedList<>();

        Node temp = nodeX;
        while (temp != lca) {
            result.add(temp.element);
            temp = temp.father;
        }
        result.add(lca.element); 

        LinkedList<Integer> descida = new LinkedList<>();
        temp = nodeY;
        while (temp != lca) {
            descida.addFirst(temp.element);
            temp = temp.father;
        }

        result.addAll(descida);
        return result;
    }

}
