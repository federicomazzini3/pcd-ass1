package pcd.ass1;

public class Agent extends Thread {
	
	private String directoryPdf;
	private String toIgnoreFile;
	private int wordsNumber;
	private Counter counter;
	private PdfFile files;
	private ToIgnore toIgnore;
	private FlagStop stopFlag;
	private View view;
	
	public Agent(String directoryPdf, String toIgnoreFile, int wordsNumber, PdfFile files, ToIgnore toIgnore, Counter counter, FlagStop stopFlag, View view) {
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
		stopFlag.reset();

		Chrono chrono = new Chrono();
		chrono.start();
								

		IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFile, toIgnore);
		ignoreAgent.start();
		
		// gestisce la lettura della directory
		GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdf, files);
		generatorAgent.start();

		// gestisce i processi che leggono i file
		DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter);
		readerDispatcher.start();

		SinkAgent sinkAgents = new SinkAgent(counter, wordsNumber, chrono);
		sinkAgents.start();
	}
}

