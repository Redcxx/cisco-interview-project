package com.luoweilue.ciscointerviewproject;

import java.io.IOException;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimilarityResultTest {

    private final String TEXT_1 = "MOCK_TEXT_1";
    private final String TEXT_2 = "MOCK_TEXT_2";
    private Processor MOCK_PROCESSOR;
    private SimilarityQuery SAMPLE_QUERY;

    @Before
    public void setup() throws IOException {
        MOCK_PROCESSOR = mock(Processor.class);
        String URL_1 = "MOCK_URL_1";
        String URL_2 = "MOCK_URL_2";

        when(MOCK_PROCESSOR.readURLTextContent(URL_1)).thenReturn(TEXT_1);
        when(MOCK_PROCESSOR.readURLTextContent(URL_2)).thenReturn(TEXT_2);
        SAMPLE_QUERY = new SimilarityQuery();
        SAMPLE_QUERY.setUrl1(URL_1);
        SAMPLE_QUERY.setUrl2(URL_2);
    }


    @Test
    public void parseSameTokens() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set("1", "2", "3", "4", "5"));
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set("1", "2", "3", "4", "5"));

        SimilarityResult expected = new SimilarityResult(1.0, null);
        assertEquals(SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR), expected);
    }

    @Test
    public void parseShouldFilterDuplicates() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set("1", "2", "3", "4", "5", "5", "5"));
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set("1", "2", "6", "7", "8", "8", "8"));

        SimilarityResult expected = new SimilarityResult(2.0 / 8.0, null);
        assertEquals(SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR), expected);
    }

    @Test
    public void parseDifferentTokens1() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set("1", "2", "3", "4", "5"));
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set("1", "2", "6", "7", "8"));

        SimilarityResult expected = new SimilarityResult(2.0 / 8.0, null);
        assertEquals(SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR), expected);
    }

    @Test
    public void parseDifferentTokens2() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set("1", "2", "3", "4", "5"));
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set("1", "6", "7", "8", "9"));

        SimilarityResult expected = new SimilarityResult(1.0 / 9.0, null);
        assertEquals(SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR), expected);
    }

    @Test
    public void parseDifferentTokens3() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set("1", "2", "3", "4", "5"));
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set("6", "7", "8", "9", "0"));

        SimilarityResult expected = new SimilarityResult(0.0 / 10.0, null);
        assertEquals(SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR), expected);
    }

    @Test
    public void parseEmptyTokenShouldGiveError1() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.newHashSet());
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set("1", "2", "3", "4", "5"));

        SimilarityResult actual = SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR);
        assertNotNull(actual.getNullableErrorMessage());
    }

    @Test
    public void parseEmptyTokenShouldGiveError2() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set("1", "2", "3", "4", "5"));
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set());

        SimilarityResult actual = SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR);
        assertNotNull(actual.getNullableErrorMessage());
    }

    @Test
    public void parseEmptyTokenShouldGiveError3() {
        when(MOCK_PROCESSOR.tokenize(TEXT_1)).thenReturn(Sets.set());
        when(MOCK_PROCESSOR.tokenize(TEXT_2)).thenReturn(Sets.set());

        SimilarityResult actual = SimilarityResult.parse(SAMPLE_QUERY, MOCK_PROCESSOR);
        assertNotNull(actual.getNullableErrorMessage());
    }

}