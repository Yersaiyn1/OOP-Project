package core.observer;

public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(NewsEvent event);
}