package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class BubbleSortService {

    public int[] bubbleSortService(int[] array) {
        int n = array.length;
        boolean getauscht;

        for (int i = 0; i < n - 1; i++) {
            getauscht = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    getauscht = true;
                }
            }
            if (!getauscht)
                break;
        }

        return array;
    }
}
