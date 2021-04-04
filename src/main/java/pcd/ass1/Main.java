package pcd.ass1;

public class Main {

	public static void main(String[] args) {
		String directoryPdf = "/Users/federicomazzini/Documents/uni/programmazione-concorrente/progetto/ass1";
		String toIgnoreFile = "/Users/federicomazzini/Documents/uni/programmazione-concorrente/progetto/ass1/toignore.txt";
		int numberOfWords = 10;
		PdfFile files = new PdfFile();
		ToIgnore toIgnore = new ToIgnore();
		Counter counter = new Counter();
		StopFlag flag = new StopFlag();

		StarterAgent starterAgent = new StarterAgent(directoryPdf, toIgnoreFile, numberOfWords, files, toIgnore, counter, flag);
		starterAgent.start();
	}
}
