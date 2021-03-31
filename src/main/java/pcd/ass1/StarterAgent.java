package pcd.ass1;

/*
 * Agente che fa partire la pipeline
 */

public class StarterAgent extends Thread {
	
	private String directoryPdf;
	private String toIgnoreFile;
	private int wordsNumber;
	private Counter counter;
	private PdfFile files;
	private ToIgnore toIgnore;
	private Flag stopFlag;
	private View view;
	
	public StarterAgent(String directoryPdf, String toIgnoreFile, int wordsNumber, PdfFile files, ToIgnore toIgnore, Counter counter, Flag stopFlag, View view) {
		this.directoryPdf = directoryPdf;
		this.toIgnoreFile = toIgnoreFile;
		this.wordsNumber = wordsNumber;
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.stopFlag = stopFlag;
		this.view = view;
		this.setName("Startup Agent");
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

		SinkAgent sinkAgents = new SinkAgent(counter, wordsNumber, chrono, stopFlag, view);
		sinkAgents.start();
	}
}

