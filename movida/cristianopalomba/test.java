/*
package movida.cristianopalomba;

import java.io.File;
import java.util.Scanner;

import movida.commons.MapImplementation;
import movida.commons.Person;
import movida.commons.SortingAlgorithm;
import movida.cristianopalomba.MovidaCore;

public class test {
    static MovidaCore m;

    private static void scelta_interfacce() {
        Scanner S = new Scanner(System.in);
        boolean esci = false;
        System.out.println("Il programma mette a tua disposizione tre interfacce ognuna con vari metodi: \n");
        System.out.println("IMovidaDB = 1 \n");
        System.out.println("IMovidaSearch = 2 \n");
        System.out.println("IMovidaCollaboration= 3 \n");
        System.out.println("Scegli a quale intefaccia accedere digitando il numero associato ad essa \n");
        System.out.println("Digita qualsiasi altro numero per terminare \n");
        while (!esci) {
            int input = S.nextInt();
            if (input == 1) {
                mostraDB();
            } else if (input == 2) {
                mostraSearch();
            } else if (input == 3) {
                mostraCollaboration();
            } else {
                esci = true;
            }
        }
        System.out.println("Grazie per aver utilizzato il programma dei GLADIATORI DELLA GIUSTIZIA");
        return;
    }


    private static void mostraDB() {
        System.out.println("l'interfaccia DB si compone dei seguenti metodi: \n");
        System.out.println("saveToFile = 1 \n");
        System.out.println("clear = 2 \n");
        System.out.println("countMovies = 3 \n");
        System.out.println("countPeople = 4 \n");
        System.out.println("deleteMovieByTitle = 5 \n");
        System.out.println("getMovieByTitle = 6 \n");
        System.out.println("getPersonByName = 7 \n");
        System.out.println("getAllMovies = 8 \n");
        System.out.println("getAllPeople = 9 \n");
        System.out.println("per tornare alla scelta dell'interfaccia digita qualsiasi altro numero \n");
        Scanner S = new Scanner(System.in);
        int input = S.nextInt();
        if (input == 1) {
            //savetofile
        } else if (input == 2) {
            m.clear();
        } else if (input == 3) {
            m.countMovies();
        } else if (input == 4) {
            m.countPeople();
        } else if (input == 5) {
            System.out.println("Inserisci il titolo del film che vuoi eliminare: \n");
            String titolo = S.nextLine();
            m.deleteMovieByTitle(titolo);
        } else if (input == 6) {
            System.out.println("Inserisci il titolo del film che vuoi ottenere: \n");
            String titolo = S.nextLine();
            m.getMovieByTitle(titolo);
        } else if (input == 7) {
            System.out.println("Inserisci il nome della persona che vuoi ottenere: \n");
            String nome = S.nextLine();
            m.getPersonByName(nome);
        } else if (input == 8) {
            m.getAllMovies();
        } else if (input == 9) {
            m.getAllPeople();
        } else scelta_interfacce();
        return;
    }


    private static void mostraSearch(){
        System.out.println("l'interfaccia Search si compone dei seguenti metodi: \n");
        System.out.println("searchMoviesByTitle = 1 \n");
        System.out.println("searchMoviesInYear = 2 \n");
        System.out.println("searchMoviesDirectedBy = 3 \n");
        System.out.println("searchMoviesStarredBy = 4 \n");
        System.out.println("searchMostVotedMovies = 5 \n");
        System.out.println("searchMostRecentMovies = 6 \n");
        System.out.println("searchMostActiveActors = 7 \n");
        System.out.println("per tornare alla scelta dell'interfaccia digita qualsiasi altro numero \n");
        Scanner S = new Scanner(System.in);
        int input = S.nextInt();
        if (input == 1) {
            System.out.println("Inserisci la parola che vuoi cercare nei titoli dei film: \n");
            String parola = S.nextLine();
            m.searchMoviesByTitle(parola);
        } else if (input == 2) {
            System.out.println("Inserisci l'anno di cui vuoi i film: \n");
            String anno = S.nextLine();
            m.searchMoviesByTitle(anno);
        } else if (input == 3) {
            System.out.println("Inserisci il nome del regista di cui vuoi i film: \n");
            String regista = S.nextLine();
            m.searchMoviesDirectedBy(regista);
        } else if (input == 4) {
            System.out.println("Inserisci l'attore di cui vuoi i film: \n");
            String attore = S.nextLine();
            m.searchMoviesStarredBy(attore);
        } else if (input == 5) {
            System.out.println("Inserisci il numero di film che vuoi ottenere: \n");
            int quantitativo = S.nextInt();
            m.searchMostVotedMovies(quantitativo);
        } else if (input == 6) {
            /*
            System.out.println("Inserisci il numero di film che vuoi ottenere: \n");
            int quantitativo = S.nextInt();
            searchMostRecentMovies(quantitativo);
            */
/*       } else if (input == 7) {
           /*
           System.out.println("Inserisci il numero di attori che vuoi ottenere: \n");
           int quantitativo = S.nextInt();
           searchMostActiveActors(quantitativo);
           */
/*     } else scelta_interfacce();
     return;
 }


 private static void mostraCollaboration(){
     System.out.println("l'interfaccia Collaboration si compone dei seguenti metodi: \n");
     System.out.println("getDirectCollaboratorsOf = 1 \n");
     System.out.println("getTeamOf = 2 \n");
     System.out.println("maximizeCollaborationsInTheTeamOf = 3 \n");
     System.out.println("per tornare alla scelta dell'interfaccia digita qualsiasi altro numero \n");
     Scanner S = new Scanner(System.in);
     int input = S.nextInt();
     if (input == 1) {
         System.out.println("Inserisci il nome dell'attore del quale vuoi ottenere le collaborazioni dirette: \n");
         String attore = S.nextLine();
         Person attore_personificato = m.getPersonByName(attore);
         m.getDirectCollaboratorsOf(attore_personificato);
     } else if (input == 2) {
         System.out.println("Inserisci il nome dell'attore del quale vuoi ottenere il team: \n");
         String attore = S.nextLine();
         Person attore_personificato = m.getPersonByName(attore);
         m.getTeamOf(attore_personificato);
     } else if (input == 3) {
         System.out.println("Inserisci il nome dell'attore del quale vuoi massimizzare la score delle collaborazioni: \n");
         String attore = S.nextLine();
         Person attore_personificato = m.getPersonByName(attore);
         m.maximizeCollaborationsInTheTeamOf(attore_personificato);
     } else scelta_interfacce();
     return;
 }

 public static void main(String args[]) {
     System.out.println("inserisci il path del file da caricare: ");
     String path_file;
     Scanner S = new Scanner(System.in);
     path_file = S.nextLine();
     // path_file->magia->file
     // dato il path vorrei ottenere un oggetto di tipo file da passare in input a loadFromFile ma non capisco come
     File t = new File(path_file);
     m.loadFromFile(t);
     MapImplementation x = m.AskMap();
     m.setMap(x);
     SortingAlgorithm y = m.AskSorting();
     m.setSort(y);
     // ora in teoria i dati sono caricati e quindi possiamo chiamare tutto cio che vogliamo
     scelta_interfacce();
     return;
 }
}
*/

package movida.cristianopalomba;

import java.io.File;

import movida.commons.MapImplementation;
import movida.commons.SortingAlgorithm;

public class test{
    public static void main(String args[]){
        String path = "/Users/andreacristiano/Desktop/VNIVERSITà/ciaooooooooooo/esempio-formato-dati.txt";
        File ao = new File(path);
        SortingAlgorithm a = SortingAlgorithm.SelectionSort;
        MapImplementation m = MapImplementation.AVL;
        MovidaCore gg = new MovidaCore(a, m);
        gg.loadFromFile(ao);
        System.out.println(gg.countMovies());
        
    }
}