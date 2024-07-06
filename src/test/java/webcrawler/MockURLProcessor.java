package src.test.java.webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockURLProcessor {
    private static final Pattern URL_PATTERN = Pattern.compile("href=\"(http[^\"]*)\"");

    public static List<String> process(String content) {
        List<String> urls = new ArrayList<>();
        Matcher matcher = URL_PATTERN.matcher(content);
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }
}
