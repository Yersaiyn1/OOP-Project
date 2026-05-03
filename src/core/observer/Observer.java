package core.observer;

import java.io.Serializable;

/**
 * Observer interface for the Observer pattern.
 * User implements Observer so any subscribed user receives news events.
 */
public interface Observer extends Serializable {

    void update(NewsEvent event);
}