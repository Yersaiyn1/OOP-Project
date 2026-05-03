package core.observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsService implements Subject, Serializable {
    private final List<Observer> observers = new ArrayList<>();
    private final List<String> newsFeed = new ArrayList<>();

    @Override
    public synchronized void attach(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public synchronized void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public synchronized void notifyObservers(NewsEvent event) {
        for (Observer o : observers) {
            o.update(event);
        }
    }

    public synchronized void publishNews(String newsContent) {
        newsFeed.add(newsContent);
        NewsEvent event = new NewsEvent(newsContent);
        notifyObservers(event);
    }

    public List<String> getNewsFeed() {
        return new ArrayList<>(newsFeed);
    }
}