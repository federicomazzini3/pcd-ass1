package pcd.ass1;


public class Main {

	public static void main(String[] args) {
		Chrono chrono = new Chrono();
		chrono.start();

		String directoryFilesPath = args[0];
		int numberOfWords = Integer.parseInt(args[1]);
		String toIgnoreFilePath = args[2];

		PdfFile files = new PdfFile(); // contiene la lista dei file nella directory specificata
		ToIgnore toIgnore = new ToIgnore(); // contiene il file con le parole da ignorare e le parole da ignorare
		Counter counter = new Counter();												

		IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFilePath, toIgnore);
		ignoreAgent.start();
		
		// gestisce la lettura della directory
		GeneratorAgent generatorAgent = new GeneratorAgent(directoryFilesPath, files);
		generatorAgent.start();

		// gestisce i processi che leggono i file
		DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter);
		readerDispatcher.start();

		SinkAgent sinkAgents = new SinkAgent(counter, numberOfWords, chrono);
		sinkAgents.start();
		
		Controller controller = new Controller();
		ShowGUI gui = new ShowGUI(controller, 0);
		gui.display();		
	}
}
