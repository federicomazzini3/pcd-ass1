package pcd.ass1;

import java.util.ArrayList;

/*
 * Thread che gestisce l'analisi dei file pdf.
 * Per ogni file pdf istanzia un nuovo processo che analizza il file pdf.
 * 
 * TODO: scalabilità con riguardo al numero dei processori e alla disponibilità cpu
 */
public class DispatcherReaderAgent extends Thread{

	private PdfFile files;
	private ToIgnore toIgnore;
	private Counter counter;
	private ArrayList<ReaderAgent> readers;
	private Flag flag;
	
	public DispatcherReaderAgent (PdfFile files, ToIgnore toIgnore, Counter counter, Flag stopFlag) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.readers = new ArrayList<ReaderAgent>();
		this.flag = stopFlag;
	}
	
	public void run() {
		if(!flag.isStop()) {
			int n = Runtime.getRuntime().availableProcessors();
			log("Creo "+n+" Workers"); 
			for(int i = 0; i <= n; i++) {
				ReaderAgent reader = new ReaderAgent(files, counter, toIgnore, flag);
				reader.start();
				readers.add(reader);
			}
		}
	}
	
	public ArrayList<ReaderAgent> getWorkers(){
		return this.readers;
	}
	
	public void log(String s) {
		System.out.println("[Pdf Manager] " + s);
	}
}
