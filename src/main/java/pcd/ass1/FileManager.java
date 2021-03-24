package pcd.ass1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

/*
 * THREAD THAT MANAGE ALL THE FILES AND THE PDF FILES
 */
public class FileManager extends Thread {

	private String directory;
	private PdfFile files;

	public FileManager(String directory, PdfFile files) {
		this.directory = directory;
		this.files = files;
	}
	
	public void run() {
		this.log("Cerco i file nella directory");
		Path path = Paths.get(directory);

		try (Stream<Path> walk = Files.walk(path)) {

			walk.filter(Files::isReadable) // read permission
					.filter(Files::isRegularFile) // file only
					.filter(this::isPdf)
					.map(this::toFile)
					.forEach(doc -> {
						log("File trovato" + doc.getName());
						files.setPdfFile(doc);
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.log("Finito");
	}

	private Stream<PDDocument> toPage(PDDocument document){
		List<PDDocument> allPages = new ArrayList<PDDocument>();
		Splitter splitter = new Splitter();
		try {
			allPages = splitter.split(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allPages.stream();
	}
	
	private PDDocument toPDDocument(File file) {
		PDDocument doc = new PDDocument();
		try {
			PDDocument.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	private File toFile(Path path) {
		return path.toFile();
	}
	
	private boolean isPdf(Path path) {
		boolean cond = path.getFileName().toString().toLowerCase().endsWith("pdf");
		return cond;
	}

	private void log(String string) {
		System.out.println("File Manager: " + string);
	}
}