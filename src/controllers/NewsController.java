package controllers;

import core.AuthService;
import core.Logger;
import core.observer.NewsService;
import data.Database;
import models.academic.News;
import models.users.Manager;
import models.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for news publishing and feed access.
 */
public final class NewsController {

    private NewsController() {}

    /**
     * Publish a news item. Managers only.
     */
    public static boolean publish(News n) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Manager)) {
            System.out.println("[news] only managers can publish news");
            return false;
        }
        if (n == null) return false;
        ((Manager) current).manageNews(n);
        Logger.getInstance().log(current, "published news '" + n.getTitle() + "'");
        return true;
    }

    /**
     * Get the full news feed (any logged-in user).
     */
    public static List<News> getFeed() {
        if (!AuthService.getInstance().isLoggedIn()) {
            return new ArrayList<>();
        }
        return NewsService.getInstance().getNewsFeed();
    }

    /**
     * Subscribe the current user to news updates.
     */
    public static boolean subscribe() {
        User current = AuthService.getInstance().getCurrentUser();
        if (current == null) {
            System.out.println("[news] login required");
            return false;
        }
        NewsService.getInstance().attach(current);
        Logger.getInstance().log(current, "subscribed to news");
        return true;
    }

    /**
     * Unsubscribe the current user.
     */
    public static boolean unsubscribe() {
        User current = AuthService.getInstance().getCurrentUser();
        if (current == null) return false;
        NewsService.getInstance().detach(current);
        Logger.getInstance().log(current, "unsubscribed from news");
        return true;
    }

    /**
     * Database lookup of stored news (used by AdminView/managers).
     */
    public static List<News> listAllStored() {
        List<News> out = new ArrayList<>();
        for (Object v : Database.getInstance().getNews().values()) {
            if (v instanceof News) out.add((News) v);
        }
        return out;
    }
}