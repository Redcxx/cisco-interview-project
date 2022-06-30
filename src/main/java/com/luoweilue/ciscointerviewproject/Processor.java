package com.luoweilue.ciscointerviewproject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;

public class Processor {

    private static final Pattern TOKENIZER = Pattern.compile("[^0-9a-zA-Z]");

    public String readURLTextContent(String validURL) throws IOException {
        try (Scanner scanner = new Scanner(new URL(validURL).openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");  // \\A means no delimiter
            String html = scanner.hasNext() ? scanner.next() : "";
            return Jsoup.parse(html).text();
        }
    }

    public Set<String> tokenize(String text) {
        // fairly naive processing
        return TOKENIZER.splitAsStream(text)
                .map(String::toLowerCase)
                .filter(token -> !token.isEmpty() && token.trim().length() > 1)
//                .peek(System.out::println)
                .collect(Collectors.toSet());
    }
}
