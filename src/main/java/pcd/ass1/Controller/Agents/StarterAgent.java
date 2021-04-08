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
    private FinishEvent finish;
    private View view;

    public StarterAgent(String directoryPdf, String toIgnoreFile, int wordsNumber, PdfFile<File> files, ToIgnore toIgnore, Counter counter, StopFlag stopFlag, FinishEvent finish, View view) {
        this.directoryPdf = directoryPdf;
        this.toIgnoreFile = toIgnoreFile;
        this.wordsNumber = wordsNumber;
        this.files = files;
        this.toIgnore = toIgnore;
        this.counter = counter;
        this.stopFlag = stopFlag;
        this.finish = finish;
        this.view = view;
        this.setName("Startup Agent");
    }

    public void run() {
        Chrono chrono = new Chrono();
        chrono.start();

        log("Inizializzo pipeline");

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

    private void log(String s){
        System.out.println("[" + this.getName() + "] " + s);
    }
}

