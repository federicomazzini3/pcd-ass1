package pcd.ass1;

public class Flag {

	private boolean isStop;
	private boolean isReset;

	public Flag() {
		isStop = false;
		isReset = false;
	}
	
	public synchronized void setReset() {
		isReset = true;
		notifyAll();
	}
	
	public synchronized void setStop(){
		isStop = true;
	}
	
	public synchronized void setStart() {
		isStop = false;
		isReset = false;
		notifyAll();
	}
	
	public synchronized boolean isReset() {
		return isReset;
	}
	
	public synchronized boolean isStop() {
		while (isStop) {
			try {
				wait();
			} catch (InterruptedException ex) { }
		}
		return isStop;
	}
}
