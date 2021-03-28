package pcd.ass1;


public class Main {

	public static void main(String[] args) {
		PdfFile files = new PdfFile(); // contiene la lista dei file nella directory specificata
		ToIgnore toIgnore = new ToIgnore(); // contiene il file con le parole da ignorare e le parole da ignorare
		Counter counter = new Counter();	
		
		Controller controller = new Controller(files, toIgnore, counter);
		View view = new View(controller);
		//ShowGUI gui = new ShowGUI(controller, counter.getProcessedWords());
		controller.setView(view);
		view.display();		
	}
}
