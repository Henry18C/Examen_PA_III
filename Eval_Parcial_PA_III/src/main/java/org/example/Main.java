package org.example;

import java.util.function.Function;
import java.util.function.Predicate;

public class Main {




    static class PosicionCaballo {
        Integer fila;
        Integer columna;

        PosicionCaballo(Integer fila, Integer columna) {
            this.fila = fila;
            this.columna = columna;
        }

        @Override
        public String toString() {
            return "(" + fila + ", " + columna + ")";
        }

        public boolean inside() {
            return fila >= 1 && fila <= 8 && columna >= 1 && columna <= 8;
        }



    }



    public static void main(String[] args) {


        /*
        1. Dada una Lista<Lista<T>>, defina un método flatMap que permita genera una sola lista con todos
        los elementos de cada
         una de las listas originales. Implementar el método utilizando una folding de izquierda a derecha.
        */
        Lista<Lista<Integer>> listaDeListas = Lista.of(
                Lista.of(1, 2, 3),
                Lista.of(4, 5),
                Lista.of(6, 7, 8)
        );




        Lista<Integer> resultado = flatMap(listaDeListas,Function.identity());
        System.out.println(resultado);


        //2. Dada una Lista<T>, defina un método que permita filtrar los elementos de una lista dada una determinada
        //condición. Implementar el método utilizando una folding de izquierda a derecha.
        //Con métodos anteriores, considere el siguiente problema:
/*
        Considere un tablero de ajedrez en el cual la posición del CABALLO se lo representa como una tupla
        Pos(Integer, Integer) la cual contiene la fila y la columna (row, columna). La esquina inferior
        izquierda es la posición (1,1). Recuerde que el CABALLO solo se puede mover en “L”.

*/



        Predicate<Integer> condicion = n -> n % 2 == 0;

        Lista<Integer> listaFiltrada = filter(resultado, condicion);



        ///posicion







        PosicionCaballo posicion = new PosicionCaballo(3, 4);


        boolean estaDentro = posicion.inside();


        System.out.println("La posición " + posicion + " está dentro del tablero: " + estaDentro);






    }

//1
    static  <U,T> Lista<U> flatMap(Lista<T> listaDeListas,Function<T, Lista<U>> fn) {
        return listaDeListas.foldLeft(Lista.Empty, acc -> elem -> acc.concat(fn.apply(elem)));
    }

//2
    static <T> Lista<T> filter(Lista<T> lista,Predicate<T> condition) {
        return lista.foldLeft(Lista.Empty, acc -> elem -> condition.test(elem) ? acc.concat(Lista.of(elem)) : acc);
    }

//2.1




}