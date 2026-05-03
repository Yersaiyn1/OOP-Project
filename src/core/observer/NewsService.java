package core.observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsService implements Subject, Serializable {
    private List<Observer> observers = new ArrayList<>();
    private List<String> newsFeed = new ArrayList<>();

    @Override
    public void attach(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(NewsEvent event) {
        for (Observer o : observers) {
            o.update(event);
        }
    }

    public void publishNews(String newsContent) {
        newsFeed.add(newsContent);
        NewsEvent event = new NewsEvent(newsContent);
        notifyObservers(event);
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }
}