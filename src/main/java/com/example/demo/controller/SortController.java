package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SortService;

@RestController
@RequestMapping("/sort")
@CrossOrigin(origins = "http://localhost:5173") // falls du React lokal auf Port 5173 laufen lässt
public class SortController {

    @Autowired
    private SortService sortService;

    @PostMapping("/{algorithmName}")
    public List<Integer> sort(@PathVariable String algorithmName, @RequestBody List<Integer> numbers) {
        return sortService.sortByAlgorithm(algorithmName, numbers);
    }
}