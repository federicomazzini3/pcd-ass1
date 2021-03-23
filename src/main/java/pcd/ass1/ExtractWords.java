package pcd.ass1;

import java.util.List;

public class ExtractWords {

	public static void main(String[] args) {
		Chrono chrono = new Chrono();
		chrono.start();

		String directoryPdf = args[0];
		int numberOfWords = Integer.parseInt(args[1]);
		String toIgnorePath = args[2];

		PdfFile files = new PdfFile(); // contiene la lista dei file nella directory specificata
		ToIgnore toExcludeFile = new ToIgnore(); // contiene il file con le parole da ignorare e le
																	// parole da ignorare

		Excluser excluser = new Excluser(toIgnorePath, toExcludeFile);
		excluser.start();
		
		// gestisce la lettura della directory
		FileManager fileManager = new FileManager(directoryPdf, files);
		fileManager.start();

		// gestisce i processi che leggono i file
		PdfManager pdfManager = new PdfManager(files, toExcludeFile);
		pdfManager.start();

		Long wait = (long) 20000;
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chrono.stop();
		double time = chrono.getTime();
		System.out.println("Completato in: " + time + " ms");
	}

	

	public static void print(Object o) {
		System.out.println(o);
	}
}
