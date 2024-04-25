package org.example;

import org.example.listas.Lista;
import org.example.listas.ListaUtils;

import java.util.function.Function;

import static org.example.listas.ListaUtils.combinedMap;

public class Main {
    public static void main(String[] args) {


        // 1

        Function<Integer, Integer> f1 = x -> x + 2;
        Function<Integer, Integer> f2 = x -> x * 3;
        Function<Integer, Integer> f3 = x -> x + 4;


        Lista<Function<Integer, Integer>> functionList = Lista.of(f1, f2, f3);

        int result = ListaUtils.fsmap(3, functionList);

        System.out.println(result);  // Resultado: f3(f2(f1(3)))

        //2
        Function<Integer, Integer> f11 = x -> x + 1;
        Function<Integer, Integer> f22 = x -> x * 2;
        Function<Integer, Integer> f33 = x -> x * x;

        Lista<Integer> numeros = Lista.of(1, 2, 3);

        // Lista de funciones
        Lista<Function<Integer, Integer>> funcs = Lista.of(f11, f22, f33);

        // Usando combinedMap
        Lista<Lista<Integer>> resultadoMap = combinedMap(numeros, funcs);
        System.out.println("Usando Map: " + resultadoMap);

        //fold

        Lista<Integer> resultadoFold = ListaUtils.combinedFold(resultadoMap);

        System.out.println("Usando Fold: " + resultadoFold);

        //3

        Lista<Integer> resultados = ListaUtils.multEq(2, 3, 6);

        System.out.print("Resultado: ");
        while(!resultados.isEmpty()) {
            System.out.print(resultados.head() + " ");
            resultados = resultados.tail();
        }

        //4

        System.out.println("FF");
        Lista<Integer> ls = Lista.of(12,10,16,11,9,7);
        for(Integer t: ls) {
            System.out.println(t);
        }

    }

    ///5

    Lista<Integer> lista = Lista.of(1, 2, 3, 4);
    //Flowable<Integer> flowable = Flowable.fromIterable(lista);








}