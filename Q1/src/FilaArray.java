//COM ARRANJO
/*Fila com array circular
 O uso de um array circular permite que a fila reutilize
 os índices de forma eficiente sem desperdiçar memória quando
elementos são removidos.*/
public class FilaArray {
    private Integer fila[];
    private int count;
    private int primeiro; // indice para o primeiro da fila
    private int ultimo; // indice para o ultimo da fila


    public FilaArray() {
        fila = new Integer[10];
        count = 0;
        // as operações de inserção e remoção começarão do início do array.
        primeiro = 0;
        ultimo = 0;
    }

    // Insere o elemento no final da fila
    public void enqueue(Integer element) {
        // Primeiro verifica se tem espaco na fila
        if (count == fila.length)
            throw new FullQueueException("A fila esta cheia!");
        fila[ultimo] = element;
        count++;
        //Exemplo de como o índice circular funciona:
        ultimo = (ultimo+1) % fila.length;
        // pega o índice "ultimo" e divide pelo total de elementos
        // Por exemplo num vetor de 10 posicoes (indices 0 a 9):
        // - Se ultimo estava na posicao 4: 5/10, resto eh 5
        // - Se ultimo estava na posicao 9: 10/10, resto eh 0
    }

    // remove e retorna o elemento e do início da fila,
    // e dá erro se a fila estiver vazia
    public Integer dequeue() {
        // Primeiro verifica se a fila nao esta vazia
        if (count == 0)
            throw new EmptyQueueException("A fila esta vazia!");
        Integer elem = fila[primeiro];
        fila[primeiro] = null;
        count--;
        //Atualiza o índice primeiro de forma circular usando a operação
        // (primeiro + 1) % fila.length, garantindo que, se primeiro atingir
        // o final do array, ele volte para o início.
        primeiro = (primeiro+1) % fila.length;
        return elem;
    }

    // retorna, mas não remove, o primeiro elemento da fila,
    // e dá erro se a fila estiver vazia
    public Integer head() {
        // Primeiro verifica se a fila nao esta vazia
        if (count == 0)
            throw new EmptyQueueException("A fila esta vazia!");
        return fila[primeiro];
    }

    // retorna o número de elementos da fila
    public int size() {
        return count;
    }

    // retorna true se a fila estiver vazia, e false caso contrário
    public boolean isEmpty() {
        return (count == 0);
    }

    // esvazia a fila
    public void clear(){
        fila = new Integer[10];
        count = 0;
        primeiro = 0;
        ultimo = 0;
    }

}
