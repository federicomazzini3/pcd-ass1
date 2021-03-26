package pcd.ass1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TextReader {

	private Map<String,Integer> localCounter;
	private ArrayList<String> toIgnoreWords;
	private int processedWords; 
	
	public TextReader(ArrayList<String> toIgnoreWords) { 
		this.toIgnoreWords = toIgnoreWords;
		this.processedWords = 0;
	}

	public Map<String,Integer> getOccurrences(String document) {
		refresh();
		
        String filteredDocument = document.replaceAll("[|;:,�'\"-].", " ");

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
