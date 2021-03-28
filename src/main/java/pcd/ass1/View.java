package pcd.ass1;

public class View {
	
	private ShowGUI gui;
	
	public View(Controller controller, int initialValue) {
		this.gui = new ShowGUI(controller, initialValue);
	}
	
	public synchronized void setCountingState() {
		gui.setCountingState();
	}
	
	public synchronized void setIdleState() {
		gui.setIdleState();
	}
	
	/*public synchronized void updateCountValue(int value) {
		gui.updateCountValue(value);
	}*/
	
	public synchronized void display() {
		gui.display();
	}

}
