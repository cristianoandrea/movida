package movida.cristianopalomba;

import java.util.ArrayList;
import java.util.LinkedList;

import movida.commons.Collaboration;
import movida.commons.Person;
import movida.cristianopalomba.mappe.*;

public class grafo {
    
    private ArrayList<LinkedList<Collaboration>> lista_adiacenza;
    private ArrayList<Person> nodi;
    private ArrayList<Collaboration> archi;

    public grafo (Map<Person, String> database_attori) {
        /*
        nodi = database_attori.toArray();
        for (int i=0; i<nodi.length; i++) {
            lista_adiacenza.add(new LinkedList<Person>().add(nodi[i]));
        }*/
        lista_adiacenza=null;
        nodi=null;
        archi=null;
        
    }

    public void add (Collaboration lavoro) {

        if (nodi.contains(lavoro.getActorA()) && nodi.contains(lavoro.getActorB())){
            //se l'elemento A della collab che passo in input è già presente ne cerco l' indice nell'array di nodi:
            //all'ordine dei nodi nell'arraylist corrisponde l'ordine dei nodi nell'arraylist di liste
            int index_nodoA = nodi.indexOf(lavoro.getActorA());
            int index_nodoB = nodi.indexOf(lavoro.getActorB());
            //quanto voglio vedere ora è se nella lista sopra indicata è presente una collab con B, 
            //se sì mi limito ad aggiungere il film alla collab
            boolean aggiuntoA=false;
            boolean aggiuntoB=false;
            for (int i=0; i<lista_adiacenza.get(index_nodoA).size(); i++) {

                //il get i mi serve a scorrere la lista delle varie collaborazioni e vedere se in i è presente il doppione
                if (lista_adiacenza.get(index_nodoA).get(i).getActorA()==lavoro.getActorB()) {
                    lista_adiacenza.get(index_nodoA).get(i).addMovie(lavoro.getMovie());
                    aggiuntoA=true;
                } else if (lista_adiacenza.get(index_nodoA).get(i).getActorB()==lavoro.getActorB()) {
                    lista_adiacenza.get(index_nodoA).get(i).addMovie(lavoro.getMovie());
                    aggiuntoA=true;

                } 
            }    
            if (!aggiuntoA){ //aggiungi in coda alla lista di actorA se la ricerca di prima ha dato esito negativo
                lista_adiacenza.get(index_nodoA).add(lavoro);
            }
            
            for (int i=0; i<lista_adiacenza.get(index_nodoB).size(); i++) {

                //il get i mi serve a scorrere la lista delle varie collaborazioni e vedere se in i è presente il doppione
                if (lista_adiacenza.get(index_nodoB).get(i).getActorA()==lavoro.getActorA()) {
                    lista_adiacenza.get(index_nodoB).get(i).addMovie(lavoro.getMovie());
                    aggiuntoB=true;
                } else if (lista_adiacenza.get(index_nodoB).get(i).getActorB()==lavoro.getActorA()) {
                    lista_adiacenza.get(index_nodoB).get(i).addMovie(lavoro.getMovie());
                    aggiuntoB=true;

                } 
            }    
            if (!aggiuntoB){ //aggiungi in coda alla lista di actorA se la ricerca di prima ha dato esito negativo
                lista_adiacenza.get(index_nodoB).add(lavoro);
            }

        } else {

            if (!nodi.contains(lavoro.getActorA())) {
                nodi.add(lavoro.getActorA());
                LinkedList<Collaboration> nuovo_nodo = new LinkedList<Collaboration>();
                nuovo_nodo.add(lavoro);
                lista_adiacenza.add(nuovo_nodo);
            } else {
                int index_nodo = nodi.indexOf(lavoro.getActorA());
                lista_adiacenza.get(index_nodo).add(lavoro);
            }


            if (!nodi.contains(lavoro.getActorB())) {
                nodi.add(lavoro.getActorB());
                LinkedList<Collaboration> nuovo_nodo = new LinkedList<Collaboration>();
                nuovo_nodo.add(lavoro);
                lista_adiacenza.add(nuovo_nodo);
            } else {
                int index_nodo = nodi.indexOf(lavoro.getActorB());
                lista_adiacenza.get(index_nodo).add(lavoro);
            }
            
            archi.add(lavoro);
        
        }
    return;
    }

    public LinkedList<Collaboration> getList (Person actor) {

        int indice = nodi.indexOf(actor);
        return lista_adiacenza.get(indice);
        
    }
    


}
