package com.luoweilue.ciscointerviewproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

    @GetMapping("/")
    public String similarityQuery(@ModelAttribute SimilarityQuery similarityQuery, Model model) {
        model.addAttribute("query", similarityQuery);
        return "index";
    }

    @PostMapping("/")
    public String similarityResult(@ModelAttribute SimilarityQuery similarityQuery, Model model) {
        model.addAttribute("query", similarityQuery);
        model.addAttribute("result", SimilarityResult.parse(similarityQuery, new Processor()));
        return "index";
    }
}
