package pcd.ass1;

import java.io.File;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfWorker extends Thread {
	
	private File file;
	private ToIgnore toIgnoreFile;
	private Counter globalCounter;
	private ArrayList<TextReader> readerList;

	public PdfWorker(File file, Counter counter, ToIgnore toExcludeFile) {
		this.file = file;
		this.toIgnoreFile = toExcludeFile;
		this.globalCounter = counter;
		this.readerList = new ArrayList<TextReader>();
	}

	public void run() {

		PDDocument document;
		try {
			this.logHello();
			document = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			
			String text = stripper.getText(document);
			/*
			 * TODO: da fare come oggetto (statico?) e non come thread
			 * oppure text reader okay ma accorpare worker con manager?
			 */
			TextReader textReader = new TextReader(text, globalCounter, toIgnoreFile);
			textReader.start();
			readerList.add(textReader);
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<TextReader> getReaders(){
		return this.readerList;
	}

	private void logHello() {
		System.out.println("Pdf worker: " + file.getName());
	}
}
