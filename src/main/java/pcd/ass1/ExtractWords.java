package pcd.ass1;

import java.util.List;

public class ExtractWords {

	public static void main(String[] args) {
		Chrono chrono = new Chrono();
		chrono.start();

		String directory = args[0];
		int numberOfWords = Integer.parseInt(args[1]);
		String toIgnoreFileName = args[2];

		Files files = new Files(); // contiene la lista dei file nella directory specificata
		ToIgnore toExcludeFile = new ToIgnore(toIgnoreFileName); // contiene il file con le parole da ignorare e le
																	// parole da ignorare
		Counter counter = new Counter(); // contiene il conteggio delle parole totali di tutti i pdf

		// gestisce la lettura della directory
		FileManager fileManager = new FileManager(directory, files, toExcludeFile);
		fileManager.start();

		// gestisce i processi che leggono i file
		PdfManager pdfManager = new PdfManager(files, toExcludeFile, counter);
		pdfManager.start();

		System.out.println("Ho i primi risultati...");
		printResult(counter.getFirstN(numberOfWords));

		try {
			for (PdfWorker worker : pdfManager.getWorkers()) {
				worker.join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Ecco i risultati finali!");
		printResult(counter.getFirstN(numberOfWords));

		chrono.stop();
		double time = chrono.getTime();
		System.out.println("Completato in: " + time + " ms");
	}

	public static void printResult(List<Occurrence> occ) {
		for (Occurrence o : occ) {
			System.out.println(o.getWord() + " " + o.getCount());
		}
	}

	public static void print(Object o) {
		System.out.println(o);
	}
}
