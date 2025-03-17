import java.util.Iterator;
import java.util.NoSuchElementException;

class ListaIterator<E> implements Iterator<E> {
    private Celula<E> atual;

    public ListaIterator(ListaDuplamenteEncadeada<E> lista) {
        this.atual = lista.getPrimeiro().getProximo(); // Ignora nó sentinela
    }

    @Override
    public boolean hasNext() {
        return atual != null;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        E item = atual.getItem();
        atual = atual.getProximo();
        return item;
    }
}

class ListaDuplamenteEncadeada<E> implements Iterable<E> {
    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int tamanho;

    public ListaDuplamenteEncadeada() {
        Celula<E> sentinela = new Celula<>();
        this.primeiro = this.ultimo = sentinela;
        this.tamanho = 0;
    }

    public int tamanho() {
        return this.tamanho;
    }

    public boolean vazia() {
        return this.primeiro == this.ultimo;
    }

    public void inserirFinal(E novo) {
        Celula<E> novaCelula = new Celula<>(novo, this.ultimo, null);
        this.ultimo.setProximo(novaCelula);
        this.ultimo = novaCelula;
        this.tamanho++;
    }

    public E removerFinal() {
        if (vazia()) {
            throw new IllegalStateException("A lista está vazia!");
        }
        Celula<E> removida = this.ultimo;
        this.ultimo = removida.getAnterior();
        this.ultimo.setProximo(null);
        this.tamanho--;
        return removida.getItem();
    }

    @Override
    public Iterator<E> iterator() {
        return new ListaIterator<>(this);
    }
}
