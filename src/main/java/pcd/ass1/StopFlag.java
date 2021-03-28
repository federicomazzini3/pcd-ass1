package pcd.ass1;

public class StopFlag {

	private boolean isStop;

	public StopFlag() {
		isStop = false;
	}

	public synchronized void setTrue() {
		isStop = true;
	}
	
	public synchronized void setFalse() {
		isStop = false;
		notifyAll();
	}

	public synchronized boolean check() {
		while (isStop) {
			try {
				wait();
			} catch (InterruptedException ex) { }
		}
		return isStop;
	}
}
