package pcd.ass1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TextReader {

	private Map<String,Integer> localCounter;
	private ArrayList<String> toIgnoreWords;
	private int processedWord; 
	
	public TextReader() { 
		this.processedWord = 0;
	}
	
	public int getProcessedWord() {
		return this.getProcessedWord();
	}
	
	public void setToIgnoreWord(ArrayList<String> words) {
		this.toIgnoreWords= words;
	}

	public Map<String,Integer> getOccurrences(String document) {
		refresh();
		
        String filteredDocument = document.replaceAll("[|;:,”'\"].", " ");
		StringTokenizer doc = new StringTokenizer(filteredDocument);
		
		while (doc.hasMoreTokens()) {
			processedWord++;
			String word = doc.nextToken().toLowerCase();
			
			if(!toIgnore(word))
				this.addLocalOccurrence(word);
		}
		return localCounter;
	}
	
	private void addLocalOccurrence(String word) {
		this.localCounter.merge(word, 1, Integer::sum);
	}
	
	
	private boolean toIgnore(String word) {
		return toIgnoreWords.contains(word);
	}
	
	private void refresh() {
		this.processedWord = 0;
		this.localCounter = new HashMap<String,Integer>();
	}

	private void log() {
		System.out.println("Saluti da TextReader");
	}
}
