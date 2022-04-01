package movida.cristianopalomba.mappe;

public interface Map<V,K extends Comparable<K>> //extends Iterable<V>  
{
    /**
     * Aggiunge al dizionario l'elemento <code>elem</code> associato
     * alla chiave <code>key</code>
     * 
     * @param elem l'elemento da aggiungere al dizionario
     * @param key la chiave da associare a <code>elem</code>
     */
    public void add(V elem, K key); //avl si 23 sì

    /**
     * Rimuove l'elemento associato alla chiave <code>key</code> dal
     * dizionario, ritornando il record associato ad esso
     * 
     * @param key la chiave associata all'elemento da rimuovere
     * @return il record dell'elemento rimosso
     */
    public V remove(K key); // avl sì

    /**
     * ritorna true se e solo se il dizionario contiene la chiave
     * <code>key</code>
     * 
     * @param key la chiave da cercare nel dizionario
     * @return true se la chiave è presente, false altrimenti
     */
    public boolean contains(K key);//avl si 23 sì

    /**
     * ritorna la dimensione del dizionario
     * 
     * @return il numero di elementi contenuti
     */
    public int size();//avl si 23 sì

    /**
     * ritorna il record dell'elemento associato alla chiave <code>key</code>
     * 
     * @param key la chiave da cercare nel dizionario
     * @return il record associato alla chiave <code>key</code>
     */
    public V get(K key);//avl si 23 sì

    /**
     * ritorna l'array contenente tutti gli elementi contenuti nel
     * dizionario
     * 
     * @return un array contenente tutti gli elementi
     */
    public V[] toArray();//avl si 

    /**
     * controlla che il dizionario contenga degli elementi
     * 
     * @return true se il dizionario non contiene elementi, false altrimenti
     */
    public boolean isEmpty();//avl si 23 sì

    /**
     * elimina ogni riferimento agli elementi contenuti nel dizionario
     */
    public void clear();//avl si 23 sì

    /**
     * ritorna il dizionario con tutti gli stessi elementi, ma con
     * l'implementazione opposta
     * 
     * @return un dizionario contenente tutti gli stessi elementi di questo
     */
    public Map<V,K> swap();
    
}