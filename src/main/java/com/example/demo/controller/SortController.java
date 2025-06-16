package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.SortService;

@RestController
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private SortService sortService;

    @PostMapping("/{algorithmName}")
    public Map<String, Object> sort(@PathVariable String algorithmName, @RequestBody List<Integer> numbers) {
        return sortService.sortByAlgorithmWithTime(algorithmName, numbers);
    }
}
