package pcd.ass1.Controller.Agents;

import pcd.ass1.Model.Occurrence;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractSinkAgent extends Thread{

    public abstract void run();

    List<Occurrence> createOccurrencesList(Map<String, Integer> occ, int wordsNumberToRetrieve) {
        return occ.entrySet().stream()
                .map(e -> new Occurrence(e.getKey(), e.getValue()))
                .sorted()
                .limit(wordsNumberToRetrieve)
                .collect(Collectors.toList());
    }

    void log(String s) {
        System.out.println("[Sink Agent] " + s);
    }
}
