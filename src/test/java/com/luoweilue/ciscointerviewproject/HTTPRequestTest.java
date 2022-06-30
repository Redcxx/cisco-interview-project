package com.luoweilue.ciscointerviewproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HTTPRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void IndexPageShouldContainTitle1() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/",
                String.class)
        ).contains("Compute Similarity");
    }

    @Test
    public void IndexPageShouldContainTitle2() throws Exception {
        assertThat(this.restTemplate.postForObject(
                "http://localhost:" + port + "/",
                new SimilarityQuery(),
                String.class)
        ).contains("Compute Similarity");
    }
}
