package pcd.ass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 *  Inizializzato con numero di parole da estrarre dal conteggio
 *  Ritorna il risultato finale
 *  Al termine dell'elaborazione dei Worker, il suo compito � quello di 
 *  recuperare i dati e elaborare il risultato finale
 */

public class SinkAgent extends Thread{
	
	private int numberOfWords;
	private Counter counter;
	private Chrono chrono;
	private StopFlag stopFlag;
	
	public SinkAgent(Counter counter, int words, StopFlag stopFlag, Chrono chrono) {
		this.counter = counter;
		this.numberOfWords = words;	
		this.stopFlag = stopFlag;
		this.chrono = chrono;
	}
	
	public void run() {
		while(!stopFlag.check()) {
			log("Attendo risultati...");
			Map<String, Integer> occ = counter.getOccurrencies();
			int numberOfProcessedWords = this.counter.getProcessedWords();
			
			List<Occurrence> occurrencies = createOccurrencesList(occ, numberOfWords);
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
