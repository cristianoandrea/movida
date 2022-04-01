package movida.cristianopalomba.mappe;

import java.lang.reflect.Array;




public class Tree23<V,K extends Comparable<K>> implements Map<V,K> {

    private Tree23_struct<V,K> radice;
   
    public Tree23() {
        /*
        radice= new Tree23_struct<V,K>(element, key);
        */
        radice=null;
    }

    public Tree23 (V elem, K key) {
        radice= new Tree23_struct<V,K>(elem, key);
    }

    

    private void setFigli (Tree23_struct<V,K> input, Tree23_struct<V,K> sinistra, Tree23_struct<V,K> centro) {

        if(input.sx==null && input.dx==null && input.cx==null) {
            input.isFoglia=true;
            input.sx=sinistra; input.cx=centro; input.dx=null;
            input.intermedio=null;
            return;

        } else {
            if (returnMax(sinistra).compareTo(returnMax(centro))>0) {
                input.cx=sinistra; input.sx=centro; input.dx=null;
            } else if (returnMax(sinistra).compareTo(returnMax(centro))<=0) {
                input.sx=sinistra; input.cx= centro; input.dx=null;
            }
            sinistra.padre=input; centro.padre=input;
            input.isFoglia=false;
            setIntermedio(input);
            
        }
        
    }

    private void set (Tree23_struct<V,K> node, Tree23_struct<V,K> input) {

        if (returnMax(input).compareTo(returnMax(node.sx))<0) {
            node.dx=node.cx; node.cx=node.sx; node.sx=input;
        } else if (returnMax(input).compareTo(returnMax(node.cx))>0) {
            node.dx=input;
        } else {
            node.dx=node.cx; node.cx=input;
        }
        input.padre=node;
        node.intermedio[1]=returnMax(node.cx);
        return;
    }

    private void setIntermedio(Tree23_struct<V,K> node){

        if (node.dx==null && node.isFoglia==false) {
            //sappiamo che ha 2 figli
            node.intermedio[0]=returnMax(node.sx);
            node.intermedio[1]=null;
        } else if (node.dx!=null) {
            node.intermedio[0]=returnMax(node.sx);
            node.intermedio[1]=returnMax(node.cx);
        }
        return;
    }
    
    private K returnMax (Tree23_struct<V,K> sottoalbero) {

        while (sottoalbero.sx!=null) {
            if (sottoalbero.dx!=null) {
                sottoalbero=sottoalbero.dx;
            } else sottoalbero=sottoalbero.cx;
        }
        return sottoalbero.getChiave();
    }

    private boolean figlioUnico (Tree23_struct<V,K> node) {

        if (node.sx!=null^node.cx!=null^node.dx!=null) {
            
            return true;

         } else return false;

    }

    private Boolean available (Tree23_struct<V,K> node){

        if(!node.isFoglia && node.dx==null) {

            return true;

        } else return false;

    }

    
    public void add(V elem, K key) {
       
        Tree23_struct<V,K> tmp = new Tree23_struct<V,K>(elem, key);

        if (radice==null) {
            radice=tmp;
        } else {
            insert(radice, tmp);
        }
        return;
    }

    private Tree23_struct<V,K> insert (Tree23_struct<V,K> root, Tree23_struct<V,K> nodo) {

        if (root.sx.isFoglia) { //significa che siamo al livello delle foglie, questo root è l'ipotetico padre di nodo
            //vediamo se dispo
            if(available(root)){
                set(root, nodo);
            } else {
                split(root, nodo);
            }
        }
        //confrontiamo tramite la chiave k fino allo spot
        if (nodo.getChiave().compareTo(root.intermedio[0])<=0)return insert(root.sx, nodo); 
        if (nodo.getChiave().compareTo(root.intermedio[1])>0) return insert(root.dx, nodo);
        else return insert(root.cx, nodo);
        
    }

    private Tree23_struct<V,K> split(Tree23_struct<V,K> nodo, Tree23_struct<V,K> input) {

        //creiamo un nuovo nodo ove salvare i due sottoalberi che splittiamo
        Tree23_struct<V,K> nuovo = new Tree23_struct<V,K>(null, null);
        if (input.getChiave().compareTo(nodo.cx.getChiave())>0) {

            //dato che lo split avviene solo in sottoalberi con 3 sottoalberi, se il nodo che vogliamo inserire è maggiore di quello centrale del luogo individuato
            //allora prenderemo il nodo di destra più quello nuovo per fare lo split
            setFigli(nuovo, nodo.dx, input);
            nodo.dx=null;

        } else if (input.getChiave().compareTo(nodo.cx.getChiave()) < 0) {
            //viene da sè che se è minore del centrale avviene il contrario
            setFigli(nuovo, nodo.sx, input);
            nodo.sx=null;
            nodo.sx= min(nodo.cx, nodo.dx);
            nodo.cx=nodo.dx;
            nodo.dx=null;
        }
        setIntermedio(nodo);
        //vogliamo cercare un padre al nodo creato in seguito allo split
        //se il padre del nodo che abbiamo rubato dall'albero con 3 sottoalberi 
        //è disponibile ci accasiamo da lui e ringraziamo 
        if (available(nodo.padre)) {
            //grazie papi <3
            nuovo.padre=nodo.padre;

            //se invece quello a cui vogliamo prendere il padre è orfano come noi 
            //potremmo offrirgli innanzitutto un caffè sospeso per la gentilezza
        } else if (nodo.padre==null) {
            //costruiamoci una nuova famiglia tutti insieme <3
            Tree23_struct<V,K> new_padre = new Tree23_struct<V,K>(null, null);
            //nostro papi robot ospiterà entrambi
            setFigli(new_padre, nodo, nuovo);

        } else {
            //se il padre di nodo è occupato allora si procede con l'esproprio del figlio a noi più vicino
            //si richiama ricorsivamente la procedura di split in quanto si è ripresentata di fatto la situazione iniziale
            return split(nodo.padre, nuovo);

        }
        //l'algoritmo ritorna la testa dell'albero considerato
        return nodo;

    }

    private Tree23_struct<V,K> min (Tree23_struct<V,K> nodo1, Tree23_struct<V,K> nodo2) {
        if (returnMax(nodo1).compareTo(returnMax(nodo2))>0) {
            return nodo2;
        } else return nodo1;
    }

    

    public V remove(K key) {
        V ritorno = get(key);
        destroy(radice, key);
        return ritorno;
    }

    private Tree23_struct<V,K> delete (Tree23_struct<V,K> nodo, K chiave) {


        if(chiave==nodo.dx.getChiave()) {

            nodo.dx=null;

            nodo.intermedio[1]=null;

        } else if (chiave==nodo.sx.getChiave()) {

            nodo.sx=nodo.cx; nodo.cx=nodo.dx; nodo.dx=null;
            nodo.intermedio[0]= returnMax(nodo.sx);
            nodo.intermedio[1]=null;

        } else if (chiave==nodo.cx.getChiave()) {

            nodo.cx=nodo.dx; nodo.dx=null;
            nodo.intermedio[0]= returnMax(nodo.sx);
            nodo.intermedio[1]=null;


        } if (nodo==null||chiave==null) return null;

        else return nodo;

    }

    private Tree23_struct<V,K> destroy(Tree23_struct<V,K> root, K nodo) {
        //ci troviamo nel nodo intermedio che contiene nodo
            if (root.sx.isFoglia) {
                root=delete(root, nodo);
                fix(root);
            } 
    
            //trovare il nodo da rimuovere
            if (nodo.compareTo(root.intermedio[0])<=0) return destroy(root.sx, nodo); 
            if (nodo.compareTo(root.intermedio[1])>0) return destroy(root.dx, nodo);
            else return destroy(root.cx, nodo);
    }

    private Tree23_struct<V,K> fix (Tree23_struct<V,K> nodo) {

         //manca implementazione degli intermedi fatta bene, controlla
        if (figlioUnico(nodo)) {
            Tree23_struct<V,K> padre=nodo.padre;
            Tree23_struct<V,K> non_nullo=new Tree23_struct<V,K>(null, null);
            if(nodo.sx==null) non_nullo=nodo.cx;
            else if(nodo.cx==null) non_nullo=nodo.sx;
            else non_nullo=nodo.dx;

            if (nodo==padre.sx) {
                //furto dx: mi trovo in sottoalbero sx e rubo il minimo da cx
                if(!available(padre.cx)) {
                    nodo.dx=padre.cx.sx;
                    setFigli(padre, padre.cx, padre.dx);
                    //ordiniamo il nodo di sinistra, quello che si è arrubbato il nodo
                    if(nodo.sx==null) {

                        nodo.sx=nodo.cx; nodo.cx=nodo.dx; nodo.dx=null; //rubando da destra son sicuro di rubare un nodo più grosso
                        

                    } else if (nodo.cx==null) {

                        nodo.cx=nodo.dx; nodo.dx=null;
                        
                    }
                    return nodo;

                } else { //il nodo centrale ha solo due elementi: procedo con la fusione
                    set(padre.cx, non_nullo); //prendo elemento dalla carcassa dell'altro e infilo 
                    padre.sx=padre.cx;
                    padre.cx=padre.dx;
                    padre.dx=null; // ordino di modo da lasciare libero a destra come al solito

                    return fix(padre);
                     
                }
            } else if (nodo==padre.cx) {
                //furto sx: sto al centro e rubo a sinistra se possibile
                if(!available(padre.sx)) {
                    nodo.dx=padre.sx.dx;
                    padre.sx.dx=null;
                    //rubo da sx, so di rubare un elemento più piccolo di quelli che già ho
                    if(nodo.sx==null) {

                        nodo.sx=nodo.dx; nodo.dx=null;

                    } else if (nodo.cx==null) {

                        nodo.cx=nodo.sx; nodo.sx=nodo.dx; nodo.dx=null;

                    }
                    return nodo;

                } //ramo else sarebbe l'unione con il nodo di sinistra, vediamo prima se possibile 
                //fare uno split con quello di destra, rubo da destra per portare a sx
                else if (!available(padre.dx)){

                    nodo.dx=padre.dx.sx; // da destra arriva sicuro più grosso del presente
                    setFigli(padre.dx, padre.dx.cx, padre.dx.dx);
                    setFigli(nodo, nodo.dx, non_nullo);

                    return nodo;

                } else {
                    //se va male anche questo tentativo procedo con l'unificazione dei due
                    nodo.cx=padre.dx.sx;
                    nodo.dx=padre.dx.cx;
                    padre.dx=null;
                    return fix(padre);

                }
            } else if (nodo==padre.dx) {

                if (!available(padre.cx)) {
                    //rubo dal centro e porto a destra, rubo nodo di sicuro più piccolo del presente
                    setFigli(nodo, non_nullo, padre.cx.dx);
                    padre.cx.dx=null;
                    return nodo;

                } else {

                    set(padre.cx, non_nullo);
                    padre.dx=null;
                    return fix(padre);

                }
            }
        } else return nodo;

        //messo dalla quick fix, io non ho colpe ragazzi
        return nodo;

    }

    
    public boolean contains(K key) {
        if (search(radice, key)!=null) return true;
        else return false;
    }

    private Tree23_struct<V,K> search(Tree23_struct<V,K> root, K key) {

        if (root==null) return null;
        Tree23_struct<V,K> v = root;
        if (v.isFoglia) {

            if (v.getChiave()==key) {
                return v;
            } else return null;

        } else {
            if (key.compareTo(root.intermedio[0])<0) {
                return search(root.sx, key);
            } else if (key.compareTo(root.intermedio[0])>0 && ((root.intermedio[1]==null)||key.compareTo(root.intermedio[1])<0)) {
                return search(root.cx, key);
            } else //if (root.intermedio[1]!=null && key.compareTo(root.intermedio[1])>0) 
                return search(root.dx, key);

        }
    }


    public int size() {
        return dimension(radice);
    }

    private int dimension(Tree23_struct<V,K> root) {
        if (root==null) return 0;
        else if (root.isFoglia) return 1;
        else return (dimension(root.sx)+ dimension(root.cx)+ dimension(root.dx));
    }

    public V get(K key) {
        Tree23_struct<V,K> ajo = search(radice, key);
        return ajo.getElement();

    }

    
    public V[] toArray() {
        V peppe= radice.getElement();
        V[] carlo = (V[]) Array.newInstance(peppe.getClass(), 2);
        visit(radice, carlo, 0);
        return carlo;
        
    }

    private void visit (Tree23_struct<V,K> root, V[] nodi, int i) {
        if (root.sx.isFoglia) {
            nodi[i]=root.sx.getElement();
            nodi[i+1]=root.cx.getElement();
            if (root.dx.isFoglia) {
                nodi[i+2]=root.dx.getElement();
                return;
            } else if (root.dx==null) return;
        } else {
            if (root.dx!=null ){
                visit(root.sx, nodi, i++);
                visit(root.cx, nodi, i++);
                visit(root.dx, nodi, i++);
            } else {
                visit(root.sx, nodi,i++);
                visit(root.cx, nodi,i++);
            }
        }
    }

    
    private void visit_nodi (Tree23_struct<V,K> root, Tree23_struct<V,K>[] nodi, int i ) {
        
        if (root.sx.isFoglia) {
            nodi[i]=root.sx;
            nodi[i+1]=root.cx;
            if (root.dx.isFoglia) {
                nodi[i+2]=root.dx;
                return;
            } else if (root.dx==null) return;
        } else {
            if (root.dx!=null ){
                visit_nodi(root.sx, nodi, i++);
                visit_nodi(root.cx, nodi, i++);
                visit_nodi(root.dx, nodi, i++);
            } else {
                visit_nodi(root.sx, nodi,i++);
                visit_nodi(root.cx, nodi,i++);
            }
        }

    }
    

    public boolean isEmpty() {
        if (radice==null) return true;
        else return false;
    }


    public void clear() {
        radice=null;
        return;
    }

    
    public Map<V,K> swap() {

        Map<V,K> albero = new AVL<V,K>();
        Tree23_struct<V, K>[] nodi = (Tree23_struct<V, K>[]) Array.newInstance(radice.getClass(), size());
        visit_nodi(radice, nodi, 0);
        
        for (int i=0; i<nodi.length;i++) {
            albero.add(nodi[i].getElement(), nodi[i].getChiave());
        }
        return albero;
    }

}
