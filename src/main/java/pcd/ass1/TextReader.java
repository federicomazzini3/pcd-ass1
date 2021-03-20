package pcd.ass1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TextReader extends Thread {

	StringTokenizer doc;
	Excluser excluser;
	Counter globalCounter;
	Map<String,Integer> localCounter;
	ArrayList<String> toIgnoreWords;

	public TextReader(String document, Counter counter, ToIgnoreFile toExcludeFile) {
		this.doc = new StringTokenizer(document);
		this.toIgnoreWords = toExcludeFile.getToIgnoreWords();
		this.localCounter = new HashMap<String, Integer>();
		this.globalCounter = counter;
	}

	public void run() {
		while (doc.hasMoreTokens()) {
			String word = doc.nextToken();
			
			if(!toIgnore(word))
				this.addLocalOccurrence(word);
		}
		globalCounter.mergeOccurrence(localCounter);
	}
	
	private void addLocalOccurrence(String word) {
		/*if(this.localCounter.get(word) == null) {
			this.localCounter.put(word, 1);
		} else {
			int n = this.localCounter.get(word);
			this.localCounter.put(word, n+1);
		}*/
		this.localCounter.merge(word, 1, Integer::sum);
	}
	
	
	private boolean toIgnore(String word) {
		return toIgnoreWords.contains(word);
	}

	private void log() {
		System.out.println("Saluti da TextReader");
	}
}
