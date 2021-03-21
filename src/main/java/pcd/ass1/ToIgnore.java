package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * MONITOR THAT STORE THE TO IGNORE FILE AND THE LIST OF ALL WORD TO IGNORE
 */

public class ToIgnore {

	private String toIgnoreFileName;
	private File toIgnoreFile;
	private ArrayList<String> toIgnoreWords;
	private Lock mutex;
	private Condition isToIgnoreWordsAvail;
	private Condition isToIgnoreFileAvail;
	private boolean toIgnoreWordsAvail;
	private boolean toIgnoreFileAvail;

	public ToIgnore(String name) {
		this.toIgnoreFileName = name;
		this.mutex = new ReentrantLock();
		this.isToIgnoreWordsAvail = mutex.newCondition();
		this.isToIgnoreFileAvail = mutex.newCondition();
		this.toIgnoreWordsAvail = false;
		this.toIgnoreFileAvail = false;
	}

	public void setToIgnoreWords(ArrayList<String> words) {
		try {
			mutex.lock();
			this.toIgnoreWords = words;
			this.toIgnoreWordsAvail= true;
			this.isToIgnoreWordsAvail.signalAll();
			//log("set ignore words");
		} finally {
			mutex.unlock();
		}
	}

	public void setToIgnoreFiles(File file) {
		try {
			mutex.lock();
			this.toIgnoreFile = file;
			this.toIgnoreFileAvail = true;
			this.isToIgnoreFileAvail.signalAll();
			//log("set ignore files");
		} finally {
			mutex.unlock();
		}
	}
	
	public String getToIgnoreFileName() {
		try {
			mutex.lock();
			//log("get to ignore file name");
		if(this.toIgnoreFileName == null)
			return new String();
		return this.toIgnoreFileName;
		} finally {
			mutex.unlock();
		}
	}

	public ArrayList<String> getToIgnoreWords() {
		try {
			mutex.lock();
			if (!this.toIgnoreWordsAvail) {
				try {
					this.isToIgnoreWordsAvail.await();
				} catch (InterruptedException ex) {
				}
			}
			//log("get to ignore words");
			return this.toIgnoreWords;
		} finally {
			mutex.unlock();
		}
	}

	public File getToIgnoreFile() {
		try {
			mutex.lock();
			if (!this.toIgnoreFileAvail) {
				try {
					this.isToIgnoreFileAvail.await();
				} catch (InterruptedException ex) {
				}
			}
			//log("get to ignore file");
			return this.toIgnoreFile;
		} finally {
			mutex.unlock();
		}
	}
	
	public void log(String s) {
		System.out.println(s);
	}
}
