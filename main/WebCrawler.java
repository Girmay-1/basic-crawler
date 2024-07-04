package main;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class WebCrawler {
    private final String startingUrl;
    private final int maxDepth;
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    Logger logger = Logger.getLogger(WebCrawler.class.getName());

    public WebCrawler(String startingUrl, int maxDepth){
        this.startingUrl = startingUrl;
        this.maxDepth = maxDepth;
    }

    public void startCrawling() {
        try {
            crawl(startingUrl, 0);
        } finally {
            executorService.shutdown();
        }
    }

    private void crawl(String url, int depth){
        if(depth > maxDepth || visitedUrls.contains(url)){
            return;
        }
        visitedUrls.add(url);
        //asynchronously fetching URLs
        CompletableFuture.supplyAsync(() -> URLFetcher.fetch(url), executorService)
                .thenApplyAsync(content -> URLProcessor.process(content), executorService)
                .thenAcceptAsync(urls -> {
                    for (String nextUrl : urls) {
                        crawl(nextUrl, depth + 1);
                    }
                }, executorService)
                .exceptionally(ex -> {
                    logger.severe("Failed to process URL: " + url + ", error: " + ex);
                    return null;
                });
    }
}
