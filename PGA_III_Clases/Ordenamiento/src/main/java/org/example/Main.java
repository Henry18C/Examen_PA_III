package org.example;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {



        var ls = Lista.of(4,6,2,3,8);




//1
        Optional<Integer> max = ls.max(Integer::compare);
        max.ifPresent(value -> System.out.println("El máximo es: " + value));

        Optional<Integer> maxFL = ls.maxFL(Integer::compare);
        maxFL.ifPresent(value -> System.out.println("El máximoFL es: " + value));


//2
        Optional<Integer> min = ls.min(Integer::compare);
        min.ifPresent(value -> System.out.println("El Minimo es: " + value));

        Optional<Integer> minFR = ls.minFR(Integer::compare);
        min.ifPresent(value -> System.out.println("El MinimoFR es: " + value));
//3
        Comparator<Integer> comparator = Comparator.naturalOrder();
        Lista<Integer> subListaSinMax = ls.subListWithoutMax(comparator);

        // Imprimir la lista original y la sublista sin el valor máximo
        System.out.println("Lista original: " + ls.toString());
        System.out.println("Sublista sin el valor máximo: " + subListaSinMax.toString());

    }


    }







