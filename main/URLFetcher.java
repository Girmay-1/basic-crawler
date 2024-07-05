package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

public class URLFetcher {
   static Logger logger = Logger.getLogger(URLFetcher.class.getName());

    public static String fetch(String url) {
        StringBuilder content = new StringBuilder();
        try{
            URL urlObj = new URL(url);  //creating a URL object
            BufferedReader in = new BufferedReader(new InputStreamReader(urlObj.openStream())); // Open a stream to read the URL content

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine); // Append each line of content to the StringBuilder
            }
            in.close(); // Close the stream

        }catch (IOException ex){
            logger.severe("exception happened,"+ ex);
        }
        return content.toString();
    }
}
