package pcd.ass1;

import java.io.File;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/*
 * Agente che attende un file in arrivo nella pipe, lo trasforma in PDDocument e lo fa analizzare da un textreader
 */

public class ReaderAgent extends Thread {

	private PdfFile pdfFile;
	private ToIgnore toIgnore;
	private Counter globalCounter;
	private Flag flag;
	
	public ReaderAgent(PdfFile pdfFile, Counter counter, ToIgnore toIgnore, Flag flag) {
		this.pdfFile = pdfFile;
		this.toIgnore = toIgnore;
		this.globalCounter = counter;
		this.flag = flag;
		this.setName("Reader Agent " + this.getId());
	}

	public void run() {
		
		TextReader textReader = new TextReader(toIgnore.getToIgnoreWords());
		
		while (!flag.isStop()) {
			File file = pdfFile.getPdfFile();
			String currentFile = file.getName();
			PDDocument document;
			try {
				this.log(currentFile);
				document = PDDocument.load(file);
				PDFTextStripper stripper = new PDFTextStripper();

				String pdfText = stripper.getText(document);
				document.close();

				Map<String, Integer> results = textReader.getOccurrences(pdfText);
				int processedWords = textReader.getProcessedWord();
				if(!flag.isReset()) {
					globalCounter.mergeOccurrence(results, processedWords);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void log(String s) {
		System.out.println("[Pdf worker] " + this.getName() + ": " + s);
	}
}
