package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * MONITOR THAT STORE LIST OF ALL FILES AND A LIST OF ALL PDF FILES
 */
public class PdfFile {

	private ArrayList<File> pdfFiles;
	private Lock mutex;
	private Condition isPdfFilesAvail;
	private boolean pdfFilesAvail;

	public PdfFile() {
		this.mutex = new ReentrantLock();
		this.isPdfFilesAvail = mutex.newCondition();
		this.pdfFilesAvail = false;
	}

	public void setAllFilesPdf(ArrayList<File> files) {
		try {
			mutex.lock();
			this.pdfFiles = files;
			this.pdfFilesAvail = true;
			this.isPdfFilesAvail.signalAll();
			//log("set all files pdf");
		} finally {
			mutex.unlock();
		}
	}

	public ArrayList<File> getAllPdfFiles() {
		try {
			mutex.lock();
			if (!this.pdfFilesAvail) {
				try {
					this.isPdfFilesAvail.await();
				} catch (InterruptedException ex) {
				}
			}
			//log("get all files pdf");
			return this.pdfFiles;
		} finally {
			mutex.unlock();
		}
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
