package pcd.ass1;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

	private Map<String, Integer> occurrencies;
	private Lock mutex;
	private Condition update;
	private boolean isUpdate;
	private int processedWords;

	public Counter() {
		this.occurrencies = new HashMap<String, Integer>();
		this.isUpdate = false;
		this.mutex = new ReentrantLock();
		this.update = mutex.newCondition();
		this.processedWords = 0;
	}

	/*
	 * merge tra le occorrenze già nell'oggetto e quelle passate in input (faccio la
	 * somma in caso le parole sono da entrambe le parti)
	 */
	public void mergeOccurrence(Map<String, Integer> mapToMerge, int processedWords) {
		try {
			mutex.lock();
			mapToMerge.forEach((k, v) -> occurrencies.merge(k, v, Integer::sum));
			this.isUpdate = true;
			this.processedWords += processedWords;
			this.update.signal();
		} finally {
			mutex.unlock();
		}
	}

	/*
	 * ritorna le occorrenze inteso come coppie parola-numero di occorrenze
	 */

	public Map<String, Integer> getOccurrencies() {
		try {
			mutex.lock();
			if (!isUpdate) {
				try {
					this.update.await();
				} catch (InterruptedException ex) {
				}
			}
			// log("get all files pdf");
			this.isUpdate = false;
			return this.occurrencies;
		} finally {
			mutex.unlock();
		}
	}

	public int getProcessedWords() {
		try {
			mutex.lock();
			return this.processedWords;
		}finally {
			mutex.unlock();
		}
	}
	
	public void reset() {
		try {
			mutex.lock();
			this.processedWords = 0;
			this.occurrencies = new HashMap<String, Integer>();
			this.isUpdate = false;
		}finally {
			mutex.unlock();
		}
	}
}