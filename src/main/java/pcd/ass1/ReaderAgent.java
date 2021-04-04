package pcd.ass1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.Map;

/*
 * Agente che richiede un file in arrivo (prodotto da GeneratorAgent)
 * una volta recuparato il file lo trasforma in PDDocument
 * e lo fa analizzare ad un oggetto di classe textreader
 */

public class ReaderAgent extends Thread {

	private PdfFile<File> pdfFile;
	private ToIgnore toIgnore;
	private Counter globalCounter;
	private FinishEvent finish;
	
	public ReaderAgent(PdfFile<File> pdfFile, Counter counter, ToIgnore toIgnore, FinishEvent finish) {
		this.pdfFile = pdfFile;
		this.toIgnore = toIgnore;
		this.globalCounter = counter;
		this.finish = finish;
		this.setName("Reader Agent " + this.getId());
	}

	public void run() {
		
		TextReader textReader = new TextReader(toIgnore.getToIgnoreWords());
		
		log("Avvio del Reader");

		while (!finish.isFinished()){
			File file = pdfFile.getPdfFile();
			String currentFile = file.getName();
			PDDocument document;
			try {
				this.log("Analizzo il file: " + currentFile);
				document = PDDocument.load(file);
				PDFTextStripper stripper = new PDFTextStripper();

				String pdfText = stripper.getText(document);
				document.close();

				Map<String, Integer> results = textReader.getOccurrences(pdfText);
				int processedWords = textReader.getProcessedWord();
				
					globalCounter.mergeOccurrence(results, processedWords);
					log("Inserisco risultati elaborati");
					finish.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log("Esco dopo Finish");
	}

	private void log(String s) {
		System.out.println("[" + this.getName() + "]: " + s);
	}
}
