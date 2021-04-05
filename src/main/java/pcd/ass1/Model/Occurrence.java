package pcd.ass1.Model;

/*
 * Classe che rappresenta l'entità occorrenza intesa come (parole, numero di volte)
 */

public class Occurrence implements Comparable<Occurrence>{

	private String word;
	private Integer count;
	
	public Occurrence(String word, Integer count) {
		this.word = word;
		this.count = count;
	}

	public String getWord() {
		return this.word;
	}
	
	public Integer getCount() {
		return this.count;
	}
	
	@Override
	public int compareTo(Occurrence toCompare) {
		return toCompare.count - this.count;
	}
}
