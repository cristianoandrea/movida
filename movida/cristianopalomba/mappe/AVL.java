package movida.cristianopalomba.mappe;

import java.lang.reflect.Array;
import java.util.ArrayList;

import movida.commons.*;

public class AVL<V, K extends Comparable<K>> implements Map<V,K> {

    

    private AVL_struct<V, K> radice;

    private ArrayList<AVL_struct<V,K>> nodi;

    private int dimensione;
    
    public AVL () {
        /*
        radice = new AVL_struct<V,K>(elem, chiave);
        nodi= new ArrayList<AVL_struct<V,K>>();
        dimensione=1; */
        dimensione=0;
        radice=null;
    }

    private int altezza (AVL_struct<V,K> N) {
            if (N==null) return 0;
            return N.altezza;
    }

    


    private AVL_struct<V,K> rightRotate (AVL_struct<V,K> b) {

        AVL_struct<V,K> a=b.sx;
        AVL_struct<V,K> T2 = a.dx;

        a.dx=b;
        b.sx= T2;

        b.altezza=max(altezza(b.sx), altezza(b.dx)+1);
        a.altezza=max(altezza(a.sx), altezza(a.dx)+1);

        return a;
    }



    private AVL_struct<V,K> leftRotate (AVL_struct<V,K> a) {

        AVL_struct<V,K> b=a.dx;
        AVL_struct<V,K> T2 = b.sx;

        b.sx=a;
        a.dx= T2;

        b.altezza=max(altezza(b.sx), altezza(b.dx)+1);
        a.altezza=max(altezza(a.sx), altezza(a.dx)+1);

        return b;
    }

    private int getBalance(AVL_struct<V,K> N) { 
        if (N == null) 
            return 0; 
  
        return altezza(N.sx) - altezza(N.dx); 
    } 

    
    public void add(V elem, K key) {

        AVL_struct<V, K> peppecaccia = new AVL_struct<V, K>(elem, key);
        if (radice==null) {
            radice=peppecaccia;
            radice.altezza=0;
            dimensione=1;
            return;
        } else {
            //nodi.add(peppecaccia);
            radice=insert(radice ,peppecaccia);
            return;
        }
        
    }

    private AVL_struct<V, K> insert (AVL_struct<V,K> node, AVL_struct<V,K> key) {

        if (node == null) 
            return (new AVL_struct<V, K> (key.elemento, key.chiave)); 

        if (key.chiave.compareTo(node.chiave)<0)
            node.sx=insert(node.sx, key);

        else if (key.chiave.compareTo(node.chiave)>0)
            node.dx=insert(node.dx, key);
        
        else return node;

        node.altezza=1+max(altezza(node.dx), altezza(node.sx));

        int balance = getBalance(node);

        if(balance > 1 && key.chiave.compareTo(node.sx.chiave)<0) 
            return rightRotate(node);
            
        if (balance < -1 && key.chiave.compareTo(node.dx.chiave)<0) 
            return leftRotate(node);

        if (balance > 1 && key.chiave.compareTo(node.sx.chiave)>0) {
            node.sx=leftRotate(node.sx);
            return rightRotate(node);
        }

        if (balance < -1 && key.chiave.compareTo( node.dx.chiave)<0) {
            node.dx=rightRotate(node.dx);
            return leftRotate(node);
        }
        dimensione++;
        return node;

    }

    private int max (int nodo1, int nodo2) {
        if(nodo1>=nodo2) return nodo1;
        else return nodo2;
    }

    public int size () {
        return dimensione;
    }

    

    private void preOrder(AVL_struct<V,K> node, V[] array, int i ) { 
        if (node != null) { 
            array[i]=node.elemento; 
            preOrder(node.sx, array, i++); 
            preOrder(node.dx, array, i++); 
        } else return;
    }

    public V[] toArray() {
        
        Integer lenght=size();
        V[] array=(V[]) Array.newInstance(radice.getClass(), lenght);
        preOrder(radice, array, 0);
        return array;

    }


    public boolean contains(K key){
        return search(radice, key);
    }

    private boolean search(AVL_struct<V,K> node, K key){
        if (node == null) return false ;
        else if (node.chiave == key) return true;
        else if (node.chiave.compareTo(key)<0) return(search(node.dx, key)) ;
        else return(search(node.sx,key)) ;
    }

    public V remove(K key) {
        //sponsorizzato da: Pepsi 
        V oo= get(key);
        AVL_struct<V,K> node = new AVL_struct<V,K>(oo, key);
        delete(radice, node);
        return oo;

    }


    private AVL_struct<V,K> minValueNode(AVL_struct<V,K> node)  
    {  
        AVL_struct<V,K> current = node;  
  
        /* loop down to find the leftmost leaf */
        while (current.sx != null)  
        current = current.sx;  
  
        return current;  
    }  


    private AVL_struct<V,K> delete(AVL_struct<V,K> root, AVL_struct<V,K> key) {

        
        if (isEmpty()) return null;

        if (key.chiave.compareTo(root.chiave)<0)
            root.sx=delete(root.sx, key);

        else if (key.chiave.compareTo(root.chiave)>0)
            root.dx=delete(root.dx, key);
        
        else {

            if ((root.sx==null)||(root.dx==null)) {

                AVL_struct<V,K> tmp = null;
                
                if (tmp==root.sx)
                    tmp= root.dx;
                else if (tmp==root.dx) 
                    tmp=root.sx;

                if (tmp==null) {
                    tmp=root;
                    root=null;
                }

                else root=tmp;

            } else {

                AVL_struct<V,K> tmp = minValueNode(root.dx);
                root.chiave=tmp.chiave;
                root.dx=delete(root.dx, tmp);
            }
        }

        radice.altezza=max(altezza(root.sx), altezza(root.dx));
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.sx) >= 0)  
            return rightRotate(root); 

        if (balance > 1 && getBalance(root.sx) < 0)  {  
            root.sx = leftRotate(root.sx);  
            return rightRotate(root);  
        } 
        
        if (balance < -1 && getBalance(root.dx) <= 0)  
            return leftRotate(root); 
        
        if (balance < -1 && getBalance(root.dx) > 0){  
            root.dx = rightRotate(root.dx);  
            return leftRotate(root);  
        }

        return root;

    }

    public V get(K key) {
        return getV(radice, key);
    }

    private V getV(AVL_struct<V,K> node, K key){

        if (node == null) return(null);
        else if (node.chiave == key) return(node.elemento);
        else if (node.chiave.compareTo(key)<0) return(getV(node.dx, key)) ;
        else return(getV(node.sx,key)) ;
    }

    public boolean isEmpty() {
        if (radice==null) return true;
        else return false;
    }

    public void clear() {
        radice=null;
        System.out.println("clear function brought to you by FERDINAND PALOMBA");
        return;
    }

    public Map<V,K> swap(){ 
        Tree23<V,K> albero = new Tree23<V,K>(nodi.get(0).elemento, nodi.get(0).chiave);
        for(int i=1; i<nodi.size();i++) {
            albero.add(nodi.get(i).elemento, nodi.get(i).chiave);
        }
        return albero;
    }


}