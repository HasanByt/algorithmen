package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SortService {

    public List<Integer> sortByAlgorithm(String algorithmName, List<Integer> numbers) {
        return switch (algorithmName.toLowerCase()) {
            case "shakersort", "shaker" ->
                shakerSort(new ArrayList<>(numbers));
            case "bubblesort", "bubble" ->
                bubbleSort(new ArrayList<>(numbers));
            case "mergesort", "merge" ->
                mergeSort(new ArrayList<>(numbers));
            case "timsort", "tim" ->
                timSort(new ArrayList<>(numbers));

            default ->
                throw new IllegalArgumentException("Unbekannter Algorithmus: " + algorithmName);
        };
    }

    public Map<String, Object> sortByAlgorithmWithTime(String algorithmName, List<Integer> numbers) {
        long start = System.nanoTime();
        List<Integer> sorted = sortByAlgorithm(algorithmName, numbers);
        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        Map<String, Object> result = new HashMap<>();
        result.put("sorted", sorted);
        result.put("durationMs", durationMs);
        return result;
    }

    private List<Integer> shakerSort(List<Integer> list) {
        boolean swapped = true;
        int start = 0;
        int end = list.size() - 1;

        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                if (list.get(i) > list.get(i + 1)) {
                    Collections.swap(list, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;

            swapped = false;
            end--;

            for (int i = end - 1; i >= start; i--) {
                if (list.get(i) > list.get(i + 1)) {
                    Collections.swap(list, i, i + 1);
                    swapped = true;
                }
            }
            start++;
        }
        return list;
    }

    private List<Integer> bubbleSort(List<Integer> list) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return list;
    }

    private List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        List<Integer> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
        List<Integer> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())));

        return merge(left, right);
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    private List<Integer> timSort(List<Integer> list) {
        list.sort(Integer::compareTo); // Intern Timsort in Java
        return list;
    }

}
