package pcd.ass.firstAttempt;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Files {

	private ArrayList<File> pdfFiles;
	private ArrayList<File> allFiles;
	private Lock mutex;
	private Condition isPdfFileAvail;
	private Condition isAllFilesAvail;
	private boolean pdfFilesAvail;
	private boolean allFilesAvail;

	public Files() {
		this.mutex = new ReentrantLock();
		this.isPdfFileAvail = mutex.newCondition();
		this.isAllFilesAvail = mutex.newCondition();
		this.pdfFilesAvail = false;
		this.allFilesAvail = false;
	}

	public void setAllFiles(ArrayList<File> files) {
		try {
			mutex.lock();
			this.allFiles = files;
			this.allFilesAvail = true;
			this.isAllFilesAvail.signalAll();
			//log("set all files");
		} finally {
			mutex.unlock();
		}
	}

	public void setAllFilesPdf(ArrayList<File> files) {
		try {
			mutex.lock();
			this.pdfFiles = files;
			this.pdfFilesAvail = true;
			this.isPdfFileAvail.signalAll();
			//log("set all files pdf");
		} finally {
			mutex.unlock();
		}
	}

	public ArrayList<File> getAllFiles() {
		try {
			mutex.lock();
			if (!this.allFilesAvail) {
				try {
					this.isAllFilesAvail.await();
				} catch (InterruptedException ex) {
				}
			}
			//log("get all files");
			return this.allFiles;
		} finally {
			mutex.unlock();
		}
	}

	public ArrayList<File> getAllPdfFiles() {
		try {
			mutex.lock();
			if (!this.pdfFilesAvail) {
				try {
					this.isPdfFileAvail.await();
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
