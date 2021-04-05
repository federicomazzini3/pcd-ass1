package pcd.ass1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Agente il cui compito è quello di rimanere in ascolto per gli aggiornamenti
 * riguardanti le occorrenze ed il numero di parole processati.
 * Una volta ottenuti li elabora e richiede l'aggiornamento alla view tramite l'accodamento del task all'EDT
 */

public class SinkAgent extends Thread {

    private Counter counter;
    private Chrono chrono;
    private View view;
    private StopFlag stopFlag;
    private FinishEvent finish;
    private int wordsNumberToRetrieve;
    private List<Occurrence> lastResultOccurrence;
    private int lastResultProcessedWords;

    public SinkAgent(Counter counter, int words, Chrono chrono, StopFlag stopFlag, View view, FinishEvent finish) {
        this.counter = counter;
        this.wordsNumberToRetrieve = words;
        this.chrono = chrono;
        this.stopFlag = stopFlag;
        this.view = view;
        this.finish = finish;
        this.setName("Sink Agent");
    }
	
	public void run() {
        while (true) {
            log("Attendo risultati...");
            Map<String, Integer> occ = counter.getOccurrences();
            lastResultProcessedWords = counter.getProcessedWords();
            log("Elaboro il risultato");
            lastResultOccurrence = createOccurrencesList(occ);

            stopFlag.checkStop();
            this.updateView();

            if(finish.isFinished())
                break;
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
        log("Completato in:" + chrono.getTime() / 1000.00 + "secondi");
        log("Finito");
    }

    /*
     * ricalcolo l'arraylist delle occorrenze
	 */
	private List<Occurrence> createOccurrencesList(Map<String, Integer> occ) {   
		return occ.entrySet().stream()
				.map(e -> new Occurrence(e.getKey(), e.getValue()))
				.sorted()
				.limit(wordsNumberToRetrieve)
				.collect(Collectors.toList());
	}
	
	private void log(String s) {
		System.out.println("[Sink Agent] " + s);
	}

}
