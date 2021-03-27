package pcd.ass1;

public class Agent extends Thread {
	
	private Counter counter;
	private PdfFile files;
	private ToIgnore toIgnore;
	private FlagStop stopFlag;
	private View view;
	
	public Agent(PdfFile files, ToIgnore toIgnore, Counter counter, FlagStop stopFlag, View view) {
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

		String directoryFilesPath = "C:/D";//args[0];
		int numberOfWords = 5; //Integer.parseInt(args[1]);
		String toIgnoreFilePath = "C:/D/ignore.txt";//args[2]; 								

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
	}
}

