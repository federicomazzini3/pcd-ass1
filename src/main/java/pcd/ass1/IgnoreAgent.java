package pcd.ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/*
 * Recupera il file con le parole da ignorare e le inserisce all'interno di una struttura dati condivisa (monitor) denominata ToIgnore
 */

public class IgnoreAgent extends Thread{

	String toIgnoreFileName;
	ToIgnore toIgnoreFile;

	public IgnoreAgent(String toIgnoreFilePath, ToIgnore toIgnoreFile) {
		this.toIgnoreFileName = toIgnoreFilePath;
		this.toIgnoreFile = toIgnoreFile;
		this.setName("Ignore Agent");
	}
	
	public void run() {
			HashSet<String> words = new HashSet<String>();
			try {
				log("Cerco file");
				File file = new File(toIgnoreFileName);

				if (file != null) {
					Scanner input = new Scanner(file);

					while (input.hasNext()) {
						words.add(input.next());
					}
					input.close();
				}
			} catch (FileNotFoundException e) {
				log("Attenzione, file non trovato");
			} catch(NullPointerException ex) {
				log("Attenzione, file non inserito");
			}
			finally {
				toIgnoreFile.setToIgnoreWords(words);
				log("Finito");
			}
		}
	
	private void log(String s) {
		System.out.println("[Ignore Agent] " + s);
	}
}
