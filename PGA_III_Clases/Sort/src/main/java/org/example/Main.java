package org.example;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var ls = Lista.of(12,10,16,11,9,7);
        Comparator<Integer> comparator = Integer::compare; // O cualquier otro comparador que necesites
        Lista<Integer> sortedList = ls.insertionSort(comparator);
        System.out.println("Lista original: "+ls);
        System.out.println("Lista ordenada: "+sortedList);



    }
}