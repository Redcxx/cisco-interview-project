package com.luoweilue.ciscointerviewproject;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ProcessorTest {
    private static final Processor PROCESSOR_UNDER_TEST = new Processor();

    @Test
    public void tokenizeSplitByNonLetterOrNumber() {
        assertThat(PROCESSOR_UNDER_TEST.tokenize("apple;banana"), containsInAnyOrder("apple", "banana"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("apple banana"), containsInAnyOrder("apple", "banana"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("aaa%bbb;ccc"), containsInAnyOrder("aaa", "bbb", "ccc"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("aaa=111"), containsInAnyOrder("aaa", "111"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("222`111"), containsInAnyOrder("222", "111"));
    }

    @Test
    public void tokenizeFilterSingleLetter() {
        assertThat(PROCESSOR_UNDER_TEST.tokenize("a;b"), new IsEmptyCollection<>());
        assertThat(PROCESSOR_UNDER_TEST.tokenize("a b c d"), new IsEmptyCollection<>());
        assertThat(PROCESSOR_UNDER_TEST.tokenize("aaa%b;ccc"), containsInAnyOrder("aaa", "ccc"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("a=111"), containsInAnyOrder("111"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("222`1"), containsInAnyOrder("222"));
    }

    @Test
    public void tokenizeFilterBlankString() {
        assertThat(PROCESSOR_UNDER_TEST.tokenize(";     ;"), new IsEmptyCollection<>());
        assertThat(PROCESSOR_UNDER_TEST.tokenize("     "), new IsEmptyCollection<>());
        assertThat(PROCESSOR_UNDER_TEST.tokenize("aaa  ;  ccc"), containsInAnyOrder("aaa", "ccc"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("= 22  11"), containsInAnyOrder("22", "11"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("   222`     "), containsInAnyOrder("222"));
    }

    @Test
    public void tokenizeLowercaseToken() {
        assertThat(PROCESSOR_UNDER_TEST.tokenize("aAa aaa"), containsInAnyOrder("aaa"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize(" bb bB BB  "), containsInAnyOrder("bb"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("AAA CCC"), containsInAnyOrder("aaa", "ccc"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("= 22 11"), containsInAnyOrder("22", "11"));
        assertThat(PROCESSOR_UNDER_TEST.tokenize("AB   A222A"), containsInAnyOrder("ab", "a222a"));
    }

}