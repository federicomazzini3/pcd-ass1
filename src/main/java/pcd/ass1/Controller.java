package pcd.ass1;

public class Controller {
	private Counter counter;
	private StopFlag stopFlag;
	private Agent agent;
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
		this.stopFlag = new StopFlag();
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
		if (!toResume) {
			agent = new Agent(directoryPdf, toIgnoreFile, numberOfWords, files, toIgnore, counter, stopFlag, view);
			agent.start();
		}
		stopFlag.setFalse();
	}

	public synchronized void notifyStopped() {
		stopFlag.setTrue();
		toResume = true;
	}
}
