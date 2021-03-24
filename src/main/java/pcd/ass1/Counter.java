package pcd.ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
	public void mergeOccurrence(Map<String, Integer> mapToMerge) {
		try {
			mutex.lock();
			mapToMerge.forEach((k, v) -> occurrencies.merge(k, v, Integer::sum));
			this.isUpdate = true;
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

	public void setProcessedWords(int processedWords) {
		try {
			mutex.lock();
			this.processedWords += processedWords; 
		}finally {
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
}