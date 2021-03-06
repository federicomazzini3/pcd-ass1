package pcd.ass1.Controller.Agents;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import pcd.ass1.Model.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/*
 * Agente che richiede un file in arrivo (prodotto da GeneratorAgent)
 * una volta recuparato il file lo trasforma in PDDocument
 * e lo fa analizzare ad un oggetto di classe textreader
 */
public class ReaderAgent extends Thread {

    private PdfFile<File> pdfFile;
    private ToIgnore toIgnore;
    private Counter globalCounter;
    private StopFlag flag;
    private FinishEvent finish;

    public ReaderAgent(PdfFile<File> pdfFile, Counter counter, ToIgnore toIgnore, StopFlag flag, FinishEvent finish) {
        this.pdfFile = pdfFile;
        this.toIgnore = toIgnore;
        this.globalCounter = counter;
        this.flag = flag;
        this.finish = finish;
        this.setName("Reader Agent " + this.getId());
    }

    public void run() {
        log("Avvio del Reader");
        TextReader textReader = new TextReader(toIgnore.getToIgnoreWords());
        while (!finish.isFinished()) {

            try {
                flag.checkStop();
                File file = pdfFile.getPdfFile();
                String currentFile = file.getName();
                this.log("Analizzo il file: " + currentFile);

                PDDocument document = PDDocument.load(file);
                PDFTextStripper stripper = new PDFTextStripper();
                String pdfText = stripper.getText(document);
                document.close();

                Map<String, Integer> results = textReader.getOccurrences(pdfText);
                int processedWords = textReader.getProcessedWord();

                flag.checkStop();
                log("Inserisco risultati elaborati");
                globalCounter.mergeOccurrence(results, processedWords);
                finish.countDown();
            } catch (InterruptedException ex) {
                continue;
            } catch (IOException ex) {
                log("Errore nella lettura del file");
            }
        }
        log("Esco dopo Finish");
    }

    private void log(String s) {
        System.out.println("[" + this.getName() + "]: " + s);
    }
}
