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

	public PdfWorker(PdfFile pdfFile, Counter counter, ToIgnore toExcludeFile) {
		this.pdfFile = pdfFile;
		this.toIgnoreFile = toExcludeFile;
		this.globalCounter = counter;
	}

	public void run() {
		
		TextReader textReader = new TextReader(toIgnoreFile.getToIgnoreWords());
		
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
				int processedWords = textReader.getProcessedWord();
				globalCounter.mergeOccurrence(results);
				globalCounter.setProcessedWords(processedWords);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void logHello() {
		System.out.println("Pdf worker " + this.getId() + ": " + currentFile);
	}
}
