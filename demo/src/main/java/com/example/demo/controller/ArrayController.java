package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class ArrayController {
    @GetMapping("/array")
    public String index(Model model) {
        model.addAttribute("array", new ArrayResponse("Hi", List.of("A", "B", "C")));
        return "index";
    }

    record ArrayResponse(
            String greet,
            List<String> names
    ) {}
}
