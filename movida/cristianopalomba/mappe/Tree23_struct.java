package movida.cristianopalomba.mappe;

import java.lang.reflect.Array;

public class Tree23_struct<V,K extends Comparable<K>> {

    private K chiave;
    private V elemento;
    public boolean isFoglia;
    public K[] intermedio= (K[]) Array.newInstance(chiave.getClass(), 2);
    public Tree23_struct<V,K> sx, dx, cx, padre;
    //private int figli;

    public Tree23_struct (V element, K key){
        chiave=key;
        elemento=element;
        isFoglia=true;
        sx=null;
        cx=null;
        dx=null;
        padre=null;
       
    }

    

    public K getChiave() {
        return chiave;
    }

    public V getElement() {
        return elemento;
    }

    
}