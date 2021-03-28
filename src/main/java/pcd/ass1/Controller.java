package pcd.ass1;

public class Controller {
	private Counter counter;
	private FlagStop stopFlag;
	private Agent agent;
	private View view;
	private PdfFile file;

	private ToIgnore toIgnore;
	private String directoryPdf;
	private String toIgnoreFile;
	private int numberOfWords;
	
	public Controller(PdfFile files, ToIgnore toIgnore, Counter counter){
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;	
		this.stopFlag = new FlagStop();
	}
	
	public synchronized void setView(View view) {
		this.view= view;
	}
	
	public synchronized void setDirectoryPdf(String directoryPdf){
		this.directoryPdf = directoryPdf;
	}
	
	public synchronized void setToIgnoreFile(String toIgnoreFile) {
		this.toIgnoreFile = toIgnoreFile;
	}
	
	public synchronized void setNumberOfWords(int n) {
		this.numberOfWords = n;
	}

	public synchronized void notifyStarted() {
		agent = new Agent(directoryPdf, toIgnoreFile, numberOfWords, file, toIgnore, counter, stopFlag, view);
		agent.start();		
	}
	
	public synchronized void notifyStopped() {
		stopFlag.set();
	}
}
