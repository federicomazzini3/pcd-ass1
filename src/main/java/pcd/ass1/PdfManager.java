package pcd.ass1;

import java.io.File;
import java.util.ArrayList;

public class PdfManager extends Thread{

	private Files files;
	private ToIgnoreFile toExcludeFile;
	private Counter counter;
	private ArrayList<PdfWorker> workers;
	
	public PdfManager (Files files, ToIgnoreFile toExcludeFile, Counter counter) {
		this.files = files;
		this.toExcludeFile = toExcludeFile;
		this.counter = counter;
		workers = new ArrayList<PdfWorker>();
	}
	
	public void run() {
		for(File file: files.getAllPdfFiles()) {
			PdfWorker pdfWorker = new PdfWorker(file, counter, toExcludeFile);
			pdfWorker.start();
			workers.add(pdfWorker);
		}
	}
	
	public ArrayList<PdfWorker> getWorkers(){
		return this.workers;
	}
}
