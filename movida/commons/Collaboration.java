package movida.commons;

import java.util.ArrayList;

public class Collaboration implements Comparable<Collaboration>{

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;
	
	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	public void addMovie (Movie film) {
		movies.add(film);
		return;
	}

	public int compareTo(Collaboration a){
		return (int) (getScore() - a.getScore());
	}

	public Movie getMovie () {
		return movies.get(0);
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore(){
		
		Double score = 0.0;
		
		for (Movie m : movies)
			score += m.getVotes();
		
		return score / movies.size();
	}
	
}
