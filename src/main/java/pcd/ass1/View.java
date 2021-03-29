package pcd.ass1;

import java.util.List;
import java.util.Map;

public class View {
	
	private ShowGUI gui;
	
	public View(Controller controller) {
		this.gui = new ShowGUI(controller);
	}
	
	/*public synchronized void setCountingState() {
		gui.setCountingState();
	}*/
	
	/*public synchronized void setIdleState() {
		gui.setIdleState();
	}*/
	
	public synchronized void updateCountValue(int value) {
		gui.updateCountValue(value);
	}
	
	public synchronized void updateOccurrenciesLabel(List<Occurrence> occurrencies) {
		gui.updateOccurrenciesLabel(occurrencies);
	}
	
	public synchronized void resetValuesGui() {
		gui.resetValuesGui();
	}
	
	public synchronized void display() {
		gui.display();
	}

}
