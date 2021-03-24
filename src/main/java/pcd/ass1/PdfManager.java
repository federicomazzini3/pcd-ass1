package pcd.ass1;

import java.util.ArrayList;

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
	
	public PdfManager (PdfFile files, ToIgnore toIgnore, Counter counter) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.workers = new ArrayList<PdfWorker>();
	}
	
	public void run() {
		int n = Runtime.getRuntime().availableProcessors();
		log("Creo "+n+" Workers"); 
		
		for(int i = 0; i <= n; i++) {
			PdfWorker pdfWorker = new PdfWorker(files, counter, toIgnore);
			pdfWorker.start();
			workers.add(pdfWorker);
		}
		
	}
	
	public ArrayList<PdfWorker> getWorkers(){
		return this.workers;
	}
	
	public void log(String s) {
		System.out.println("[Pdf Manager] " + s);
	}
}
