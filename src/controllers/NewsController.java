package controllers;

import core.Logger;
import core.observer.NewsEvent;
import core.observer.NewsService;
import models.academic.News;
import models.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsController implements Serializable {
    private static final long serialVersionUID = 1L;

    // Создаем единственный экземпляр NewsService, чтобы вызывать методы корректно
    private static final NewsService newsService = new NewsService();
    private static final List<News> newsList = new ArrayList<>();

    public static void addNews(User user, String title, String body) {
        News news = new News(title, body);
        newsList.add(news);

        // Логирование действия. Передаем user, если он есть, иначе используем строку
        if (user != null) {
            Logger.getInstance().log(user, "Created news: " + title);
        } else {
            Logger.getInstance().log(new models.users.Teacher("temp", "System", "Admin", "sys@mail.ru", "pass", "123", 0.0, java.time.LocalDate.now(), "Admin"), "Created news: " + title);
        }

        // Публикуем новость через наш экземпляр NewsService
        newsService.publishNews(news.toString());
    }

    public static List<News> getAllNews() {
        return new ArrayList<>(newsList);
    }
}