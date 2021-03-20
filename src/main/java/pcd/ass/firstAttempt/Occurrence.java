package pcd.ass.firstAttempt;

public class Occurrence implements Comparable<Occurrence>{

	private String word;
	public Integer count;
	
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
