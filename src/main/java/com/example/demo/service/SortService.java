package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class SortService {

public Map<String, Object> sortByAlgorithmWithTime(String algorithmName, List<Integer> numbers) {
        long start = System.nanoTime();

        Map<String, Object> result;
        switch (algorithmName.toLowerCase()) {
            case "shakersort", "shaker" ->
                result = shakerSort(numbers);
            case "bubblesort", "bubble" ->
                result = bubbleSort(numbers);
            case "mergesort", "merge" ->
                result = mergeSort(numbers);
            case "timsort", "tim" ->
                result = timSort(numbers);
            default ->
                throw new IllegalArgumentException("Unbekannter Algorithmus: " + algorithmName);
        }

        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        result.put("durationMs", durationMs);
        return result;
    }

    private Map<String, Object> shakerSort(List<Integer> numbers) {
        int comparisons = 0;
        List<Integer> list = new ArrayList<>(numbers);
        boolean swapped = true;
        int start = 0;
        int end = list.size() - 1;

        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                comparisons++;
                if (list.get(i) > list.get(i + 1)) {
                    Collections.swap(list, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }

            swapped = false;
            end--;

            for (int i = end - 1; i >= start; i--) {
                comparisons++;
                if (list.get(i) > list.get(i + 1)) {
                    Collections.swap(list, i, i + 1);
                    swapped = true;
                }
            }
            start++;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sorted", list);
        map.put("comparisons", comparisons);
        return map;
    }

    private Map<String, Object> bubbleSort(List<Integer> numbers) {
        int comparisons = 0;
        List<Integer> list = new ArrayList<>(numbers);
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sorted", list);
        map.put("comparisons", comparisons);
        return map;
    }

    private Map<String, Object> mergeSort(List<Integer> numbers) {
        List<Integer> list = new ArrayList<>(numbers);
        ComparisonCounter counter = new ComparisonCounter();
        List<Integer> sorted = mergeSort(list, counter);

        Map<String, Object> map = new HashMap<>();
        map.put("sorted", sorted);
        map.put("comparisons", counter.count);
        return map;
    }

    private List<Integer> mergeSort(List<Integer> list, ComparisonCounter counter) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Integer> left = mergeSort(new ArrayList<>(list.subList(0, mid)), counter);
        List<Integer> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), counter);

        return merge(left, right, counter);
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right, ComparisonCounter counter) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            counter.count++;
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }
        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }

    private Map<String, Object> timSort(List<Integer> numbers) {
        AtomicInteger comparisons = new AtomicInteger(0);

        List<Integer> list = new ArrayList<>(numbers);
        list.sort((a, b) -> {
            comparisons.incrementAndGet(); // Vergleich zählen
            return Integer.compare(a, b);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("sorted", list);
        map.put("comparisons", comparisons);
        return map;
    }

    private static class ComparisonCounter {

        int count = 0;
    }

}
