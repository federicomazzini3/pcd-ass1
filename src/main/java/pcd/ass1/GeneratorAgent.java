package pcd.ass1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Agente con il compito di recuperare i vari file dalla directory passata in input
 * e caricarli all'interno di una struttura dati condivisa
 * implementato un producer-consumer tra questo e i worker che leggono i vari pdf.
 */

public class GeneratorAgent extends Thread {

	private String directory;
	private PdfFile<File> files;
	private FinishEvent finish;

	public GeneratorAgent(String directory, PdfFile<File> files, FinishEvent finish) {
		this.directory = directory;
		this.files = files;
		this.finish = finish;
		this.setName("Generator Agent");
	}

	/*public void run() {
		log("Cerco i file nella directory");
		Path path = Paths.get(directory);

		try (Stream<Path> walk = Files.walk(path)) {

			walk.filter(Files::isReadable)
				.filter(Files::isRegularFile)
				.filter(this::isPdf)
				.map(this::toFile)
				.forEach(doc -> {
						log("File trovato" + doc.getName());
						files.setPdfFile(doc);
						finish.add();
				});
		} catch (IOException e) {
			e.printStackTrace();
		}
		log("Finito");
		finish.setGenFinish();
	}*/
	
	public void run() {
		log("Cerco i file nella directory");
		File folder = new File(directory);
		File[] listFile = folder.listFiles();
		int cont = 0;
		if(listFile != null) {
		for (final File file : listFile) {
			if (isFilePdf(file)) {
				log("File trovato" + file.getName());
				files.setPdfFile(file);
				finish.add();
				cont++;
			}
		}
	}
		log("Finito");
		finish.setGenFinish();
		
		if(cont == 0) {
			log("Nessun pdf trovato");
			System.exit(0);
		}
	}

	private File toFile(Path path) {
		return path.toFile();
	}
	
	private boolean isFilePdf(File file) {
		boolean cond = file.getName().toString().toLowerCase().endsWith("pdf");
		return cond;
	}

	private boolean isPdf(Path path) {
		boolean cond = path.getFileName().toString().toLowerCase().endsWith("pdf");
		return cond;
	}

	private void log(String string) {
		System.out.println("[" + this.getName() + "] " + string);
	}
}