package pcd.ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Excluser extends Thread{

	String toIgnoreFileName;
	ToIgnore toIgnoreFile;
	

	public Excluser(String toIgnoreFilePath, ToIgnore toIgnoreFile) {
		this.toIgnoreFileName = toIgnoreFilePath;
		this.toIgnoreFile = toIgnoreFile;
	}
	
	public void run() {
		log("Cerco file");
		File file = new File(toIgnoreFileName);

		ArrayList<String> words = new ArrayList<String>();
		try {
			if (file != null) {
				Scanner input = new Scanner(file);

				while (input.hasNext()) {
					words.add(input.next());
				}
				input.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Attenzione, file non trovato");
		} finally {
			toIgnoreFile.setToIgnoreWords(words);
			log("Finito");
		}
	}
	
	private void log(String s) {
		System.out.println("[Excluser] " + s);
	}
}
