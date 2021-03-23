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
	

	public Excluser(String toIgnoreFileName, ToIgnore toIgnoreFile) {
		this.toIgnoreFileName = toIgnoreFileName;
		this.toIgnoreFile = toIgnoreFile;
	}
	
	public void run() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toIgnoreFile.setToIgnoreWords(words);
	}
}
