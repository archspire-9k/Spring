package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class ArrayController {
    @GetMapping("/array")
    public ArrayResponse index() {
        return new ArrayResponse("Hi", List.of("A", "B", "C"));
    }

    record ArrayResponse(
            String greet,
            List<String> names
    ) {}
}
