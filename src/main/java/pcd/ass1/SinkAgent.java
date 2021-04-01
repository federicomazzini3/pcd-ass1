package pcd.ass1;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Agente il cui compito è quello di rimanere in ascolto per gli aggiornamenti
 * riguardanti le occorrenze ed il numero di parole processati.
 * Una volta ottenuti li elabora e richiede l'aggiornamento alla view tramite l'accodamento del task all'EDT
 */

public class SinkAgent extends Thread{
	
	private int wordsNumberToRetrieve;
	private Counter counter;
	private Chrono chrono;
	private View view;
	private Flag flag;
	private List<Occurrence> lastResultOccurrence;
	private int lastResultProcessedWords;
	
	public SinkAgent(Counter counter, int words, Chrono chrono, Flag stopFlag, View view) {
		this.counter = counter;
		this.wordsNumberToRetrieve = words;
		this.chrono = chrono;		
		this.flag = stopFlag;
		this.view = view;
		this.setName("Sink Agent");
	}
	
	public void run() {
		while(!flag.isStop()) {
			log("Attendo risultati...");

			Map<String, Integer> occ = new HashMap<>(counter.getOccurrences());
			lastResultProcessedWords = counter.getProcessedWords();
			lastResultOccurrence = createOccurrencesList(occ, wordsNumberToRetrieve);

			flag.isStop();
			if(!flag.isReset()) {
				view.updateCountValue(lastResultProcessedWords);
				view.updateOccurrencesLabel(lastResultOccurrence);
			}

			log("Stampo risultati");
			printResult(lastResultOccurrence, lastResultProcessedWords);
			log("Completato in:" + chrono.getTime());	
		}		
	}
	
	public void printResult(List<Occurrence> occ, int wordsNumber) {
		for (Occurrence o : occ) {
			print(" - " + o.getWord() + " " + o.getCount());
		}
		print(" - " +"Parole processate: "+wordsNumber);
	}
	
	/*
	 * ricalcolo l'arraylist delle occorrenze
	 */
	private List<Occurrence> createOccurrencesList(Map<String, Integer> occ, int n) {
		ArrayList<Occurrence> occurrences = new ArrayList<Occurrence>();
		for (String name : occ.keySet()) {
			String key = name.toString();
			int value = occ.get(name);
			occurrences.add(new Occurrence(key, value));
		}
		Collections.sort(occurrences);
		return occurrences.stream().limit(n).collect(Collectors.toList());
	}
	
	private void print(String s) {
		System.out.println(s);
	}
	
	private void log(String s) {
		System.out.println("[Sink Agent] " + s);
	}

}
