package pcd.ass1.Controller.Agents;

import pcd.ass1.Model.*;

import java.io.File;

/*
 * Thread che gestisce l'avvio di tanti worker quanti sono quelli in chiamata ad available processors
 */
public class DispatcherReaderAgent extends Thread{

	private PdfFile<File> files;
	private ToIgnore toIgnore;
	private Counter counter;
	private StopFlag flag;
	private FinishEvent finish;
	
	public DispatcherReaderAgent (PdfFile<File> files, ToIgnore toIgnore, Counter counter, StopFlag stopFlag, FinishEvent finish) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.flag = stopFlag;
		this.finish = finish;
		this.setName("Dispatcher Reader Agent");
	}
	
	public void run() {
			flag.checkStop();
			int n = Runtime.getRuntime().availableProcessors();
			log("Creo "+n+" Workers"); 
			for(int i = 0; i <= n-1; i++) {
				ReaderAgent reader = new ReaderAgent(files, counter, toIgnore, flag, finish);
				reader.start();
			}
		}
	
	public void log(String s) {
		System.out.println("[Pdf Manager] " + s);
	}
}
