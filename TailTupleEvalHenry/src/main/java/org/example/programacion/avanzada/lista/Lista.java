package org.example.programacion.avanzada.lista;


import org.example.programacion.avanzada.tailcall.TailCall;
import org.example.programacion.avanzada.tuple.Tupla;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public interface Lista<T> {
    Lista Empty=new Empty();
    T head();
    Lista<T> tail();
    boolean isEmpty();

    private static <T> Lista<T> of(T head, Lista<T> tail){
        return new Const<>(head,tail);
    }

    static <T> Lista<T> of(T... elems){
        var tmp= Lista.Empty;

        for(int i=elems.length-1;i>=0;i--){
            tmp= new Const(elems[i],tmp);
        }

        return tmp;
    }

    static <T> int countTRBase(int contador, Lista<T> lista){
        if(lista.isEmpty()){
            return contador;
        }else{
            return countTRBase(contador+1,lista.tail());
        }
    }
    static <T> int countTailRecursive(Lista<T> lista){
        return countTRBase(0,lista);
    }

    static <T> TailCall<Integer> countTCAux(Lista<T> lista, Integer intit) {
        if (lista.isEmpty()) {
            return TailCall.tailReturn(intit);
        } else {
            Supplier<TailCall<Integer>> ret = () ->
                    countTCAux(lista.tail(), intit + 1);
            return TailCall.tailSuspend(ret);
        }
    }

    static <T> Integer countTailCall(Lista<T> lista) {

        return countTCAux(lista, 0).eval();
    }

    //PREPEND
    static <T> Lista<T> preppendTRBase(T val, Lista<T> original, Lista<T> acum) {
        if (acum.isEmpty()) {
            return Lista.of(val, original);
        } else {
            return preppendTRBase(val,original.tail(), Lista.of(original.head(),acum));
        }
    }

    static <T> Lista<T> preppendTailRecursive(T val, Lista<T> original) {
        return preppendTRBase(val, original, Lista.Empty);

    }

    static <T> TailCall<Lista<T>> preppendTCAux(T val, Lista<T> original) {
        if (original.isEmpty()) {
            return TailCall.tailReturn(Lista.of(val));
        } else {
            return TailCall.tailReturn(Lista.of(val, original));
        }
    }

    static <T> Lista<T> preppendTailCall(T val, Lista<T> original) {
        return preppendTCAux(val, original).eval();
    }

    //APPEND

    static <T> Lista<T> appendTRBase(Lista<T> orginal, Lista<T> acum, T elem) {
        if (acum.isEmpty()) {
            return invertirTailRecursive(preppendTailRecursive(elem, invertirTailRecursive(orginal)));
        } else {
            return appendTRBase(orginal.tail(), preppendTailRecursive(orginal.head(),acum), elem);
        }
    }
    static <T> Lista<T> appendTailRecursive(Lista<T> list, T elem) {
        return appendTRBase(list, Lista.Empty, elem);
    }

    static <T> TailCall<Lista<T>> appendTCAux(Lista<T> original, Lista<T> acum, T elem) {
        if (acum.isEmpty()) {
            return TailCall.tailReturn(invertTailCall(Lista.of(elem, invertTailCall(original)))); //Ojo el invertir
        } else {
            return TailCall.tailSuspend(() -> appendTCAux2(original.tail(), preppendTailCall(original.head(),acum), elem));
        }
    }
    static <T> Lista<T> appendTailCall(T elem, Lista<T> list) {
        return appendTCAux(list, Lista.of(), elem).eval();
    }
    static <T> TailCall<Lista<T>> appendTCAux2(Lista<T> orginal, Lista<T> acum, T elem) {
        if (acum.isEmpty()) {
            return TailCall.tailReturn(invertTailCall(Lista.of(elem, orginal))); //Ojo el invertir
        } else {
            return TailCall.tailSuspend(() -> appendTCAux(Lista.of(orginal.head(), acum), acum.tail(), elem));
        }
    }

    //INSERT
    static <T> Lista<T> insertTRBase(Lista<T> original, Lista<T> acum, T elem, int index){
        if(index==0){
            return invertirTailRecursive(concatTailRecursive(invertirTailRecursive(original), Lista.of(elem,acum)));
        }else{
            return insertTRBase(original.tail(), preppendTailRecursive(original.head(),acum), elem,index-1);
        }

    }
    static <T> Lista<T> insertTailRecursive(Lista<T> orginal, T elem, int index) {

        return insertTRBase(orginal, Lista.Empty, elem,index);

    }
    static <T> TailCall<Lista<T>> insertTCBase(Lista<T> original, Lista<T> acum, T elem, int index){
        if(index==0){
            return TailCall.tailReturn(invertirTailRecursive(concatTailRecursive(invertirTailRecursive(original), Lista.of(elem,acum))));    //Ojo el concat
        }else{
            Supplier<TailCall<Lista<T>>> ret=()->insertTCBase(original.tail(), preppendTailRecursive(original.head(),acum), elem,index-1);
            return TailCall.tailSuspend(ret);
        }
    }
    static <T> Lista<T> insertTailCall(Lista<T> orginal, T elem, int index) {

        return insertTCBase(orginal, Lista.of(), elem,index).eval();

    }

    //GET

    default TailCall<T> getTCAux(int index){
        if(index==0 ){
            return TailCall.tailReturn(this.head());
        }else{
            Supplier<TailCall<T>> ret= ()-> this.tail().getTCAux(index-1);
            return TailCall.tailSuspend(ret);
        }
    }

    default T getTailCall(int index){
        return getTCAux(index).eval();
    }

    //TAKE
    static <T> Lista<T> takeTRBase(Lista<T> list, Lista<T> acum, Integer elem) {
        if (list.isEmpty()) {
            return Lista.Empty;
        } else {
            if (elem == 0) {
                return acum;
            } else {
                return takeTRBase(list.tail(), preppendTailRecursive(list.head(), acum), elem - 1);
            }
        }
    }

    static <T> Lista<T> takeTailRecursive(Lista<T> list, Integer elem) {
        return invertirTailRecursive(takeTRBase(list, Lista.Empty, elem));
    }
    static <T> TailCall<Lista<T>> takeTCAux(Lista<T> list, Lista<T> acum, Integer elem) {
        if (list.isEmpty()) {
            return TailCall.tailReturn(Lista.Empty);
        } else {
            if (elem == 0) {
                return TailCall.tailReturn(acum);
            } else {
                Supplier<TailCall<Lista<T>>> ret = () ->
                        takeTCAux(list.tail(), preppendTailCall(list.head(), acum), elem - 1);
                return TailCall.tailSuspend(ret);
            }
        }

    }
    static <T> Lista<T> takeTailCall(Lista<T> list, Integer elem) {
        return invertTailCall(takeTCAux(list, Lista.of(), elem).eval());
    }

    //DROP

    static <T> Lista<T> dropTRBase(Lista<T> list, Integer elem) {
        if (!(elem == 0)) {

            return dropTRBase(list.tail(), elem - 1);

        } else if (elem == 0) {

            return list;
        } else {
            return list.tail();
        }

    }
    static <T> Lista<T> dropTailRecursive(Lista<T> list, Integer elem) {

        return dropTRBase(list, elem);
    }
    static <T> TailCall<Lista<T>> dropTCAux(Lista<T> list, Integer elem) {
        if (!(elem == 0)) {
            Supplier<TailCall<Lista<T>>> ret = () ->
                    dropTCAux(list.tail(), elem - 1);
            return TailCall.tailSuspend(ret);
        } else if (elem == 0) {
            return TailCall.tailReturn(list);
        } else {
            return TailCall.tailReturn(list.tail());
        }
    }
    static <T> Lista<T> dropTailCall(Lista<T> list, Integer elem) {
        return dropTCAux(list, elem).eval();
    }

    //CONCAT

    static <T> Lista<T> concatTRBase(Lista<T> primero, Lista<T> segundo, Lista<T> acumulador) {
        if(segundo.isEmpty() && primero.isEmpty()){
            return invertirTailRecursive(acumulador);
        }else if(primero.isEmpty()){
            return concatTRBase(primero,segundo.tail(), preppendTailRecursive(segundo.head(),acumulador));
        }else{
            return concatTRBase(primero.tail(),segundo, preppendTailRecursive(primero.head(),acumulador));

        }
    }
    static <T> Lista<T> concatTailRecursive(Lista<T> primero, Lista<T> segundo) {
        return concatTRBase(primero,segundo, Lista.Empty);
    }

    static <T> TailCall<Lista<T>> concatTCAux(Lista<T> ListaTC1, Lista<T> ListaTC2) {
        if (ListaTC1.isEmpty()) {
            return TailCall.tailReturn(ListaTC2);
        } else {
            Supplier<TailCall<Lista<T>>> ret1 = () ->
                    concatTCAux(ListaTC1.tail(), preppendTailCall(ListaTC1.head(), ListaTC2));
            return TailCall.tailSuspend(ret1);
        }
    }

    static <T> Lista<T> concatTailCall(Lista<T> lista1, Lista<T> lista2) {
        return concatTCAux(invertTailCall(lista1), lista2).eval();
    }

    //MAP

    static <T, U> Lista<U> mapTRBase(Lista<T> original, Lista<U> acumulador, Function<T, U> fn) {
        if (original.isEmpty()) {
            return invertirTailRecursive(acumulador);
        } else {
            return mapTRBase(original.tail(), Lista.of(fn.apply(original.head()), acumulador), fn);
        }
    }

    static <T,U> Lista<U> mapTailRecursive(Lista<T> original, Function<T, U> fn) {
        return mapTRBase(original, Lista.Empty, fn);
    }

    static <T, U> TailCall<Lista<U>> mapTCBase(Lista<T> original, Lista<U> acumulador, Function<T, U> fn) {
        if (original.isEmpty()) {
            return TailCall.tailReturn(invertirTailRecursive(acumulador));
        } else {
            Supplier<TailCall<Lista<U>>> ret= ()->mapTCBase(original.tail(), Lista.of(fn.apply(original.head()), acumulador), fn);
            return TailCall.tailSuspend(ret);
        }
    }

    static <T,U> Lista<U> mapTailCall(Lista<T> original, Function<T, U> fn) {
        return (Lista<U>) mapTCBase(original, Lista.Empty, fn).eval();
    }

    //FOLD LEFT
    default <U> U foldLeft(U identity, Function<U,Function<T,U>> fn){
        U ret = identity;
        var tmp=this;

        while(!tmp.isEmpty()){
            ret =fn.apply(ret).apply(tmp.head());
            tmp=tmp.tail();
        }

        return ret;
    }


    //INVERT

    static <T> Lista<T> invertirTRBase(Lista<T> listaTC, Lista<T> acum) {
        if (listaTC.isEmpty()) {
            return acum;
        } else {
            return invertirTRBase(listaTC.tail(), preppendTailRecursive(listaTC.head(), acum));
        }
    }

    static <T> Lista<T> invertirTailRecursive(Lista<T> list) {
        return invertirTRBase(list, Lista.Empty);
    }

    static <T> TailCall<Lista<T>> invertirTCAux(Lista<T> lista, Lista<T> acum) {
        if (lista.isEmpty()) {
            return TailCall.tailReturn(acum);
        } else {
            Supplier<TailCall<Lista<T>>> ret = () ->
                    invertirTCAux(lista.tail(), preppendTailCall(lista.head(), acum));
            return TailCall.tailSuspend(ret);
        }
    }

    static <T> Lista<T> invertTailCall(Lista<T> list) {
        return invertirTCAux(list, Lista.of()).eval();
    }


    static TailCall<Lista<Integer>> rangeTailCall(Integer start, Integer end, Lista<Integer> acc) {
        if (start < end) {
            Supplier<TailCall<Lista<Integer>>> ret = () ->{
                return  rangeTailCall(start,end - 1, preppendTailCall(end-1, acc));
            };

            return TailCall.tailSuspend(ret);

        } else {
            return TailCall.tailReturn(acc);
        }


    }

////DEBER

    ///PARTE 1
public static <T, U> Lista<U> sucesoresTailRecursive(Lista<Tupla<T, U>> lista, T nodo) {
    return sucesoresTRBase(lista, Lista.Empty, nodo);
}


             //tail Recursive
    private static <T, U> Lista<U> sucesoresTRBase(Lista<Tupla<T, U>> lista, Lista<U> acumulador, T nodo) {
        if (lista.isEmpty()) {
            return invertirTailRecursive(acumulador);
        } else {
            Tupla<T, U> head = lista.head();
            Lista<Tupla<T, U>> tail = lista.tail();

            if (head.first().equals(nodo)) {
                acumulador = preppendTailCall(head.second(), acumulador);
            }

            return sucesoresTRBase(tail, acumulador, nodo);
        }
    }


 /// Folding

    public static <T, U> Lista<U> sucesoresFoldLeft(Lista<Tupla<T, U>> lista, T nodo) {
        return lista.foldLeft(Lista.Empty, acc -> elem -> {
            if (elem.first().equals(nodo)) {
                return preppendTailCall(elem.second(), acc);
            } else {
                return acc;
            }
        });
    }
    ///PARTE 2

    static Lista<Lista<Integer>> descomponer(int valor, Lista<Integer> monedas) {
        return descomponerRec(valor, monedas, Lista.Empty);
    }

    static Lista<Lista<Integer>> descomponerRec(int valor, Lista<Integer> monedas, Lista<Integer> acum) {
        if (valor == 0) {
            return Lista.of(acum);
        } else if (valor < 0 || monedas.isEmpty()) {
            return Lista.Empty;
        } else {
            // Tomar la moneda actual o pasar a la siguiente
            Lista<Lista<Integer>> conMonedaActual = descomponerRec(valor - monedas.head(), monedas, Lista.of(monedas.head(), acum));
            Lista<Lista<Integer>> sinMonedaActual = descomponerRec(valor, monedas.tail(), acum);

            return Lista.concatTailCall(conMonedaActual, sinMonedaActual);
        }
    }


    //PARTE III


    static <T> Lista<T> eliminarNElementos(Lista<T> lista, int n, Predicate<T> condicion) {
        return (Lista<T>) eliminarNElementosRec(lista, n, condicion, Lista.Empty, Lista.Empty).eval();
    }

    static <T> TailCall<Lista<T>> eliminarNElementosRec(Lista<T> lista, int n, Predicate<T> condicion, Lista<T> acumEliminados, Lista<T> acumRestantes) {
        if (n == 0 || lista.isEmpty()) {
            return TailCall.tailReturn(concatTailRecursive(invertTailCall(acumRestantes), lista));
        } else if (condicion.test(lista.head())) {
            return TailCall.tailSuspend(() ->
                    eliminarNElementosRec(lista.tail(), n - 1, condicion, preppendTailRecursive(lista.head(), acumEliminados), acumRestantes)
            );
        } else {
            return TailCall.tailSuspend(() ->
                    eliminarNElementosRec(lista.tail(), n, condicion, acumEliminados, preppendTailRecursive(lista.head(), acumRestantes))
            );
        }
    }




}




