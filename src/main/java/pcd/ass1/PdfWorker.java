package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfWorker extends Thread {

	private PdfFile pdfFile;
	private ToIgnore toIgnoreFile;
	private Counter globalCounter;
	private String currentFile;
	private TextReader textReader;

	public PdfWorker(PdfFile pdfFile, Counter counter, ToIgnore toExcludeFile) {
		this.pdfFile = pdfFile;
		this.toIgnoreFile = toExcludeFile;
		this.globalCounter = counter;
		this.textReader = new TextReader();
	}

	public void run() {
		
		textReader.setToIgnoreWord(toIgnoreFile.getToIgnoreWords());
		
		while (true) {

			File file = pdfFile.getPdfFile();
			currentFile = file.getName();
			PDDocument document;
			try {
				this.logHello();
				document = PDDocument.load(file);
				PDFTextStripper stripper = new PDFTextStripper();

				String text = stripper.getText(document);
				document.close();
				
				Map<String, Integer> results = textReader.getOccurrences(text);
				globalCounter.mergeOccurrence(results);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void logHello() {
		System.out.println("Pdf worker " + this.getId() + ": " + currentFile);
	}
}
