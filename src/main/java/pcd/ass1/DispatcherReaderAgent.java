package pcd.ass1;

import java.io.File;

/*
 * Thread che gestisce l'avvio di tanti worker quanti sono quelli in chiamata ad available processors
 */
public class DispatcherReaderAgent extends Thread{

	private PdfFile<File> files;
	private ToIgnore toIgnore;
	private Counter counter;
	private FinishEvent finish;
	
	public DispatcherReaderAgent (PdfFile<File> files, ToIgnore toIgnore, Counter counter, FinishEvent finish) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.finish = finish;
		this.setName("Dispatcher Reader Agent");
	}
	
	public void run() {
			int n = Runtime.getRuntime().availableProcessors();
			log("Creo "+n+" Workers"); 
			for(int i = 0; i <= n-1; i++) {
				ReaderAgent reader = new ReaderAgent(files, counter, toIgnore, finish);
				reader.start();
			}
		}
	
	public void log(String s) {
		System.out.println("[Pdf Manager] " + s);
	}
}
