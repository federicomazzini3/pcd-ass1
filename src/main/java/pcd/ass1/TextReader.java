package pcd.ass1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TextReader extends Thread {

	String document;
	ToIgnore toIgnore;
	Excluser excluser;
	Counter globalCounter;
	Map<String,Integer> localCounter;
	ArrayList<String> toIgnoreWords;
	
	public TextReader(String document, Counter counter, ToIgnore toIgnore) {
		this.document = document;
		this.toIgnore = toIgnore;
		this.toIgnoreWords = new ArrayList<String>();
		this.localCounter = new HashMap<String, Integer>();
		this.globalCounter = counter;
	}

	public void run() {
        String filteredDocument = document.replaceAll("[|;:,”'\"].", " ");
		StringTokenizer doc = new StringTokenizer(filteredDocument);
		toIgnoreWords = toIgnore.getToIgnoreWords();
		
		while (doc.hasMoreTokens()) {
			String word = doc.nextToken().toLowerCase();
			
			if(!toIgnore(word))
				this.addLocalOccurrence(word);
		}
		globalCounter.mergeOccurrence(localCounter);
	}
	
	private void addLocalOccurrence(String word) {
		this.localCounter.merge(word, 1, Integer::sum);
	}
	
	
	private boolean toIgnore(String word) {
		return toIgnoreWords.contains(word);
	}

	private void log() {
		System.out.println("Saluti da TextReader");
	}
}
