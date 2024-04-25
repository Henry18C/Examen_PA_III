package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


//1
        Lista<Integer> progresionRecursiva = progresionAritmeticaRecursiva(2, 3, 5);
        System.out.println(progresionRecursiva);

        Lista<Integer> progresionTailRecursiva = progresionAritmeticaTailRecursiva(2, 3, 5);
        System.out.println(progresionTailRecursiva);


        //2
        Lista<Integer> listaOriginal = Lista.of(2, 5, 8, 11, 14, 17, 20, 23, 26, 29);
        int indice = 5;

        Lista<Lista<Integer>> resultado = dividirLista(listaOriginal, indice);

        // Imprimir las dos sub-listas resultantes
        System.out.println("Sub-lista 1:");
        System.out.println(resultado.head());

        System.out.println("Sub-lista 2:");
        System.out.println(resultado.tail().head());

        Lista<Integer> listaValores = Lista.of(12, 1, 16, 11, 9, 7);
        ResultadoBusqueda resultado1 = buscarMinimoIndice(listaValores, 0);

        System.out.println("Resultado: (índice, min) = (" + resultado1.getIndice() + ", " + resultado1.getMinimo() + ")");


        Lista<Integer> lista1 = Lista.of(12, 10, 16, 11, 9, 7);
        Lista<Integer> lista2 = Lista.of(4, 6, 7, 2);

        Lista<Integer> resultado2 = concatenarListas(lista1, lista2);

        System.out.println("Resultado: " + resultado2);

    }


    public static Lista<Integer> progresionAritmeticaRecursiva(int inicio, int diferencia, int n) {
        return Lista.unfoldRecursivo(inicio, x -> x + diferencia, x -> x <= inicio + (n - 1) * diferencia);
    }

    public static Lista<Integer> progresionAritmeticaTailRecursiva(Integer inicio, Integer diferencia, Integer n) {
        return progresionAritmeticaTailRecursivaAux(inicio, diferencia, n, 0, Lista.Empty);
    }

    private static Lista<Integer> progresionAritmeticaTailRecursivaAux(Integer inicio, Integer diferencia, Integer n, Integer contador, Lista<Integer> acc) {
        if (contador >= n) {
            return acc;
        } else {
            return progresionAritmeticaTailRecursivaAux(inicio, diferencia, n, contador + 1, acc.append(inicio + contador * diferencia));
        }
    }

    //division
    public static Lista<Lista<Integer>> dividirLista(Lista<Integer> lista, int indice) {
        return dividirListaAux(lista, indice, 0, Lista.Empty, Lista.Empty);
    }

    private static Lista<Lista<Integer>> dividirListaAux(Lista<Integer> lista, int indice, int currentIndex, Lista<Integer> acc1, Lista<Integer> acc2) {
        if (lista.isEmpty()) {
            return Lista.of(acc1, acc2);
        } else if (currentIndex < indice) {
            return dividirListaAux(lista.tail(), indice, currentIndex + 1, acc1.append(lista.head()), acc2);
        } else {
            return dividirListaAux(lista.tail(), indice, currentIndex + 1, acc1, acc2.append(lista.head()));
        }
    }
//MINIMO
public static ResultadoBusqueda buscarMinimoIndice(Lista<Integer> lista, int indice) {
    if (lista.isEmpty()) {
        // Si la lista está vacía, devolvemos el resultado con un valor "no encontrado"
        return new ResultadoBusqueda(-1, Integer.MAX_VALUE);
    }

    // Llamada recursiva para buscar en el resto de la lista
    ResultadoBusqueda resultadoRestante = buscarMinimoIndice(lista.tail(), indice + 1);

    int valorActual = lista.head();
    if (valorActual < resultadoRestante.getMinimo()) {
        // Si el valor actual es menor que el mínimo encontrado en el resto de la lista
        return new ResultadoBusqueda(indice, valorActual);
    } else {
        // Si el mínimo está en el resto de la lista, simplemente devolvemos ese resultado
        return resultadoRestante;
    }
}

    // Clase auxiliar para almacenar el resultado de la búsqueda
    static class ResultadoBusqueda {
        private final int indice;
        private final int minimo;

        public ResultadoBusqueda(int indice, int minimo) {
            this.indice = indice;
            this.minimo = minimo;
        }

        public int getIndice() {
            return indice;
        }

        public int getMinimo() {
            return minimo;
        }
    }


    //3

    public static Lista<Integer> concatenarListas(Lista<Integer> lista1, Lista<Integer> lista2) {
        if (lista1.isEmpty()) {
            // Si la primera lista está vacía, simplemente devolvemos la segunda lista
            return lista2;
        } else {
            // Concatenamos el primer elemento de la primera lista con el resultado de la concatenación del resto de la lista1 y la lista2
            return Lista.of(lista1.head(), concatenarListas(lista1.tail(), lista2));
        }
    }

}