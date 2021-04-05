package pcd.ass1;

import pcd.ass1.Controller.Controller;
import pcd.ass1.View.View;

public class Main {

	public static void main(String[] args) {
		Controller controller = new Controller();
		View view = new View(controller);
		controller.setView(view);
		view.display();		
	}
}
