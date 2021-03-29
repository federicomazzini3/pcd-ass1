package pcd.ass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Agente il cui compito è quello di rimanere in ascolto per gli aggiornamenti
 * riguardanti le occorrenze ed il numero di parole processati.
 * Una volta ottenuti li elabora e richiede l'aggiornamento alla view tramite l'accodamento del task all'EDT
 */

public class SinkAgent extends Thread{
	
	private int numberOfWords;
	private Counter counter;
	private Chrono chrono;
	private View view;
	private Flag flag;
	
	public SinkAgent(Counter counter, int words, Chrono chrono, Flag stopFlag, View view) {
		this.counter = counter;
		this.numberOfWords = words;		
		this.chrono = chrono;		
		this.flag = stopFlag;
		this.view = view;
		this.setName("Sink Agent");
	}
	
	public void run() {
		while(!flag.isStop()) {
			log("Attendo risultati...");
			
			Map<String, Integer> occ = counter.getOccurrences();
			int numberOfProcessedWords = this.counter.getProcessedWords();
			List<Occurrence> occurrencies = createOccurrencesList(occ, numberOfWords);

			flag.isStop();
			
			if(!flag.isReset()) {
				view.updateCountValue(numberOfProcessedWords);
				view.updateOccurrencesLabel(occurrencies);
			}

			log("Stampo risultati");
			printResult(occurrencies, numberOfProcessedWords);
			log("Completato in:" + chrono.getTime());	
		}		
	}
	
	public void printResult(List<Occurrence> occ, int numberOfWords) {
		for (Occurrence o : occ) {
			print(" - " + o.getWord() + " " + o.getCount());
		}

		print(" - " +"Parole processate: "+numberOfWords);
	}
	
	/*
	 * ricalcolo l'arraylist delle occorrenze
	 */
	private List<Occurrence> createOccurrencesList(Map<String, Integer> occurrencies, int n) {
		ArrayList<Occurrence> newOccurrencies = new ArrayList<Occurrence>();
		for (String name : occurrencies.keySet()) {
			String key = name.toString();
			int value = occurrencies.get(name);
			newOccurrencies.add(new Occurrence(key, value));
		}
		Collections.sort(newOccurrencies);
		return newOccurrencies.stream().limit(n).collect(Collectors.toList());
	}
	
	private void print(String s) {
		System.out.println(s);
	}
	
	private void log(String s) {
		System.out.println("[Sink Agent] " + s);
	}

}
