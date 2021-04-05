package pcd.ass1.Controller.Agents;

import pcd.ass1.*;
import pcd.ass1.Model.*;
import pcd.ass1.View.View;

import java.io.File;

/*
 * Agente che fa partire la pipeline
 */

public class StarterAgent extends Thread {
	
	private String directoryPdf;
	private String toIgnoreFile;
	private int wordsNumber;
	private Counter counter;
	private PdfFile<File> files;
	private ToIgnore toIgnore;
	private StopFlag stopFlag;
	private View view;
	
	public StarterAgent(String directoryPdf, String toIgnoreFile, int wordsNumber, PdfFile<File> files, ToIgnore toIgnore, Counter counter, StopFlag stopFlag, View view) {
		this.directoryPdf = directoryPdf;
		this.toIgnoreFile = toIgnoreFile;
		this.wordsNumber = wordsNumber;
		this.files = files;
		this.toIgnore = toIgnore;
		this.counter = counter;
		this.stopFlag = stopFlag;
		this.view = view;
		this.setName("Startup Agent");
	}
	
	public void run() {

		FinishEvent finish = new FinishEvent();

		Chrono chrono = new Chrono();
		chrono.start();

		// gestisce il recupero delle parole da ignorare da file
		IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFile, toIgnore, stopFlag);
		ignoreAgent.start();
		
		// gestisce la lettura della directory
		GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdf, files, stopFlag, finish);
		generatorAgent.start();

		// gestisce i processi che leggono i file
		DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter, stopFlag, finish);
		readerDispatcher.start();

		// processa i risultati raw dei reader agent
		SinkAgent sinkAgents = new SinkAgent(counter, wordsNumber, chrono, stopFlag, view, finish);
		sinkAgents.start();
	}
}

