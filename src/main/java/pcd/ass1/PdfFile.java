package pcd.ass1;

import java.io.File;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * MONITOR THAT STORE LIST OF ALL FILES AND A LIST OF ALL PDF FILES
 */
public class PdfFile {

	private Queue<File> files;
	private Lock mutex;
	private Condition notEmpty;

	public PdfFile() {
		this.mutex = new ReentrantLock();
		this.notEmpty = mutex.newCondition();
		this.files = new LinkedList<File>();
	}
	
	public void setPdfFile(File file) {
		try {
			mutex.lock();
			this.files.add(file);
			this.notEmpty.signal();
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
			return this.files.poll();
		} finally {
			mutex.unlock();
		}
	}
	
	private boolean isEmpty() {
		return files.isEmpty();
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
