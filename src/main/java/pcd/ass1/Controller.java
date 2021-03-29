package pcd.ass1;

public class Controller {
	private Counter counter;
	private Flag flag;
	private StarterAgent starterAgent;
	private View view;
	private PdfFile files;

	private ToIgnore toIgnore;
	private String directoryPdf;
	private String toIgnoreFile;
	private int numberOfWords;
	private boolean toResume;

	public Controller(PdfFile files, ToIgnore toIgnore, Counter counter) {
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.toIgnoreFile = new String();
		this.flag = new Flag();
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
		flag.setStart();
	}

	public synchronized void notifyStopped() {
		flag.setStop();
		toResume = true;
	}
	
	public synchronized void notifyReset() {
		setDirectoryPdf(null);
		setNumberOfWords(0);
		setToIgnoreFile(null);
		counter.reset();
		toIgnore.reset(); 
		files.reset();
		view.resetValuesGui();
		flag.setReset();
		toResume = false;
	}
}
