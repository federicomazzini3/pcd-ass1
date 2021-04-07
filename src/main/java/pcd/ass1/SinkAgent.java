package pcd.ass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/*
 * Agente il cui compito è quello di rimanere in ascolto per gli aggiornamenti
 * riguardanti le occorrenze ed il numero di parole processati.
 * Una volta ottenuti li elabora e richiede l'aggiornamento alla view tramite l'accodamento del task all'EDT
 */
public class SinkAgent extends Thread {

    private Counter counter;
    private Chrono chrono;
    private FinishEvent finish;
    private int wordsNumberToRetrieve;
    private List<Occurrence> lastResultOccurrence;
    private int lastResultProcessedWords;

    public SinkAgent(Counter counter, int words, Chrono chrono, FinishEvent finish) {
        this.counter = counter;
        this.wordsNumberToRetrieve = words;
        this.chrono = chrono;
        this.finish = finish;
        this.setName("Sink Agent");
    }

    public void run() {
        while (true) {
            try {
                log("Attendo risultati...");
                Map<String, Integer> occ = counter.getOccurrences();
                lastResultProcessedWords = counter.getProcessedWords();
                log("Elaboro il risultato");
                lastResultOccurrence = createOccurrencesList(occ);

                log("Stampo risultati");
                printResult();

                if (finish.isFinished()) {
                    break;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                continue;
            }
        }
        printResult();
        log("Completato in:" + chrono.getTime());
        log("Finito");
        System.exit(0);
    }

    private void printResult() {
        if (lastResultOccurrence != null) {
            for (Occurrence occ : lastResultOccurrence) {
                System.out.println(" - " + occ.getWord() + " " + occ.getCount());
            }

        }
        System.out.println("Numero di parole processate: " + lastResultProcessedWords);
    }

    private List<Occurrence> createOccurrencesList(Map<String, Integer> occ) {
        List<Occurrence> allOccurrences = new ArrayList<Occurrence>();
        List<Occurrence> occurrences = new ArrayList<Occurrence>();
        for (Entry<String, Integer> e : occ.entrySet()) {
            allOccurrences.add(new Occurrence(e.getKey(), e.getValue()));
        }
        Collections.sort(allOccurrences);
        if (allOccurrences.size() != 0) {
            for (int i = 0; i < wordsNumberToRetrieve; i++) {
                occurrences.add(allOccurrences.get(i));
            }
        }
        return occurrences;
    }

    private void log(String s) {
        System.out.println("[Sink Agent] " + s);
    }

}
