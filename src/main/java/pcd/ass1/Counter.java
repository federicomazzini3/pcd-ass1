package pcd.ass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Counter{

	private Map<String, Integer> occurrencies;
	private ArrayList<Occurrence> occurrenciesList;
	private boolean available;
	private boolean modified;

	public Counter() {
		occurrencies = new HashMap<String, Integer>();
		occurrenciesList = new ArrayList<Occurrence>();
		available = false;
		modified = false;
	}

	/*
	 * merge tra le occorrenze già nell'oggetto e quelle passate in input (faccio la somma in caso le parole sono da entrambe le parti)
	 */
	public synchronized void mergeOccurrence(Map<String,Integer> mapToMerge) {
		mapToMerge.forEach((k, v) -> occurrencies.merge(k, v, Integer::sum));
		this.available = true;
		this.modified = true;
		this.notifyAll();
	}
	
	public synchronized void setOccurrenceToZero() {
		this.available = true;
		this.modified = true;
		this.notifyAll();
	}
	
	/*
	 * restituisce le n occorrenze più ripetute
	 */
	public synchronized List<Occurrence> getFirstN(int n){
		while (!available) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		if(modified)
			refreshOccurrenciesList();
		
		Collections.sort(occurrenciesList);
		return occurrenciesList.stream().limit(n).collect(Collectors.toList());
	}
	
	/*
	 * ricalcolo l'arraylist delle occorrenze
	 */
	private void refreshOccurrenciesList(){
		ArrayList<Occurrence> newOccurrencies = new ArrayList<Occurrence>();
		for (String name : this.occurrencies.keySet()) {
			String key = name.toString();
			int value = occurrencies.get(name);
			newOccurrencies.add(new Occurrence(key, value));
		}
		this.occurrenciesList = newOccurrencies;
	}
}