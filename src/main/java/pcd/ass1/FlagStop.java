package pcd.ass1;

public class FlagStop {
	
	private boolean flag;
	
	public FlagStop() {
		flag = false;
	}
	
	public synchronized void reset() {
		flag = false;
	}
	
	public synchronized void set() {
		flag = true;
	}
	
	public synchronized boolean isSet() {
		return flag;
	}

}
