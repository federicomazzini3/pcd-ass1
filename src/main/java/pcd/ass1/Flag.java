package pcd.ass1;

/*
 * Struttura dati condivisa (monitor) che rappresenta lo stato del sistema -> start, stop, reset
 */
public class Flag {

	private boolean isStop;
	private boolean isReset;

	public Flag() {
		this.isStop = false;
		this.isReset = false;
	}

	public synchronized void setStart() {
		this.isStop = false;
		this.isReset = false;
		notifyAll();
	}
	
	public synchronized void setReset() {
		this.isReset = true;
		notifyAll();
	}
	
	public synchronized void setStop(){
		this.isStop = true;
	}
	
	public synchronized boolean isReset() {
		return isReset;
	}
	
	public synchronized boolean isStop() {
		while (this.isStop) {
			try {
				wait();
			} catch (InterruptedException ex) { }
		}
		return this.isStop;
	}
}
