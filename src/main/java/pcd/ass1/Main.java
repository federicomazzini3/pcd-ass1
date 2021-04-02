package pcd.ass1;

public class Main {

	public static void main(String[] args) {
		Controller controller = new Controller();
		View view = new View(controller);
		controller.setView(view);
		view.display();		
	}
}
