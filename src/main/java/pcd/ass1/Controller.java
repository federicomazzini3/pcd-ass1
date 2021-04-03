package pcd.ass1;

import java.io.File;

public class Controller {
	private Counter counter;
	private StopFlag flag;
	private StarterAgent starterAgent;
	private View view;
	private PdfFile<File> files;
	private ToIgnore toIgnore;
	private String directoryPdf;
	private String toIgnoreFile;
	private int numberOfWords;
	private boolean toResume;

	public Controller() {
		this.files = new PdfFile<File>();
		this.toIgnore = new ToIgnore();
		this.counter = new Counter();
		this.toIgnoreFile = new String();
		this.flag = new StopFlag();
		this.toResume = false;		
	}

	public synchronized void setView(View view) {
		this.view = view;
	}

	public synchronized void setDirectoryPdf(String directoryPdf) {
		this.directoryPdf = directoryPdf;
	}

	public synchronized void setToIgnoreFile(String toIgnoreFile) {
		this.toIgnoreFile = toIgnoreFile;
	}

	public synchronized void setNumberOfWords(int n) {
		this.numberOfWords = n;
	}

	public synchronized void notifyStarted() {
		if(!toResume) {
		starterAgent = new StarterAgent(directoryPdf, toIgnoreFile, numberOfWords, files, toIgnore, counter, flag, view);
		starterAgent.start();
		}
		flag.setFalse();
	}

	public synchronized void notifyStopped() {
		flag.setTrue();
		toResume = true;
	}
	
	/*public synchronized void notifyReset() {
		setDirectoryPdf(null);
		setNumberOfWords(0);
		setToIgnoreFile(null);
		counter.reset();
		toIgnore.reset(); 
		files.reset();
		view.resetValuesGui();
		flag.setReset();
		toResume = false;
	}*/
}
