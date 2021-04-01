package pcd.ass1;

import java.io.File;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/*
 * Agente che richiede un file in arrivo (prodotto da GeneratorAgent)
 * una volta recuparato il file lo trasforma in PDDocument
 * e lo fa analizzare ad un oggetto di classe textreader
 */

public class ReaderAgent extends Thread {

	private PdfFile<File> pdfFile;
	private ToIgnore toIgnore;
	private Counter globalCounter;
	private Flag flag;
	private FinishEvent finish;
	
	public ReaderAgent(PdfFile<File> pdfFile, Counter counter, ToIgnore toIgnore, Flag flag, FinishEvent finish) {
		this.pdfFile = pdfFile;
		this.toIgnore = toIgnore;
		this.globalCounter = counter;
		this.flag = flag;
		this.setName("Reader Agent " + this.getId());
		this.finish = finish;
	}

	public void run() {
		
		TextReader textReader = new TextReader(toIgnore.getToIgnoreWords());
		
		while (!flag.isStop() && !finish.isFinished()){
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
					finish.countDown();
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
