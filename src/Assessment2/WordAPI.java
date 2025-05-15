package Assessment2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class WordAPI {
    private static final String API_URL = "https://random-word-api.herokuapp.com/word";
    private static final String[] FALLBACK_WORDS = {
            "programming", "hangman", "computer", "java", "swing",
            "database", "algorithm", "interface", "object", "method"
    };

    public String getRandomWord() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);  // Set timeout to 5 seconds

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return getRandomFallbackWord();
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            String responseStr = response.toString().trim();
            // Better parsing of the response that is usually in format ["word"]
            if (responseStr.startsWith("[\"") && responseStr.endsWith("\"]")) {
                return responseStr.substring(2, responseStr.length() - 2);
            } else {
                // Try to parse out any word within the response
                responseStr = responseStr.replaceAll("[\\[\\]\"]", "");
                if (!responseStr.isEmpty()) {
                    return responseStr;
                }
                return getRandomFallbackWord();
            }
        } catch (Exception e) {
            System.err.println("Error accessing word API: " + e.getMessage());
            return getRandomFallbackWord();
        }
    }

    private String getRandomFallbackWord() {
        return FALLBACK_WORDS[new Random().nextInt(FALLBACK_WORDS.length)];
    }
}