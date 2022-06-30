package com.luoweilue.ciscointerviewproject;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
@AllArgsConstructor
public class SimilarityResult {

    private static Logger logger = LoggerFactory.getLogger(SimilarityResult.class);

    double score;
    String nullableErrorMessage;

    public static SimilarityResult parse(SimilarityQuery similarityQuery, Processor processor) {

        try {
            double score = computeScore(similarityQuery, processor);
            return new SimilarityResult(score, null);
        } catch (IOException | ArithmeticException e) {
            return new SimilarityResult(-1, e.getMessage());
        }

    }

    private static double computeScore(SimilarityQuery similarityQuery, Processor processor) throws IOException {
        Set<String> url1Tokens = processor.tokenize(processor.readURLTextContent(similarityQuery.getUrl1()));
        Set<String> url2Tokens = processor.tokenize(processor.readURLTextContent(similarityQuery.getUrl2()));

        if (url1Tokens.size() == 0) {
            throw new IOException("No text token found: " + similarityQuery.getUrl1());
        }

        if (url2Tokens.size() == 0) {
            throw new IOException("No text token found: " + similarityQuery.getUrl2());
        }

        long intersect = url1Tokens.stream().distinct().filter(url2Tokens::contains).count();
        long union = Stream.concat(url1Tokens.stream(), url2Tokens.stream()).distinct().count();

        if (union == 0) {
            return 0.0;
        } else {
            return intersect / (double) union;
        }
    }

}
