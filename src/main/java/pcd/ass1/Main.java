package pcd.ass1;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		final String directoryPdf = "/home/fred/Documenti/uni/programmazione-concorrente/progetto/ass1/PDF";
		final String toIgnoreFile = "/home/fred/Documenti/uni/programmazione-concorrente/progetto/ass1/toignore.txt";
		final int wordsNumber = 10;
		PdfFile<File> files = new PdfFile<File>();
		ToIgnore toIgnore = new ToIgnore();
		Counter counter = new Counter();

		FinishEvent finish = new FinishEvent();

		Chrono chrono = new Chrono();
		chrono.start();

		IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFile, toIgnore);
		GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdf, files, finish);
		DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter, finish);
		SinkAgent sinkAgents = new SinkAgent(counter, wordsNumber, chrono, finish);

		ignoreAgent.start();
		generatorAgent.start();
		readerDispatcher.start();
		sinkAgents.start();
	}
}
