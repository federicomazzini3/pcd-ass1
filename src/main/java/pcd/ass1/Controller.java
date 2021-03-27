package pcd.ass1;

public class Controller {
	private Counter counter;
	private FlagStop stopFlag;
	private Agent agent;
	private View setView;
	private PdfFile file;
	private ToIgnore toIgnore;
	
	public Controller(PdfFile file, ToIgnore toIgnore, Counter counter){
		this.file = file;
		this.toIgnore = toIgnore;
		this.counter = counter;	
		this.stopFlag = new FlagStop();
	}
	
	public synchronized void setView(View view) {
		this.setView= view;
	}

	public synchronized void notifyStarted() {
		agent = new Agent(file, toIgnore, counter, stopFlag, setView);
		agent.start();		
	}
	
	public synchronized void notifyStopped() {
		stopFlag.set();
	}
		
}
