package src.main.java.webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLProcessor {
    // Regex pattern to find URLs within the content
    private static final Pattern URL_PATTERN = Pattern.compile(
            "href=\"(http[^\"]*)\"");

    // Processes the content to extract URLs
    public static List<String> process(String content) {
        List<String> urls = new ArrayList<>();
        Matcher matcher = URL_PATTERN.matcher(content);
        while (matcher.find()) {
            urls.add(matcher.group(1)); // Add each found URL to the list
        }
        return urls; // Return the list of extracted URLs
    }
}
