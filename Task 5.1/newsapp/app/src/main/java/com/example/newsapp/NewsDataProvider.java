package com.example.newsapp;

import java.util.ArrayList;
import java.util.List;

public class NewsDataProvider {

    public static List<NewsItem> getTopStories() {
        List<NewsItem> topStories = new ArrayList<>();

        topStories.add(new NewsItem(1, "Terraria News",
                "Latest updates and news about Terraria",
                R.drawable.news_image,
                getLoremIpsum(2),
                true));

        topStories.add(new NewsItem(2, "Minecraft News",
                "Everything happening in the world of Minecraft",
                R.drawable.news_image,
                getLoremIpsum(3),
                true));

        topStories.add(new NewsItem(3, "Animal Crossing News",
                "Cozy news from Animal Crossing",
                R.drawable.news_image,
                getLoremIpsum(2),
                true));

        topStories.add(new NewsItem(4, "Stardew Valley News",
                "Farming life updates and cozy happenings",
                R.drawable.news_image,
                getLoremIpsum(3),
                true));

        return topStories;
    }

    public static List<NewsItem> getNews() {
        List<NewsItem> news = new ArrayList<>();

        news.add(new NewsItem(5, "Spiritfarer News",
                "Heartfelt stories from Spiritfarer",
                R.drawable.news_image,
                getLoremIpsum(2),
                false));

        news.add(new NewsItem(6, "Unpacking News",
                "Peaceful updates from the world of Unpacking",
                R.drawable.news_image,
                getLoremIpsum(3),
                false));

        news.add(new NewsItem(7, "Coffee Talk News",
                "Chill news from Coffee Talk café",
                R.drawable.news_image,
                getLoremIpsum(2),
                false));

        news.add(new NewsItem(8, "Garden Paws News",
                "Updates from your favorite animal shopkeeping sim",
                R.drawable.news_image,
                getLoremIpsum(3),
                false));

        return news;
    }

    public static List<NewsItem> getRelatedNews(int newsId) {
        List<NewsItem> relatedNews = new ArrayList<>();

        relatedNews.add(new NewsItem(9, "Cozy Gaming Tips",
                "How to enjoy your cozy gaming life",
                R.drawable.news_image,
                getLoremIpsum(2),
                false));

        relatedNews.add(new NewsItem(10, "Upcoming Cozy Games",
                "Stay tuned for future cozy releases",
                R.drawable.news_image,
                getLoremIpsum(3),
                false));

        return relatedNews;
    }

    private static String getLoremIpsum(int paragraphs) {
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nullam euismod, nisl eget aliquam ultricies, nunc nisl aliquet nunc, " +
                "quis aliquam nisl nunc eu nisl. Nullam euismod, nisl eget aliquam ultricies, " +
                "nunc nisl aliquet nunc, quis aliquam nisl nunc eu nisl.\n\n";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < paragraphs; i++) {
            result.append(lorem);
        }
        return result.toString();
    }
}
