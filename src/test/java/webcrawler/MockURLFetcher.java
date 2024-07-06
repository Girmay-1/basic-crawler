package src.test.java.webcrawler;

import java.util.HashMap;
import java.util.Map;

public class MockURLFetcher {
    private static final Map<String, String> MOCK_CONTENT = new HashMap<>();

    static {
        MOCK_CONTENT.put("http://example.com", "<a href=\"http://example.com/page1\">Page 1</a>");
        MOCK_CONTENT.put("http://example.com/page1", "");
    }

    public static String fetch(String url){
        return MOCK_CONTENT.getOrDefault(url, "");
    }
}
