package pcd.ass1;

import java.util.List;
import java.util.Map;

public class View {
	
	private ShowGUI gui;
	
	public View(Controller controller) {
		this.gui = new ShowGUI(controller);
	}
	
	public synchronized void updateCountValue(int value) {
		gui.updateCountValue(value);
	}
	
	public synchronized void updateOccurrencesLabel(List<Occurrence> occurrences) {
		gui.updateOccurrenciesLabel(occurrences);
	}
	
	public synchronized void resetValuesGui() {
		gui.resetValuesGui();
	}
	
	public synchronized void display() {
		gui.display();
	}

}
