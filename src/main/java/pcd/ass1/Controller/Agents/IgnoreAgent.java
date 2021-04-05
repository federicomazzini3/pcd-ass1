package pcd.ass1.Controller.Agents;

import pcd.ass1.Model.StopFlag;
import pcd.ass1.Model.ToIgnore;

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
	StopFlag flag;

	public IgnoreAgent(String toIgnoreFilePath, ToIgnore toIgnoreFile, StopFlag flag) {
		this.toIgnoreFileName = toIgnoreFilePath;
		this.toIgnoreFile = toIgnoreFile;
		this.flag = flag;
		this.setName("Ignore Agent");
	}
	
	public void run() {
			flag.checkStop();
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
				flag.checkStop();
				toIgnoreFile.setToIgnoreWords(words);
				log("Finito");
			}
		}
	
	private void log(String s) {
		System.out.println("[Ignore Agent] " + s);
	}
}
