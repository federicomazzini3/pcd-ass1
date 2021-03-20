package pcd.ass1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class FileManager extends Thread{
	
	private String directory;
	private ToIgnoreFile toExcludeFile;
	private Excluser excluser;
	private Files files;

	public FileManager(String directory, Files files, ToIgnoreFile toExcludeFile) {
		this.directory = directory;
		this.toExcludeFile = toExcludeFile;
		this.excluser = new Excluser(toExcludeFile);
		this.files = files;
	}
	
	public void run() {
		ArrayList<File> allFiles = new ArrayList<File>();
		ArrayList<File> allPdfFiles = new ArrayList<File>();
		Boolean isToIgnoreFileFound = false;
		
		excluser.start();
		File folder = new File(directory);
		String currentFileName;
		for (File currentFile : folder.listFiles()) {
			currentFileName = currentFile.getName();
			allFiles.add(currentFile);
			
			if(currentFileName.toLowerCase().endsWith("pdf"))
				allPdfFiles.add(currentFile);
			
			if (this.toExcludeFile.getToIgnoreFileName().equals(currentFileName)) {
				this.toExcludeFile.setToIgnoreFiles(currentFile);
				isToIgnoreFileFound = true;
			}
		}

		this.files.setAllFiles(allFiles);
		this.files.setAllFilesPdf(allPdfFiles);
		
		if(!isToIgnoreFileFound)
			this.toExcludeFile.setToIgnoreFiles(null);
	}
}
