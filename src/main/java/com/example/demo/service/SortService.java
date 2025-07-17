package com.example.demo.service;

import ch.wiss.magicsort.ISort;
import ch.wiss.magicsort.SortAlgorithm;
import ch.wiss.magicsort.SortFactory;
import ch.wiss.magicsort.SortResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SortService {

    public Map<String, Object> sortByAlgorithmWithTime(String algorithmName, List<Integer> numbers) {
        SortAlgorithm algorithm = SortAlgorithm.fromDbName(algorithmName);
        ISort sorter = SortFactory.getSorter(algorithm);

        // Neuer Rückgabewert: SortResult
        SortResult resultData = sorter.sort(numbers);

        Map<String, Object> result = new HashMap<>();
        result.put("algorithm", algorithm.getDbName());
        result.put("input", numbers);
        result.put("sorted", resultData.sorted());
        result.put("durationMs", resultData.durationMs());
        result.put("comparisons", resultData.comparisons());

        return result;
    }
}
