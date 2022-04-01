/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package movida.commons;


/**
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 * 
 * Una persona � identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * Semplificazione: <code>name</code> � usato per memorizzare il nome completo (nome e cognome)
 * 
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 * 
 */
public class Person implements Comparable<Person> {

	private String name;
	private int numero_film;
	
	
	public Person(String name) {
		this.name = name;
		numero_film=1;
	}

	public int compareTo (Person a) {
		return getFilm() - a.getFilm();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void addFilm() {
		numero_film++; 
		return;
	}

	public int getFilm(){
		return numero_film;
	}

}
