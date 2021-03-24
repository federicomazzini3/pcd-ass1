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
	
	public SinkAgent(Counter counter, int words, Chrono chrono) {
		this.counter = counter;
		this.numberOfWords = words;		
		this.chrono = chrono;
		
	}
	
	public void run() {
		while(true) {
			Map<String, Integer> occ = counter.getOccurrencies();
			
			List<Occurrence> occurrencies = createOccurrencesList(occ, 10);
			printResult(occurrencies);
			System.out.println("Completato in:" + chrono.getTime());
		}	
		
	}
	
	public static void printResult(List<Occurrence> occ) {
		for (Occurrence o : occ) {
			System.out.println(o.getWord() + " " + o.getCount());
		}
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
		return newOccurrencies.stream().limit(numberOfWords).collect(Collectors.toList());
	}

}
