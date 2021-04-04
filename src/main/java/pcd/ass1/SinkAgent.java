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
    private StopFlag flag;
    private FinishEvent finish;
    private int wordsNumberToRetrieve;
    private List<Occurrence> lastResultOccurrence;
    private int lastResultProcessedWords;

    public SinkAgent(Counter counter, int words, Chrono chrono, StopFlag stopFlag, FinishEvent finish) {
        this.counter = counter;
        this.wordsNumberToRetrieve = words;
        this.chrono = chrono;
        this.flag = stopFlag;
        this.finish = finish;
        this.setName("Sink Agent");
    }
	
	public void run() {
        while (!finish.isFinished()) {
            log("Attendo risultati...");
            Map<String, Integer> occ = counter.getOccurrences();
            lastResultProcessedWords = counter.getProcessedWords();
            log("Elaboro il risultato");
            lastResultOccurrence = createOccurrencesList(occ);

            flag.checkStop();
            log("Stampo risultati");
            printResult();
        }
        log("Completato in:" + chrono.getTime());
        log("Finito");
        System.exit(0);
    }

    private void printResult(){
        for(Occurrence occ: lastResultOccurrence){
            System.out.println(" - " + occ.getWord() + " " + occ.getCount());
        }
        System.out.println("Numero di parole processate: " + lastResultProcessedWords);
    }

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
