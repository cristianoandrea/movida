package movida.cristianopalomba.mappe;

public class AVL_struct<V,K> {

    public int altezza;
    public V elemento;
    public K chiave;
    public AVL_struct<V, K> sx, dx;

    public AVL_struct(V element,K key) {
        chiave=key;
        elemento=element;
        altezza=0;
    }

}