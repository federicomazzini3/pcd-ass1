package pcd.ass1.Controller.Agents;

import pcd.ass1.Chrono;
import pcd.ass1.Model.Counter;
import pcd.ass1.Model.FinishEvent;
import pcd.ass1.Model.Occurrence;

import java.util.List;
import java.util.Map;

public class SinkAgentCLI extends AbstractSinkAgent{
    protected Counter counter;
    private Chrono chrono;
    private FinishEvent finish;
    private int wordsNumberToRetrieve;
    private List<Occurrence> lastResultOccurrence;
    private int lastResultProcessedWords;

    public SinkAgentCLI(Counter counter, int words, Chrono chrono, FinishEvent finish) {
        this.counter = counter;
        this.wordsNumberToRetrieve = words;
        this.chrono = chrono;
        this.finish = finish;
        this.setName("Sink Agent");
    }

    public void run() {
        while (!finish.isFinished()) {
            log("Attendo risultati...");
            Map<String, Integer> occ = counter.getOccurrences();
            lastResultProcessedWords = counter.getProcessedWords();
            log("Elaboro il risultato");
            lastResultOccurrence = createOccurrencesList(occ, wordsNumberToRetrieve);

            this.printResult();
        }
        this.printResult();
        log("Completato in:" + chrono.getTime() / 1000.00 + " secondi");
        log("Finito");
        System.exit(0);
    }

    private void printResult(){
        for(Occurrence occ: lastResultOccurrence){
            System.out.println(" - " + occ.getWord() + " " + occ.getCount());
        }
        System.out.println(" - Parole processate: " + lastResultProcessedWords);
    }
}
