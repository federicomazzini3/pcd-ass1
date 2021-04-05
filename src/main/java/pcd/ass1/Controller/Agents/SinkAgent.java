package pcd.ass1.Controller.Agents;

import pcd.ass1.*;
import pcd.ass1.Model.Counter;
import pcd.ass1.Model.FinishEvent;
import pcd.ass1.Model.Occurrence;
import pcd.ass1.Model.StopFlag;
import pcd.ass1.View.View;

import java.util.List;
import java.util.Map;

/*
 * Agente il cui compito è quello di rimanere in ascolto per gli aggiornamenti
 * riguardanti le occorrenze ed il numero di parole processati.
 * Una volta ottenuti li elabora e richiede l'aggiornamento alla view tramite l'accodamento del task all'EDT
 */

public class SinkAgent extends AbstractSinkAgent {

    private Counter counter;
    private Chrono chrono;
    private View view;
    private StopFlag flag;
    private FinishEvent finish;
    private int wordsNumberToRetrieve;
    private List<Occurrence> lastResultOccurrence;
    private int lastResultProcessedWords;

    public SinkAgent(Counter counter, int words, Chrono chrono, StopFlag stopFlag, View view, FinishEvent finish) {
        this.counter = counter;
        this.wordsNumberToRetrieve = words;
        this.chrono = chrono;
        this.flag = stopFlag;
        this.view = view;
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

            flag.checkStop();
            this.updateView();
        }
        this.updateViewComplete();
    }

    private void updateView() {
        view.updateCountValue(lastResultProcessedWords);
        view.updateOccurrencesLabel(lastResultOccurrence);
        log("Aggiorno la VIEW");
    }

    private void updateViewComplete() {
        this.updateView();
        view.updateComplete(chrono.getTime() / 1000.00);
        log("Completato in:" + chrono.getTime());
        log("Finito");
    }

}
