package com.luoweilue.ciscointerviewproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.awt.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApplicationRESTController {

    @PostMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public String similarityResult(@ModelAttribute SimilarityQuery similarityQuery, Model model) {
        SimilarityResult res = SimilarityResult.parse(similarityQuery, new Processor());

        return JsonNodeFactory.instance.objectNode()
                .put("score", res.getScore())
                .put("error", res.getNullableErrorMessage())
                .toString();
    }
}