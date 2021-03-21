package pcd.ass1;

import java.io.File;
import java.util.ArrayList;

/*
 * THREAD THAT MANAGE ALL THE FILES AND THE PDF FILES
 */

public class FileManager extends Thread{
	
	private String directory;
	private ToIgnore toExcludeFile;
	private Excluser excluser;
	private Files files;

	public FileManager(String directory, Files files, ToIgnore toExcludeFile) {
		this.directory = directory;
		this.toExcludeFile = toExcludeFile;
		this.excluser = new Excluser(toExcludeFile);
		this.files = files;
	}
	
	public void run() {
		ArrayList<File> allPdfFiles = new ArrayList<File>();
		Boolean isToIgnoreFileFound = false;
		
		excluser.start();
		File folder = new File(directory);
		String currentFileName;
		File[] files = folder.listFiles();
		
		if(files == null)
			files = new File[0];
		
		for (File currentFile : files) {
			currentFileName = currentFile.getName();
			
			if(currentFileName.toLowerCase().endsWith("pdf"))
				allPdfFiles.add(currentFile);
			
			if (this.toExcludeFile.getToIgnoreFileName().equals(currentFileName)) {
				this.toExcludeFile.setToIgnoreFiles(currentFile);
				isToIgnoreFileFound = true;
			}
		}
		
		this.files.setAllFilesPdf(allPdfFiles);
		
		if(!isToIgnoreFileFound)
			this.toExcludeFile.setToIgnoreFiles(null);
	}
}
