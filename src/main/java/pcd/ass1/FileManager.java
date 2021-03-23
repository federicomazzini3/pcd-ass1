package pcd.ass1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
		Path path = Paths.get(directory);

		try (Stream<Path> walk = Files.walk(path)) {

			walk.filter(Files::isReadable) // read permission
					.filter(Files::isRegularFile) // file only
					.filter(this::isPdf)
					.map(this::toFile)
					.forEach(doc -> {
						files.setPdfFile(doc);
					});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private File toFile(Path path) {
		return path.toFile();
	}
	
	private boolean isPdf(Path path) {
		boolean cond = path.getFileName().toString().toLowerCase().endsWith("pdf");
		return cond;
	}

}