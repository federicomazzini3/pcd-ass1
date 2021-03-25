package pcd.ass1;


public class ExtractWords {

	public static void main(String[] args) {
		Chrono chrono = new Chrono();
		chrono.start();

		String directoryPdf = args[0];
		int numberOfWords = Integer.parseInt(args[1]);
		String toIgnorePath = args[2];

		PdfFile files = new PdfFile(); // contiene la lista dei file nella directory specificata
		ToIgnore toExcludeFile = new ToIgnore(); // contiene il file con le parole da ignorare e le parole da ignorare
		Counter counter = new Counter();												

		Excluser excluser = new Excluser(toIgnorePath, toExcludeFile);
		excluser.start();
		
		// gestisce la lettura della directory
		FileManager fileManager = new FileManager(directoryPdf, files);
		fileManager.start();

		// gestisce i processi che leggono i file
		PdfManager pdfManager = new PdfManager(files, toExcludeFile, counter);
		pdfManager.start();

		SinkAgent sinkAgents = new SinkAgent(counter, numberOfWords, chrono);
		sinkAgents.start();
	}
}
