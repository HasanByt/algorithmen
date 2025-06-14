package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Algorithm;
import com.example.demo.repository.AlgorithmRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/algorithms")
public class AlgorithmController {

    @Autowired
    private AlgorithmRepository repository;

    @PostMapping
    public Algorithm saveAlgorithm(@RequestBody Algorithm algo) {
        return repository.save(algo);
    }

    @GetMapping
    public List<Algorithm> getAllAlgorithms() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Algorithm getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
