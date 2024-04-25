package org.example.listas;

import java.util.List;
import java.util.function.Function;

public class ListaUtils {


//1

    public static <T> T fsmap(T x, Lista<Function<T, T>> fs) {
        return fs.foldLeft(x, acc -> f -> f.apply(acc));
    }


    //2

    //map
    public static <T> Lista<Lista<T>> combinedMap(Lista<T> lista, Lista<Function<T, T>> funcs) {

        return funcs.map(f -> lista.map(e -> f.apply(e)));

    }

    //fold


    public static <T> Lista<T> combinedFold(Lista<Lista<T>> mappedLists) {

        return mappedLists.foldLeft(Lista.Empty,
                acc -> list -> {
                    T head = list.head();
                    Lista<T> tail = acc;
                    return Lista.of(head, tail);
                }
        );


    }


    public static Lista<Integer> multEq(int x, int y, int n) {
        return multEqHelper(x, y, n, Lista.of(1));
    }

    private static Lista<Integer> multEqHelper(int x, int y, int n, Lista<Integer> acc) {
        if (n == 0) {
            return acc;
        } else {
            int last = acc.head();
            int next = last * x * y;
            Lista<Integer> newAcc = Lista.of(next, acc);
            return multEqHelper(x, y, n-1, newAcc);
        }
    }
    
    


    
    
    
}