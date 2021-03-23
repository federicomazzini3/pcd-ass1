package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * MONITOR THAT STORE LIST OF ALL FILES AND A LIST OF ALL PDF FILES
 */
public class PdfFile {

	private Queue<File> pdfFiles;
	private Lock mutex;
	private Condition notEmpty;

	public PdfFile() {
		this.mutex = new ReentrantLock();
		this.notEmpty = mutex.newCondition();
		this.pdfFiles = new LinkedList<File>();
	}
	
	public void setPdfFile(File file) {
		try {
			mutex.lock();
			this.pdfFiles.add(file);
			this.notEmpty.signalAll();
			//log("set all files pdf");
		} finally {
			mutex.unlock();
		}
	}
	
	public File getPdfFile() {
		try {
			mutex.lock();
			while (isEmpty()) {
				try {
					this.notEmpty.await();
				} catch (InterruptedException ex) {
				}
			}
			//log("get all files pdf");
			return this.pdfFiles.poll();
		} finally {
			mutex.unlock();
		}
	}
	
	private boolean isEmpty() {
		return pdfFiles.isEmpty();
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
