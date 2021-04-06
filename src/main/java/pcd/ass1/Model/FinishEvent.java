package pcd.ass1.Model;

public class FinishEvent {

    private int cont;
    private boolean genFinish;

    public FinishEvent() {
        this.cont = 0;
        this.genFinish = false;
    }

    public synchronized void add() {
        this.cont += 1;
    }

    public synchronized void setGenFinish() {
        this.genFinish = true;
    }

    public synchronized void countDown() {
        this.cont -= 1;
    }

    public synchronized boolean isFinished() {
        return this.cont == 0 && genFinish;
    }

}
