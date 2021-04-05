package pcd.ass1;

import pcd.ass1.Controller.Agents.DispatcherReaderAgent;
import pcd.ass1.Controller.Agents.GeneratorAgent;
import pcd.ass1.Controller.Agents.IgnoreAgent;
import pcd.ass1.Controller.Agents.SinkAgentNoGui;
import pcd.ass1.Model.*;

import java.io.File;

public class MainNoGUI {
    public static void main(String[]args){
        Chrono chrono = new Chrono();
        chrono.start();

        String directoryPdfPath = args[0];
        String toIgnoreFilePath = args[1];
        int wordsToRetrieve = Integer.parseInt(args[2]);
        Counter counter = new Counter();
        PdfFile<File> files = new PdfFile<File>();
        ToIgnore toIgnore = new ToIgnore();
        StopFlag stopFlag = new StopFlag();
        FinishEvent finish = new FinishEvent();

        // gestisce il recupero delle parole da ignorare da file
        IgnoreAgent ignoreAgent = new IgnoreAgent(toIgnoreFilePath, toIgnore, stopFlag);
        // gestisce la lettura della directory
        GeneratorAgent generatorAgent = new GeneratorAgent(directoryPdfPath, files, stopFlag, finish);
        // gestisce i processi che leggono i file
        DispatcherReaderAgent readerDispatcher = new DispatcherReaderAgent(files, toIgnore, counter, stopFlag, finish);
        // processa i risultati raw dei reader agent
        SinkAgentNoGui sinkAgents = new SinkAgentNoGui(counter, wordsToRetrieve, chrono, finish);

        ignoreAgent.start();
        generatorAgent.start();
        readerDispatcher.start();
        sinkAgents.start();
    }
}
