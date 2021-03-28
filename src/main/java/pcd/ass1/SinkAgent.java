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
	private View view;
	private FlagStop stopFlag;
	
	public SinkAgent(Counter counter, int words, Chrono chrono, FlagStop stopFlag, View view) {
		this.counter = counter;
		this.numberOfWords = words;		
		this.chrono = chrono;		
		this.stopFlag = stopFlag;
		this.view = view;
	}
	
	public void run() {
		while(true) {
			
			stopFlag.reset();
			view.setCountingState();
			while(!stopFlag.isSet()) {
				Map<String, Integer> occ = counter.getOccurrencies();
				
				view.updateCountValue(counter.getProcessedWords());
				System.out.println(counter.getProcessedWords());
				
				log("Attendo risultati...");
				int numberOfProcessedWords = this.counter.getProcessedWords();
				
				List<Occurrence> occurrencies = createOccurrencesList(occ, numberOfWords);
				log("Stampo risultati");
				printResult(occurrencies, numberOfProcessedWords);
				log("Completato in:" + chrono.getTime());	
			}
			try {
				Thread.sleep(10);
				} catch(Exception ex){
			}
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
