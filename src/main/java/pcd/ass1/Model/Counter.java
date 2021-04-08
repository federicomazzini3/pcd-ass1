package pcd.ass1.Model;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/*
 * Struttura dati condivisa (monitor) che mantiene
 *  - il numero delle occorrenze inteso come <parola, numero>
 *  - ed il totale delle parole processate
 */
public class Counter {

	private Map<String, Integer> occurrences;
	private Lock mutex;
	private Condition update;
	private boolean isUpdate;
	private int processedWords;

	public Counter() {
		this.isUpdate = false;
		this.mutex = new ReentrantLock();
		this.update = mutex.newCondition();
		this.occurrences = new HashMap<String, Integer>();
		this.processedWords = 0;
	}

	/*
	 * merge tra le occorrenze già nell'oggetto e quelle passate in input
	 * (faccio la somma in caso le parole sono da entrambe le parti)
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

	public Map<String, Integer> getOccurrences() throws InterruptedException {
		try {
			mutex.lock();
			if (!isUpdate) {
					this.update.await(10000, TimeUnit.MILLISECONDS);
			}
			this.isUpdate = false;
			return new HashMap<>(this.occurrences);
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
}