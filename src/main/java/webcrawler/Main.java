package src.main.java.webcrawler;

public class Main {
    public static void main(String[] args) {
        String startUrl = "http://example.com";
        int maxDepth = 3;
        WebCrawler crawler = new WebCrawler(startUrl,maxDepth);
        crawler.startCrawling();
    }
}
