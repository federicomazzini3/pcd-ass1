package pcd.ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class IgnoreAgent extends Thread{

	String toIgnoreFileName;
	ToIgnore toIgnoreFile;
	Flag flag;

	public IgnoreAgent(String toIgnoreFilePath, ToIgnore toIgnoreFile, Flag flag) {
		this.toIgnoreFileName = toIgnoreFilePath;
		this.toIgnoreFile = toIgnoreFile;
		this.flag = flag;
	}
	
	public void run() {
		if(!flag.isStop()) {
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
				if(!flag.isReset())
				toIgnoreFile.setToIgnoreWords(words);
				log("Finito");
			}
		}
	}
	
	private void log(String s) {
		System.out.println("[Excluser] " + s);
	}
}
