package pcd.ass1;

public class Main {

	public static void main(String[] args) {

		String directoryPdf = "/Users/federicomazzini/Documents/uni/programmazione-concorrente/progetto/ass1/pdf";
		String toIgnoreFile = "/Users/federicomazzini/Documents/uni/programmazione-concorrente/progetto/ass1/toignore.txt";
		int wordsNumber = 10;
		PdfFile files = new PdfFile();
		ToIgnore toIgnore = new ToIgnore();
		Counter counter = new Counter();

		FinishEvent finish = new FinishEvent();

		Chrono chrono = new Chrono();
		chrono.start();

		IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFile, toIgnore);
		ignoreAgent.start();

		// gestisce la lettura della directory
		GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdf, files, finish);
		generatorAgent.start();

		// gestisce i processi che leggono i file
		DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter, finish);
		readerDispatcher.start();

		SinkAgent sinkAgents = new SinkAgent(counter, wordsNumber, chrono, finish);
		sinkAgents.start();
	}
}
