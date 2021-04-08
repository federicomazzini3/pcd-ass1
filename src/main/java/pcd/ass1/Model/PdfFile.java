package pcd.ass1.Model;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Struttura dati condivisa che contiene i file riconosciuti e inseriti dal Generator Agent
 * I file vengono inseriti in una coda
 * Questi vengono rimossi ogni qual volta un worker richiede un pdf
 * E' stato implementato uno scenario producer-consumer tra GeneratorAgent e ReaderAgent
 */
public class PdfFile<Item> {

    private Queue<Item> files;
    private Lock mutex;
    private Condition notEmpty;

    public PdfFile() {
        this.mutex = new ReentrantLock();
        this.notEmpty = mutex.newCondition();
        this.files = new LinkedList<Item>();
    }

    public void setPdfFile(Item file) {
        try {
            mutex.lock();
            this.files.add(file);
            if (this.wasEmpty())
                this.notEmpty.signal();
        } finally {
            mutex.unlock();
        }
    }

    public Item getPdfFile() throws InterruptedException {
        try {
            mutex.lock();
            while (isEmpty()) {
                    this.notEmpty.await();
            }
            return this.files.poll();
        } finally {
            mutex.unlock();
        }
    }

    private boolean isEmpty() {
        return files.isEmpty();
    }

    private boolean wasEmpty() {
        return files.size() == 1;
    }
}
