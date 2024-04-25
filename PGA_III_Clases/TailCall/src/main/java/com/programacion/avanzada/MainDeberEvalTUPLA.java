package com.programacion.avanzada;

import com.programacion.avanzada.lista.Lista;
import com.programacion.avanzada.recursion.TailCall;

import java.util.function.Function;
import java.util.function.Predicate;

import static com.programacion.avanzada.lista.Lista.eliminarNElementos;
import static com.programacion.avanzada.recursion.TailCall.tailReturn;
import static com.programacion.avanzada.recursion.TailCall.tailSuspend;

public class MainDeberEvalTUPLA {


    record Tupla(String t1, String t2){

    }
    public static void main(String[] args) {
        System.out.println("******* PARTE 1 ******");
        Lista<Tupla> ls1 = Lista.of(
                new Tupla("m", "n"),
                new Tupla("m", "p"),
                new Tupla("m", "o"),
                new Tupla("n", "q"),
                new Tupla("q", "s"),
                new Tupla("q", "r"),
                new Tupla("p", "q"),
                new Tupla("o", "r")
        );

//parte 1
        System.out.println("Suc: "+ sucesor(ls1, "m"));
        System.out.println("Suc-Fold: "+ sucesorFold(ls1,"m"));


       // parte 2

        System.out.println("******* PARTE 2 ******");
        var monedas= Lista.of(5,2,1);
        System.out.println("Cambiar moneda"+ cambiarMonedas(monedas,19));
//parte 3


        System.out.println("******* PARTE 3 ******");
        Lista<Integer> lista = Lista.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int n = 3;

        Predicate<Integer> condicion = x -> x % 2 == 0; // Ejemplo de condición: eliminar números pares

        Lista<Integer> resultado = eliminarNElementos(lista, n, condicion);
        System.out.println(resultado);


    }




    static TailCall<Lista<String>> sucesorAux(Lista<String> ret, Lista<Tupla> graph, String nodo){

        if(graph.isEmpty()){
            return tailReturn(ret);
        }else{
            if(graph.head().t1.equals(nodo)){
                var tmp = ret.prepend(graph.head().t2());
                return  TailCall.tailSuspend( ()-> sucesorAux(tmp,graph.tail(),nodo));
            }else{
                return TailCall.tailSuspend(()-> sucesorAux(ret,graph.tail(),nodo));
            }
        }
    }


    static Lista<String> sucesor(Lista<Tupla> graph, String nodo){
        TailCall<Lista<String>> ret = sucesorAux(Lista.Empty,graph,nodo);
        return ret.eval().invertFold();

    }



    static Lista<String> sucesorFold( Lista<Tupla> graph, String nodo) {
        Lista<String> vi = Lista.Empty;
        Function<Lista<String>, Function<Tupla, Lista<String>>> fn =
                ls-> tup->{
            if (tup.t1().equals(nodo)){
                return ls.prepend(tup.t2());
            }else{
                return ls;
            }
                };
        return graph.foldLeft(vi,fn).invertFold();
    }

///parte 2
    static  TailCall<Lista<Integer>> cambiarMonedaAux(Lista<Integer> ret, Lista<Integer> denom, Integer valor){

        if(valor==0){
            return  tailReturn(ret);
        }else{
            if (valor<denom.head()){
                return tailSuspend(()-> cambiarMonedaAux(ret,denom.tail(),valor));
            }else{
                return tailSuspend(()-> cambiarMonedaAux(ret.prepend(denom.head()), denom, valor-denom.head()));
            }
        }
    }

    static Lista<Integer> cambiarMonedas(Lista<Integer> denom, Integer valor){
        TailCall<Lista<Integer>> ret= cambiarMonedaAux(Lista.Empty,denom,valor);
        return ret.eval().invertFold();
    }





}
