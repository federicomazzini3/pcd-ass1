package pcd.ass1;


public class Main {

	public static void main(String[] args) {
		PdfFile files = new PdfFile();
		ToIgnore toIgnore = new ToIgnore();
		Counter counter = new Counter();	
		
		Controller controller = new Controller(files, toIgnore, counter);
		View view = new View(controller);
		controller.setView(view);
		view.display();		
	}
}
