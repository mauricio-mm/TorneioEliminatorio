//LISTA ENCADEADA
// uso de genericos <E> - E elemento de uma coleção (Queue).
public class Queue<E> {

// T é usado de forma mais genérica para representar qualquer tipo
    private class Node <T> {
        public T element;
        public Node next;

        public Node(T e) {
            element = e;
            next = null;
        }
    }

    // Atributos da classe Fila (Queue)
    private int count;
    private Node<E> head;
    private Node<E> tail;

    //construtor
    public Queue() {
        count = 0;
        head = null;
        tail = null;
    }
    //enfileirar
    public void enqueue(E element) {
        Node<E> n = new Node(element);
        if (count==0) {
            head = n;
        }
        else {
            tail.next = n;
        }
        tail = n;
        count++;
    }
    //desenfileirar
    public E dequeue() {
        if (count == 0)
            throw new EmptyQueueException("Fila vazia!"); // Erro
        E aux = head.element;
        head = head.next;
        count--;
        return aux;
    }
    //retorna um valor do tipo E
    public E head() {
        if (count == 0)
            throw new EmptyQueueException("Fila vazia!"); // Erro
        return head.element;
    }

    public boolean isEmpty() {
        return (count==0);
    }

    public int size() {
        return count;
    }

    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }


}
