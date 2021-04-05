package pcd.ass1.Model;

/*
 * Struttura dati condivisa (monitor) che rappresenta lo stato del sistema -> start, stop, reset
 */
public class StopFlag {

	private boolean isStop;

	public StopFlag() {
		this.isStop = false;
	}

	public synchronized void setFalse() {
		this.isStop = false;
		notifyAll();
	}
	
	public synchronized void setTrue(){
		this.isStop = true;
	}
	
	public synchronized void checkStop() {
		while (this.isStop) {
			try {
				wait();
			} catch (InterruptedException ex) { }
		}
	}
}
