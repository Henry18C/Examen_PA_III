package org.example.programacion.avanzada;

import org.example.programacion.avanzada.lista.Lista;
import org.example.programacion.avanzada.tuple.Tupla;

import java.util.function.Predicate;

import static org.example.programacion.avanzada.lista.Lista.*;

public class MainTupleDEBER_Evaluacion {
    public static void main(String[] args) {
        System.out.println("******* PARTE 1 ******");


        Lista<Tupla<String, String>> ls1 = Lista.of(
                new Tupla<>("m", "n"),
                new Tupla<>("m", "p"),
                new Tupla<>("m", "o"),
                new Tupla<>("n", "q"),
                new Tupla<>("q", "s"),
                new Tupla<>("q", "r"),
                new Tupla<>("p", "q"),
                new Tupla<>("o", "r")
        );
        //PARTE 1
        System.out.println(ls1);

        String nodoBuscado = "m";

        // Usar el método de sucesores tail-recursivo
        Lista<String> sucesoresTailRecursive = sucesoresTailRecursive(ls1, nodoBuscado);
        System.out.println("Sucesores (Tail Recursive): " + sucesoresTailRecursive);


        // Usar el método de sucesores con fold left
        Lista<String> sucesoresFoldLeft = sucesoresFoldLeft(ls1, nodoBuscado);
        System.out.println("Sucesores (Fold Left): " + sucesoresFoldLeft);

        //PARTE II
   /*Dada un conjunto de monedas de determinadas denominaciones,
    realizar un programa que permita descomponer un valor dado en la suma de valores tomados del
    conjunto de monedas (cambiar un valor por monedas) A manera de ejemplo, considere el conjunto de
    monedas {5,2,1} descomponer el valor 19. El resultado es: {5,5,5,2,2}/*
    */
        System.out.println("******* PARTE 2 ******");

        Lista<Integer> monedas = Lista.of(5, 2, 1);
        int valor = 19;

        Lista<Lista<Integer>> solucion = descomponer(valor, monedas);
        System.out.println("SOLUCION: "+solucion.getTailCall(1));

///PARTE 3
        System.out.println("******* PARTE 3 ******");
        Lista<Integer> lista = Lista.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int n = 3;

        Predicate<Integer> condicion = x -> x % 2 == 0; // Ejemplo de condición: eliminar números pares

        Lista<Integer> resultado = eliminarNElementos(lista, n, condicion);
        System.out.println(resultado);


    }




}
