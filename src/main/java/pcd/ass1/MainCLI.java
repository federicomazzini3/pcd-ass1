package pcd.ass1;

import pcd.ass1.Controller.Agents.*;
import pcd.ass1.Model.*;

import java.io.File;

public class MainCLI {
    public static void main(String[] args) {
        Chrono chrono = new Chrono();
        chrono.start();

        // input
        String directoryPdfPath = args[0];
        String toIgnoreFilePath = args[1];
        int wordsToRetrieve = Integer.parseInt(args[2]);

        // passive components
        Counter counter = new Counter();
        PdfFile<File> files = new PdfFile<File>();
        ToIgnore toIgnore = new ToIgnore();
        StopFlag stopFlag = new StopFlag();
        FinishEvent finish = new FinishEvent();

        // active components
        // gestisce il recupero delle parole da ignorare da file
        IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFilePath, toIgnore, stopFlag);
        // gestisce la lettura della directory
        GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdfPath, files, stopFlag, finish);
        // gestisce i processi che leggono i file
        DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter, stopFlag, finish);
        // processa i risultati raw dei reader agent
        SinkAgentCLI sinkAgents = new SinkAgentCLI(counter, wordsToRetrieve, chrono, finish);

        // start active components
        ignoreAgent.start();
        generatorAgent.start();
        readerDispatcher.start();
        sinkAgents.start();
    }
}
