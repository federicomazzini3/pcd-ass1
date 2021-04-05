package pcd.ass1.Controller.Agents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * Classe di cui ogni worker dispone un istanza la quale è di supporto
 * per il calcolo delle occorrenze e delle parole elaborate
 */

public class TextReader {

	private Map<String,Integer> localCounter;
	private HashSet<String> toIgnoreWords;
	private int processedWords; 
	
	public TextReader(HashSet<String> toIgnoreWords) { 
		this.toIgnoreWords = new HashSet<>(toIgnoreWords);
		this.processedWords = 0;
	}

	public Map<String,Integer> getOccurrences(String document) {
		refresh();
		
        String filteredDocument = document.replaceAll("[|;:,_.*=?!/<()'\"<-].", " ");

		StringTokenizer doc = new StringTokenizer(filteredDocument);
		
		while (doc.hasMoreTokens()) {
			this.processedWords++;
			String word = doc.nextToken().toLowerCase();
			
			if(!toIgnore(word))
				this.addLocalOccurrence(word);
		}
		return localCounter;
	}
	
	
	public int getProcessedWord() {
		return this.processedWords;
	}
	
	private void addLocalOccurrence(String word) {
		this.localCounter.merge(word, 1, Integer::sum);
	}
	
	
	private boolean toIgnore(String word) {
		return toIgnoreWords.contains(word);
	}
	
	private void refresh() {
		this.processedWords = 0;
		this.localCounter = new HashMap<String,Integer>();
	}
}
