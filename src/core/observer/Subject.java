package core.observer;

import java.io.Serializable;

/**
 * Subject interface for the Observer pattern. Implemented by NewsService.
 */
public interface Subject extends Serializable {
    void attach(Observer o);

    void detach(Observer o);

    void notifyObservers(NewsEvent e);
}