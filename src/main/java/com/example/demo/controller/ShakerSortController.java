package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ShakerSortService;

@RestController
@RequestMapping("/sort")
public class ShakerSortController {

    @Autowired
    private ShakerSortService shakerSortService;

    @PostMapping
    public int[] sortArray(@RequestBody int[] array) {
        return shakerSortService.shakerSort(array);
    }
}
