package pcd.ass1;

public class Agent extends Thread {
	
	private String directoryPdf;
	private String toIgnoreFile;
	private int wordsNumber;
	private Counter counter;
	private PdfFile files;
	private ToIgnore toIgnore;
	private StopFlag stopFlag;
	private View view;
	
	public Agent(String directoryPdf, String toIgnoreFile, int wordsNumber, PdfFile files, ToIgnore toIgnore, Counter counter, StopFlag stopFlag, View view) {
		this.directoryPdf = directoryPdf;
		this.toIgnoreFile = toIgnoreFile;
		this.wordsNumber = wordsNumber;
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.stopFlag = stopFlag;
		this.view = view;
	}
	
	public void run() {

		Chrono chrono = new Chrono();
		chrono.start();
								

		IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFile, toIgnore, stopFlag);
		ignoreAgent.start();
		
		// gestisce la lettura della directory
		GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdf, files, stopFlag);
		generatorAgent.start();

		// gestisce i processi che leggono i file
		DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter, stopFlag);
		readerDispatcher.start();

		SinkAgent sinkAgents = new SinkAgent(counter, wordsNumber, stopFlag, chrono);
		sinkAgents.start();
	}
}

