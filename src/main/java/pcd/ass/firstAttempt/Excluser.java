package pcd.ass.firstAttempt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Excluser extends Thread{

	ToIgnoreFile toIgnoreFile;
	ArrayList<String> words;

	public Excluser(ToIgnoreFile toExcludeWord) {
		this.toIgnoreFile = toExcludeWord;
		this.words = new ArrayList<String>();
	}

	public void run() {
		File fileTarget = toIgnoreFile.getToIgnoreFile();
		try {
			if (fileTarget != null) {
				Scanner input = new Scanner(fileTarget);

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
