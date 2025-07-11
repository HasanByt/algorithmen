package com.example.demo.service;

import ch.wiss.magicsort.ISort;
import ch.wiss.magicsort.SortAlgorithm;
import ch.wiss.magicsort.SortFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SortService {

    public Map<String, Object> sortByAlgorithmWithTime(String algorithmName, List<Integer> numbers) {
        SortAlgorithm algorithm = SortAlgorithm.fromDbName(algorithmName);
        ISort sorter = SortFactory.getSorter(algorithm);

        long start = System.nanoTime();
        List<Integer> sorted = sorter.sort(numbers);
        long end = System.nanoTime();

        Map<String, Object> result = new HashMap<>();
        result.put("algorithm", algorithm.getDbName());
        result.put("input", numbers);
        result.put("sorted", sorted);
        result.put("durationMs", (end - start) / 1_000_000.0);

        // Optional: Wenn du Vergleichszähler einbauen willst → später als Erweiterung
        result.put("comparisons", null);

        return result;
    }
}
