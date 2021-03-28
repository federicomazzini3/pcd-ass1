package pcd.ass1;

public class Controller {
	private Counter counter;
	private FlagStop stopFlag;
	private Agent agent;
	private View setView;
	private PdfFile files;
	private ToIgnore toIgnore;
	
	public Controller(PdfFile files, ToIgnore toIgnore, Counter counter){
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;	
		this.stopFlag = new FlagStop();
	}
	
	public synchronized void setView(View view) {
		this.setView= view;
	}

	public synchronized void notifyStarted() {
		agent = new Agent(files, toIgnore, counter, stopFlag, setView);
		agent.start();		
	}
	
	public synchronized void notifyStopped() {
		stopFlag.set();
	}
		
}
