package pcd.ass1;

import java.util.ArrayList;

/*
 * Thread che gestisce l'avvio di tanti worker quanti sono quelli in chiamata ad available processors
 */
public class DispatcherReaderAgent extends Thread{

	private PdfFile files;
	private ToIgnore toIgnore;
	private Counter counter;
	private ArrayList<ReaderAgent> readers;
	private Flag flag;
	private FinishEvent finish;
	
	public DispatcherReaderAgent (PdfFile files, ToIgnore toIgnore, Counter counter, Flag stopFlag, FinishEvent finish) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.readers = new ArrayList<ReaderAgent>();
		this.flag = stopFlag;
		this.setName("Dispatcher Reader Agent");
		this.finish = finish;
	}
	
	public void run() {
		if(!flag.isStop()) {
			int n = Runtime.getRuntime().availableProcessors();
			log("Creo "+n+" Workers"); 
			for(int i = 0; i <= n-1; i++) {
				ReaderAgent reader = new ReaderAgent(files, counter, toIgnore, flag, finish);
				reader.start();
				readers.add(reader);
			}
		}
	}
	
	public void log(String s) {
		System.out.println("[Pdf Manager] " + s);
	}
}
