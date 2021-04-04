package pcd.ass1;

/*
 * Struttura dati condivisa (monitor) che rappresenta lo stato del sistema -> start, stop, reset
 */
public class StopFlag {

	private boolean isStop;
	//private boolean isReset;

	public StopFlag() {
		this.isStop = false;
		//this.isReset = false;
	}

	public synchronized void setFalse() {
		this.isStop = false;
		//this.isReset = false;
		notifyAll();
	}
	
	/*public synchronized void setReset() {
		this.isReset = true;
		notifyAll();
	}*/
	
	public synchronized void setTrue(){
		this.isStop = true;
	}
	
	/*public synchronized boolean isReset() {
		return isReset;
	}*/
	
	public synchronized void checkStop() {
		while (this.isStop) {
			try {
				wait();
			} catch (InterruptedException ex) { }
		}
	}
}
