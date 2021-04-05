package pcd.ass1.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Struttura dati condivisa contenente il set di parole da ignorare
 */

public class ToIgnore {

	private HashSet<String> toIgnoreWords;
	private Lock mutex;
	private Condition isToIgnoreWordsAvail;
	private boolean toIgnoreWordsAvail;

	public ToIgnore() {
		this.mutex = new ReentrantLock();
		this.isToIgnoreWordsAvail = mutex.newCondition();
		this.toIgnoreWordsAvail = false;
	}

	public void setToIgnoreWords(HashSet<String> words) {
		try {
			mutex.lock();
			this.toIgnoreWords = words;
			this.toIgnoreWordsAvail= true;
			this.isToIgnoreWordsAvail.signalAll();
		} finally {
			mutex.unlock();
		}
	}

	public HashSet<String> getToIgnoreWords() {
		try {
			mutex.lock();
			if (!this.toIgnoreWordsAvail) {
				try {
					this.isToIgnoreWordsAvail.await();
				} catch (InterruptedException ex) {
				}
			}
			return this.toIgnoreWords;
		} finally {
			mutex.unlock();
		}
	}
}
