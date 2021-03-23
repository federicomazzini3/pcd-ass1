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

	private ArrayList<String> toIgnoreWords;
	private Lock mutex;
	private Condition isToIgnoreWordsAvail;
	private boolean toIgnoreWordsAvail;

	public ToIgnore() {
		this.mutex = new ReentrantLock();
		this.isToIgnoreWordsAvail = mutex.newCondition();
		this.toIgnoreWordsAvail = false;
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
	
	public void log(String s) {
		System.out.println(s);
	}
}
