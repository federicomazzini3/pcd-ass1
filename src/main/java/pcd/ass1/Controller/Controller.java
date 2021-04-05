package pcd.ass1.Controller;

import pcd.ass1.Controller.Agents.StarterAgent;
import pcd.ass1.Model.Counter;
import pcd.ass1.Model.PdfFile;
import pcd.ass1.Model.StopFlag;
import pcd.ass1.Model.ToIgnore;
import pcd.ass1.View.View;

import java.io.File;

public class Controller {
	private Counter counter;
	private StopFlag flag;
	private StarterAgent starterAgent;
	private View view;
	private PdfFile<File> files;
	private ToIgnore toIgnore;
	private String directoryPdf;
	private String toIgnoreFile;
	private int numberOfWords;
	private boolean toResume;

	public Controller() {
		this.files = new PdfFile<File>();
		this.toIgnore = new ToIgnore();
		this.counter = new Counter();
		this.toIgnoreFile = new String();
		this.flag = new StopFlag();
		this.toResume = false;		
	}

	public synchronized void setView(View view) {
		this.view = view;
	}

	public synchronized void setDirectoryPdf(String directoryPdf) {
		this.directoryPdf = directoryPdf;
	}

	public synchronized void setToIgnoreFile(String toIgnoreFile) {
		this.toIgnoreFile = toIgnoreFile;
	}

	public synchronized void setNumberOfWords(int n) {
		this.numberOfWords = n;
	}

	public synchronized void notifyStarted() {
		if(!toResume) {
		starterAgent = new StarterAgent(directoryPdf, toIgnoreFile, numberOfWords, files, toIgnore, counter, flag, view);
		starterAgent.start();
		}
		flag.setFalse();
	}

	public synchronized void notifyStopped() {
		flag.setTrue();
		toResume = true;
	}
}
