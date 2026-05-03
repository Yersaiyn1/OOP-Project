package core.observer;

import models.academic.News;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NewsService — Singleton + Subject.
 *
 * Manages subscriptions and broadcasts NewsEvents to all attached
 * Observers when a News item is published.
 */
public class NewsService implements Subject, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Observer> observers = new ArrayList<>();
    private final List<News> newsFeed = new ArrayList<>();

    private NewsService() {}

    private static class Holder {
        private static final NewsService INSTANCE = new NewsService();
    }

    public static NewsService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Publish a news item: store it in the feed and notify all observers.
     */
    public void publishNews(News n) {
        if (n == null) return;
        newsFeed.add(n);
        notifyObservers(new NewsEvent(n));
    }

    @Override
    public void attach(Observer o) {
        if (o == null) return;
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(NewsEvent e) {
        for (Observer o : observers) {
            o.update(e);
        }
    }

    public List<News> getNewsFeed() {
        return Collections.unmodifiableList(newsFeed);
    }

    public List<Observer> getObservers() {
        return Collections.unmodifiableList(observers);
    }
}