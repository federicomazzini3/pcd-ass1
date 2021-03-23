package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	
	public PdfManager (PdfFile files, ToIgnore toIgnore) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = new Counter();
		this.workers = new ArrayList<PdfWorker>();
	}
	
	public void run() {
		int n = Runtime.getRuntime().availableProcessors();
		
		for(int i = 0; i <= n; i++) {
			PdfWorker pdfWorker = new PdfWorker(files, counter, toIgnore);
			pdfWorker.start();
			workers.add(pdfWorker);
		}
		
		while(true) {
			Map<String, Integer> occ = counter.getOccurrencies();
			
			List<Occurrence> occurrencies = createOccurrencesList(occ, 10);
			printResult(occurrencies);
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
		return newOccurrencies.stream().limit(n).collect(Collectors.toList());
	}
	
	
	public ArrayList<PdfWorker> getWorkers(){
		return this.workers;
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
