package pcd.ass1;

import java.io.File;
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
		workers = new ArrayList<PdfWorker>();
	}
	
	/*public void run() {
		/*
		 * TODO: PdfManager dovrebbe gestire lo scenario in cui non ha trovato file e quindi aggiornare lui stesso il counter con nessun risultato
		 *
		ArrayList<File> allPdfFiles = files.getAllPdfFiles();
		
		for(File file: allPdfFiles) {
			PdfWorker pdfWorker = new PdfWorker(file, counter, toIgnore);
			pdfWorker.start();
			workers.add(pdfWorker);
		}
		
		if(allPdfFiles.isEmpty()) {
			counter.setOccurrenceToZero();
			log("Nessun file pdf trovato");
		}
	}*/
	
	public void run() {
		int n = Runtime.getRuntime().availableProcessors();
		
		for(int i = 0; i <= n; i++) {
			PdfWorker pdfWorker = new PdfWorker(files,counter, toIgnore);
			pdfWorker.start();
			workers.add(pdfWorker);
		}
		
	}

	
	public ArrayList<PdfWorker> getWorkers(){
		return this.workers;
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
