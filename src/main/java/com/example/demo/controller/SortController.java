package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SortService;

@RestController
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private SortService sortService;

    @PostMapping("/{algorithmName}")
    public List<Integer> sort(@PathVariable String algorithmName, @RequestBody List<Integer> numbers) {
        return sortService.sortByAlgorithm(algorithmName, numbers);
    }
}
