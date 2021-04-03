package pcd.ass1;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/*
 * Struttura dati condivisa (monitor) che mantiene il numero delle occorrenze inteso come parole, numero di volte
 * ed il totale delle parole processate
 */
public class Counter {

	private Map<String, Integer> occurrences;
	private Lock mutex;
	private Condition update;
	private boolean isUpdate;
	private int processedWords;

	public Counter() {
		this.occurrences = new HashMap<String, Integer>();
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
			mapToMerge.forEach((k, v) -> occurrences.merge(k, v, Integer::sum));
			this.isUpdate = true;
			this.processedWords += processedWords;
			this.update.signal();
		} finally {
			mutex.unlock();
		}
	}

	public Map<String, Integer> getOccurrences() {
		try {
			mutex.lock();
			if (!isUpdate) {
				try {
					this.update.await();
				} catch (InterruptedException ex) {
				}
			}
			this.isUpdate = false;
			Map<String, Integer> test = new HashMap<String, Integer>();
			test = this.occurrences;
			return test;
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
			this.occurrences = new HashMap<String, Integer>();
			this.isUpdate = false;
		}finally {
			mutex.unlock();
		}
	}
}