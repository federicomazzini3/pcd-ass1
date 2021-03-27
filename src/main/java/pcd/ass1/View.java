package pcd.ass1;

public class View {
	
	private ShowGUI gui;
	
	public View(Controller controller, int initialValue) {
		this.gui = new ShowGUI(controller, initialValue);
	}
	
	public synchronized void setCountingState() {
		
	}
	
	public synchronized void setIdleState() {
		
	}
	
	public synchronized void updateCountValue(int value) {
		
	}
	
	public synchronized void display() {
		gui.display();
	}

}
