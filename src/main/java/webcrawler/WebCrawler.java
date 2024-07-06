package src.main.java.webcrawler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class WebCrawler {
    private final String startingUrl;
    private final int maxDepth;
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Logger logger = Logger.getLogger(WebCrawler.class.getName());

    public WebCrawler(String startingUrl, int maxDepth) {
        this.startingUrl = startingUrl;
        this.maxDepth = maxDepth;
    }

    public void startCrawling() {
        crawl(startingUrl, 0);
        shutdownExecutor();
    }

    private void crawl(String url, int depth) {
        if (depth > maxDepth || visitedUrls.contains(url)) {
            return;
        }
        visitedUrls.add(url);

        CompletableFuture.supplyAsync(() -> fetchUrlContent(url), executorService)
                .thenApplyAsync(this::processUrlContent, executorService)
                .thenAcceptAsync(urls -> {
                    for (String nextUrl : urls) {
                        crawl(nextUrl, depth + 1);
                    }
                }, executorService)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        logger.severe("Failed to process URL: " + url + ", error: " + ex);
                    }
                });
    }

    protected String fetchUrlContent(String url) {
        return URLFetcher.fetch(url);
    }

    protected List<String> processUrlContent(String content) {
        return URLProcessor.process(content);
    }

    private void shutdownExecutor() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public Set<String> getVisitedUrls() {
        return visitedUrls;
    }
}
