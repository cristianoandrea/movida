package movida.cristianopalomba;

import movida.commons.*;
import movida.cristianopalomba.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaCollaborations{
    
    private SortingAlgorithm ordinamento;
    private MapImplementation mappa;
    
    private Map<Movie, String> database_film;
    private Map<Person, String> database_actor;
    private Map<Person, String> database_director;

    private grafo grafocollaborazioni;

    public MovidaCore (SortingAlgorithm a, MapImplementation m) {
        setSort(a);
        setMap(m);
        if(mappa == MapImplementation.AVL){
            database_film = new AVL<Movie,String>();
            database_actor = new AVL<Person,String>();
            database_director = new AVL<Person,String>();
        } else if(mappa == MapImplementation.Alberi23){
            database_film = new Tree23<Movie, String>();
            database_actor = new Tree23<Person,String>();
            database_director = new Tree23<Person,String>();
        }
    }


    public void loadFromFile(File f) throws MovidaFileException{
        //prova aprire
        //leggi file txt
        //crea un oggetto movie con quello che ittieni
        //aggiungi film
        try{
            if(f.exists()){
                Scanner reader = new Scanner(f);
                while(reader.hasNextLine()){
                    String riga = null;
                    String titolo = null;
                    Integer anno = null;
                    Person director = null;
                    Person[] cast = new Person[5];
                    Integer voti = null;
                    riga = reader.nextLine();
                    while(riga.length() > 2){
                        if(riga.contains("Title: ")){
                            titolo = riga.substring(7, riga.length()-1);
                        } else if(riga.contains("Year: ")){
                            String year = riga.substring(6, riga.length()-1);
                            char[] ao = year.toCharArray();
                            anno = StringToInt(ao);

                        } else if(riga.contains("Cast: ")){

                            String nuovo_attore = "";
                            //int k = 0;
                            int j = 0;

                            for(int i = 6; i < riga.length(); i++){

                                if(riga.charAt(i) != ',' && i != riga.length()){

                                    nuovo_attore += riga.charAt(i);
                                    //k++;

                                } else {

                                    cast[j] = new Person(nuovo_attore);
                                    j++;
                                    i++;
                                    //k = 0;
                                    nuovo_attore = "";

                                }
                            }
                            cast[j] = new Person(nuovo_attore);
                        } else if(riga.contains("Director: ")){
                            director = new Person(riga.substring(10));
                        } else if(riga.contains("Votes: ")){
                            String votes = riga.substring(7);
                            char[] ao = votes.toCharArray();
                            voti = StringToInt(ao);
                        }
                        riga = reader.nextLine();
                    }
                    Movie film = new Movie(titolo, anno, voti, cast, director);
                    if(!database_film.contains(titolo)){
                        database_film.add(film, titolo);
                    }
                    for(int e = 0; e < cast.length; e++){
                        for(int j = 0; j < cast.length; j++){
                            if(e != j){
                                Collaboration lavoro = new Collaboration(cast[e], cast[j]);
                                lavoro.addMovie(film);
                                grafocollaborazioni.add(lavoro);
                            }
                        }
                        if(!database_actor.contains(cast[e].getName())){
                            database_actor.add(cast[e], cast[e].getName());
                        } else database_actor.get(cast[e].getName()).addFilm();
                    } 
                    if(!database_director.contains(director.getName())){
                        database_director.add(director, director.getName());
                    } else database_director.get(director.getName()).addFilm();
                }
            }
        } catch (Exception e){
            throw new MovidaFileException();
            //e.printStackTrace();
        }
    }


    public void saveToFile(File f) throws MovidaFileException{
        try{
            Movie[] eminem = database_film.toArray();
            FileWriter neruda = new FileWriter(f);
            for(int i = 0; i < eminem.length; i++){
                neruda.write("Title: "); 
                neruda.write(eminem[i].getTitle());
                neruda.write("\n Year: ");
                neruda.write(eminem[i].getYear());
                neruda.write("\n Director: ");
                neruda.write(eminem[i].getDirector().getName());
                neruda.write("\n Cast: ");
                for(int j = 0; j < eminem[i].getCast().length-1; j++){
                    neruda.write(eminem[i].getCast()[j].getName());
                    if(j != eminem[i].getCast().length-1) neruda.write(", ");
                }
                neruda.write("\n Votes: ");
                neruda.write(eminem[i].getVotes());
                neruda.write("\n");
            }
            return;
        } catch(Exception e){
            throw new MovidaFileException();
        }
    }


    public void clear(){
        database_film.clear();
        database_actor.clear();
        database_director.clear();
        return;
    }


    public int countMovies(){
        return database_film.size();
    }


    public int countPeople(){
        return database_actor.size() + database_director.size();
    }


    public boolean deleteMovieByTitle(String title){
        if(database_film.remove(title) != null) return true;
        return false;
    }


    public Movie getMovieByTitle(String title){
        return database_film.get(title);
    }


    public Person getPersonByName(String name){
        return database_actor.get(name);
    }


    public Movie[] getAllMovies(){
        return database_film.toArray();
    }


    public Person[] getAllPeople(){
        Person[] attori = database_actor.toArray();
        Person[] direttori = database_director.toArray();
        int length = attori.length + direttori.length;
        Person[] personaggi = new Person[length];
        for(int i = 0; i < attori.length; i++){
            personaggi[i] = attori[i];
        }
        for(int f = 0; f < direttori.length; f++){
            personaggi[f+attori.length] = direttori[f];
        }
        return personaggi;

    }


    private int StringToInt (char[] A) {
        int somma=0;
        for(int i = 0; i<A.length; i++) {
            somma += (int) (A[i] * Math.pow(10, A.length-i));
        }
        return somma;
    }


    public boolean setSort(SortingAlgorithm a){
        if(a != SortingAlgorithm.QuickSort && a != SortingAlgorithm.SelectionSort){
            return false;
        } else {
            if(a == ordinamento) return false;
            else{
                ordinamento = a;
                return true;
            }
        }
    }


    public boolean setMap(MapImplementation m){
        if(m != MapImplementation.AVL && m != MapImplementation.Alberi23){
            return false;
        } else {
            if(m == mappa) return false;
            else{
                mappa = m;
                return true;
            }
        }
    }
    

    public Person[] getDirectCollaboratorsOf(Person actor){
        LinkedList<Collaboration> lista_attore = grafocollaborazioni.getList(actor);
        Person[] diretti = new Person[lista_attore.size()];
        for(int i = 0; i < lista_attore.size(); i++){
            if(lista_attore.get(i).getActorA() == actor){
                diretti[i] = lista_attore.get(i).getActorB();
            } else if(lista_attore.get(i).getActorB() == actor){
                diretti[i] = lista_attore.get(i).getActorA();
            }
        }
        return diretti;
    }


    public Person[] getTeamOf(Person actor){
        ArrayList<Person> visitati = new ArrayList<Person>();
        Queue<Person> da_visitare = new LinkedList<Person>();
        Person in_visita = actor;
        da_visitare.add(in_visita);
        while(!da_visitare.isEmpty()){
            LinkedList<Collaboration> lista_attore = grafocollaborazioni.getList(actor);
            for(int i = 0; i < lista_attore.size(); i++){
                if(lista_attore.get(i).getActorA() == in_visita){
                    if(!visitati.contains(lista_attore.get(i).getActorB()) && !da_visitare.contains(lista_attore.get(i).getActorB())){
                        da_visitare.add(lista_attore.get(i).getActorB());
                    }
                } else if(lista_attore.get(i).getActorB() == in_visita){
                    if(!visitati.contains(lista_attore.get(i).getActorA()) && !da_visitare.contains(lista_attore.get(i).getActorA())){
                        da_visitare.add(lista_attore.get(i).getActorA());
                    }
                }
            }
            visitati.add(in_visita);
            in_visita = da_visitare.peek();
        }
        Person[] team = (Person[]) visitati.toArray();
        return team;
    }


    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
        Person[] team = getTeamOf(actor);
        //abbiamo quindi un array di tutte le collaborazioni del team
        ArrayList<Person> raggiunti= new ArrayList<Person>();
        ArrayList<Collaboration> mst = new ArrayList<Collaboration>();
        PriorityQueue<Collaboration> frontiera = new PriorityQueue<>();
        Person attore = actor;
        while (mst.size()<team.length) {
            LinkedList<Collaboration> lista_attore = grafocollaborazioni.getList(attore);
            for (int i=0; i<lista_attore.size();i++) {
                if ( (lista_attore.get(i).getActorA()==attore) && !(raggiunti.contains(lista_attore.get(i).getActorB()) )) {
                    frontiera.add(lista_attore.get(i));
                } else if ( (lista_attore.get(i).getActorB()==attore) && !(raggiunti.contains(lista_attore.get(i).getActorA()))) {
                    frontiera.add(lista_attore.get(i));
                }
            }
            //controlla che l'elemento migliore non sia inutile
            boolean trovato=false;
            while(!trovato) {
                if( (raggiunti.contains(frontiera.peek().getActorA())) && (raggiunti.contains(frontiera.peek().getActorB()))) {
                    frontiera.remove(frontiera.peek());
                } else {
                    raggiunti.add(attore);
                    mst.add(frontiera.peek());
                if (!raggiunti.contains(frontiera.peek().getActorA())) {
                    attore=frontiera.poll().getActorA();
                } else if (!raggiunti.contains(frontiera.peek().getActorB())) {
                    attore=frontiera.poll().getActorB();
                }
                trovato=true;
                }
            }
        }
        Collaboration[] maxCollab= (Collaboration[]) mst.toArray();
        return maxCollab;
    }
    

    public Movie[] searchMoviesByTitle(String title){
        Movie[] risultati = new Movie[50];
        Movie[] films = database_film.toArray();
        int j = 0;
        for(int i = 0; i < films.length; i++){
            if(films[i].getTitle().contains(title) && j < risultati.length){
                risultati[j] = films[i];
                j++;
            }
        }
        return risultati;
        //se non trovo film dovrei restituire un vettore vuoto
        //ma essendo che se non ne trovo non ne inserisco suppongo di star gia restituendo un array vuoto
    }


    public Movie[] searchMoviesInYear(Integer year){
        Movie[] risultati = new Movie[10];
        Movie[] films = database_film.toArray();
        int j = 0;
        for(int i = 0; i < films.length; i++){
            //String anno_stringa = films[i].getYear().toString();
            //String input_stringa = year.toString();
            if(films[i].getYear() == year && j < risultati.length){
                risultati[j] = films[i];
                j++;
            }
        }
        return risultati;
    }


    public Movie[] searchMoviesDirectedBy(String name){
        Movie[] risultati = new Movie[10];
        Movie[] films = database_film.toArray();
        int j = 0;
        for(int i = 0; i < films.length; i++){
            //String regista_stringa = films[i].getDirector().getName();
            //String input_stringa = name.toString();
            if(films[i].getDirector().getName().equals(name) && j < risultati.length){
                risultati[j] = films[i];
                j++;
            }
        }
        return risultati;
    }


    public Movie[] searchMoviesStarredBy(String name){
        Movie[] risultati = new Movie[10];
        Movie[] films = database_film.toArray();
        int j = 0;
        boolean aggiunto = true;
        for(int i = 0; i < films.length; i++){
            Person[] cricca = films[i].getCast();
            while(!aggiunto){
                for(int k = 0; k < cricca.length; k++){
                    String membro_stringato = cricca[k].getName();
                    if(membro_stringato.contains(name)){
                        risultati[j] = films[i];
                        j++;
                        aggiunto = true;
                    }
                    //una volta che trovo un match tra input e membro del cast smetto di ciclare sul cast
                    //semplicemente aggiungo il film all'array di risultati e passo al prossimo
                }
            }
        }
        return risultati;
    }


    public Movie[] searchMostVotedMovies(Integer N){
        le_risposte a = le_risposte.voti;
        Movie[] raccolta = getAllMovies();
        for(int i = 0; i < raccolta.length; i++){
            raccolta[i].setMuffin(a);
        }
        sort(raccolta, N);
        return raccolta;
    }


    public Movie[] searchMostRecentMovies(Integer N) {
        le_risposte a= le_risposte.anni;
        Movie[] raccolta=getAllMovies();
        for (int i=0; i<raccolta.length;i++) {
            raccolta[i].setMuffin(a);
        }
        sort(raccolta, N);
        return raccolta;

    }


    public Person[] searchMostActiveActors(Integer N){
        Person[] attori=database_actor.toArray();
        sort(attori, N);
        return attori;
    }

    
    public void sort(Comparable A[], int N) {
        if (N==0) return;
        else if (N>A.length) N=A.length;
        if (ordinamento==SortingAlgorithm.SelectionSort) {
            SelectionSort(A, N);
        }
        else if (ordinamento==SortingAlgorithm.QuickSort) {
            quickSort(A, N);
        }
    }


    private void SelectionSort (Comparable A[], int N) {
        Comparable P[] = new Comparable[N];
        for (int k=0; k<N; k++){
            int m = k;
            for (int j = k + 1; j < A.length; j++) {
                if (A[j].compareTo(A[m]) > 0)
                 m = j;
            }
            // scambia A[k] con A[m]
            if (m != k) {
                Comparable temp = A[m]; A[m] = A[k];
                A[k] = temp;
                P[k]=A[m];
            }
        }
        A=P;
    }


    private void quickSort(Comparable A[], int N) { 
        partial_QuickSort(A, 0, A.length, N);
        Comparable[] P = new Comparable[N];
        for (int i=0; i<N; i++) {
            P[i]=A[i];
        }
        A=P;
        return;
    }


    private static int partition(Comparable A[], int i, int f, int pivot) {
        Integer sup=i;
        Integer inf=f+1;
        while (true) {
            do {
                sup++;
            }while (sup<=f && A[sup].compareTo(A[pivot])>=0);
            do {
                inf--;
            } while (A[inf].compareTo(pivot)<0);
            if (inf<sup) {
                Comparable tmp= A[inf];
                A[inf]=A[sup];
                A[sup]=tmp;
            } else break;
        }
        Comparable tmp = A[i];
        A[i]=A[sup];
        A[sup]=tmp;
        return sup;
    }


    void partial_QuickSort (Comparable A[], int i, int f, int N){
        if (i<f) {
            Integer pivot= i + (int) Math.floor((f-i+1)*Math.random());
            //nell'array lo troveremo in A[pivot]
            partition(A, i, f, pivot);
            //in teoria crea la situazione del tipo 
            //A[i..pivot-1]>A[pivot]>A[pivot+1...f]
            partial_QuickSort(A, i, pivot-1, N);
            if(pivot<N-1) {
                partial_QuickSort(A, pivot+1, f, N);
            }
        } else return;
    }


}
