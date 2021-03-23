package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Thread che gestisce l'analisi dei file pdf.
 * Per ogni file pdf istanzia un nuovo processo che analizza il file pdf.
 * 
 * TODO: scalabilità con riguardo al numero dei processori e alla disponibilità cpu
 */

public class PdfManager extends Thread{

	private PdfFile files;
	private ToIgnore toIgnore;
	private Counter counter;
	private ArrayList<PdfWorker> workers;
	private ArrayList<Occurrence> occurrenciesList;
	
	public PdfManager (PdfFile files, ToIgnore toIgnore, Counter counter) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.workers = new ArrayList<PdfWorker>();
		this.occurrenciesList = new ArrayList<Occurrence>();
	}
	
	public void run() {
		int n = Runtime.getRuntime().availableProcessors();
		
		for(int i = 0; i <= n; i++) {
			PdfWorker pdfWorker = new PdfWorker(files,counter, toIgnore);
			pdfWorker.start();
			workers.add(pdfWorker);
		}
		
		while(true) {
			refreshOccurrenciesList(counter.getOccurrencies());
		}		
	}

	/*
	 * restituisce le n occorrenze pi� ripetute
	 */
	public List<Occurrence> getFirstN(int n) {
		Collections.sort(occurrenciesList);
		return occurrenciesList.stream().limit(n).collect(Collectors.toList());
	} 
	
	/*
	 * ricalcolo l'arraylist delle occorrenze
	 */
	private void refreshOccurrenciesList(Map<String, Integer> occurrencies) {
		ArrayList<Occurrence> newOccurrencies = new ArrayList<Occurrence>();
		for (String name : occurrencies.keySet()) {
			String key = name.toString();
			int value = occurrencies.get(name);
			newOccurrencies.add(new Occurrence(key, value));
		}
		this.occurrenciesList = newOccurrencies;
	}
	
	
	public ArrayList<PdfWorker> getWorkers(){
		return this.workers;
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
