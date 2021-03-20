package pcd.ass1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfWorker extends Thread {
	
	private File file;
	private ToIgnoreFile toExcludeFile;
	private Counter globalCounter;
	private ArrayList<TextReader> readerList;

	public PdfWorker(File file, Counter counter, ToIgnoreFile toExcludeFile) {
		this.file = file;
		this.toExcludeFile = toExcludeFile;
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
			 */
			TextReader textReader = new TextReader(text, globalCounter, toExcludeFile);
			textReader.start();
			readerList.add(textReader);
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void logHello() {
		System.out.println("Pdf worker: " + file.getName());
	}
}
