package com.programacion.avanzada.lista;


import com.programacion.avanzada.recursion.TailCall;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//(1,2,3,4,5)
//(1,(2,(3,(4,(5,(Empty)))))
public interface Lista<T> {

    //por definicion es public static final


    ///variables
    Lista Empty = new Empty();


    ///Metodos ABSTRACT necesitan si o si  que la clase que
    // /los implementa hagan un override de ellos
    T head();

    Lista<T> tail();

    boolean isEmpty();

    ///Metodos STATIC, pertenecen a la interface y no se heredan
    //se coloca un <T> extra al inicio del metodo porque este es static y pertenece a la instancia
    static <T> Lista<T> of(T... elements) {
/* EXPLICACION, se quiere crear una lista de este tipo (a,b,c)
la lista tiene una longitud de 3 y para que se cree correctamente inseramos de derecha a izquierda es decir
que primero se insera c, luego b y fianalemte a

PASO 1 (i=2):

Se crea un nuevo objeto Const con parámetros (c, Empty).
Ahora tmp apunta a este nuevo objeto.
PASO 2 (i=1):

Se crea otro nuevo objeto Const con parámetros (b, tmp), donde tmp ahora apunta al objeto creado en el paso anterior.
Ahora tmp apunta al nuevo objeto creado.
PASO 3 (i=0):

Se crea un último objeto Const con parámetros (a, tmp), donde tmp ahora apunta al objeto creado en el paso anterior.
Ahora tmp apunta al nuevo objeto creado.
Al final de la iteración, tmp apunta a la estructura de lista enlazada completa (a, (b, (c, Empty))). ¡Bien hecho!
 */

        var tmp = Lista.Empty;
        for (int i = elements.length - 1; i >= 0; i--) {
            tmp = new Const(elements[i], tmp);

        }
        return tmp;

    }

    static <T> Lista<T> of(T head, Lista<T> tail) {
        return new Const<>(head, tail);

    }
    ///metodos DEFAULT, pueden tener una definicion del cuerpo del metodo por
    // defecto o la clase que los heredan pueden hacer un override de ellos

    default int count() {
        return 1 + tail().count();


    }

    /* Lista<T> prepend(T elemnt) {
        return new Const<>(elemnt, Lista.Empty);
    }*/
    default Lista<T> prepend(T elemnt) {
        return Lista.of(elemnt,this);
    }

    default Lista<T> append(T elem) {
        //esta iteracion comienza de izquierda a derecha
        /*EXPLICACION, SIENDO (a,(b,(c,Empty))) y queriendo insertar d
        1. Primera iteración:

       * head = a
       * tail = (b, (c, Empty))
       * Se crea una nueva instancia de Const con head = a y el resultado de llamar recursivamente a append en (b, (c, Empty)).
        2. Segunda iteración (recursiva):

        * Ahora estás en la lista (b, (c, Empty)).
        * head = b
        * tail = (c, Empty)
        * Se crea una nueva instancia de Const con head = b y el resultado de llamar recursivamente a append en (c, Empty).
        3. Tercera iteración (recursiva):

        * Ahora estás en la lista (c, Empty).
        * head = c
        * tail = Empty
        * Se crea una nueva instancia de Const con head = c y el resultado de llamar recursivamente a append en Empty (caso base).
        4. CASO BASE, Base de la recursión (Empty):

        * Cuando la lista es Empty, se devuelve una nueva instancia de Const con head = d y tail = Empty (caso base).

        * */
        if (this.isEmpty()) {
            return new Const<>(elem, Lista.Empty);
        } else {
            return new Const<>(
                    this.head(),
                    this.tail().append(elem)
            );

        }
    }

    default Lista<T> insert(int index, T element) {

        if (index == 0) {
            return new Const<>(element, this);
        } else {
            return Lista.of(
                    this.head(),
                    this.tail().insert(index - 1, element)
            );

        }

    }

    default T get(int index) {
        if (index == 0) {
            return this.head();

        } else {
            return this.tail().get(index - 1);
        }
    }

    default Lista<T> take(int n) {
        return n <= 0 || this.isEmpty()
                ? Lista.Empty
                : Lista.of(this.head(), this.tail().take(n - 1));

        /*if(n<=0 ||this.isEmpty()){
            return Lista.Empty;
        }else{
            return  Lista.of(this.head(), this.tail().take(n-1));
        }*/
    }

    default Lista<T> drop(int n) {
        return n <= 0 || this.isEmpty()
                ? this
                : this.tail().drop(n - 1);
        /*if(n<=0 || this.isEmpty()){
           return this;
       }else {
           return this.tail().drop(n-1);
       }*/

    }

    default Lista<T> concat(Lista<T> ls) {
        return this.isEmpty()
                ? ls
                : Lista.of(this.head(), this.tail().concat(ls));
        /*if(this.isEmpty()){
           return ls;
       }else {
           return Lista.of(this.head(), this.tail().concat(ls));
       }*/
    }

    default <U> Lista<U> map(Function<T,U> fn){
        if(isEmpty()){
            return Lista.Empty;
        }else{
            return Lista.of(fn.apply(this.head()),this.tail().map(fn));
        }

    }






    //fonding LtoR
    default  <U> U foldLeft(U identify, Function<U,Function<T,U>> fn){
            U ret= identify;
            var tmp= this;
        while (!tmp.isEmpty())    {
            ret= fn.apply(ret).apply(tmp.head());
            tmp= tmp.tail();
        }
        return  ret;
        }

///                 DEBER






    //fonding LtoR
    default <U> U foldRight(U identify, Function<T, Function<U, U>> fn) {
        if (isEmpty()) {
            return identify;
        } else {
            return fn.apply(head()).apply(tail().foldRight(identify, fn));
        }
    }

    default Lista<T> invertFold(){
        return foldLeft(Lista.Empty,ls->t->ls.prepend(t));
    }
    default <U> Lista<U> mapFold(Function<T,U> fn){
        return foldLeft(Lista.Empty, ls-> t-> ls.append(fn.apply(t)));
    }


    //con right
    default <U> Lista<U> mapFold2(Function<T,U> fn){
        return foldRight(Lista.Empty, t-> ls-> ls.prepend(fn.apply(t)));

    }
    default Integer countFold(){
        return foldLeft(0,n->t->n+1);
    }


    default Lista<T> appendFold(T elem){
        return foldRight(Lista.of(elem), t->ls-> ls.prepend(t));
    }


    default T reduce(T identity, Function<T, Function<T,T> > fn){
        return foldLeft(identity,u->t->fn.apply(u).apply(t));
    }


        //reduccion sin parametros
    default T reduce1( Function<T, Function<T,T> > fn){
        return this.tail().foldLeft(this.head(),u->t->fn.apply(u).apply(t));
    }


   default Lista<T> takeFold(int n) {
       return foldLeft(Lista.Empty,
               ls -> t -> ls.count() < n ? ls.append(t) : ls);
   }


    default Lista<T> dropFold(int n){
        int tot= this.count()-n;

        return foldRight(
                Lista.Empty,
                t->ls->ls.count()<tot?ls.prepend(t):ls);
    }

////

    ///RangeRec rersion recursiva
/*
    default   Lista<Integer> rangeRecursive(Integer start, Integer end){

        if(start< end){
            return Lista.of(start,rangeRecursive(start+1,end));
        }
        else return Lista.Empty ;
    }

    ///RangeRec rersion Corecursiva

*/


    ///range(start, end): versión tail recursiva, versión utilizando unfold
    //unfold(start, function, predicate), versiones: imperativa, recursiva, tail recursiva

    /*default void unfold(T start, Function<T,T> fn, Predicate<T> p){

    }*/

    ///                             DEBER PROGRAMACION III



    //DEBER UNFOLD IMPERATIVO
    static <T> Lista<T> unfold(T start, Function<T, T> fn, Predicate<T> p) {
        Lista<T> result = Lista.Empty;
        T current = start;

        while (p.test(current)) {
            result = result.append(current);
            current = fn.apply(current);
        }

        return result;
    }

    //UNFOLD RECURSIVO
    public static <T> Lista<T> unfoldRecursivo(T start, Function<T, T> fn, Predicate<T> p) {
        if (!p.test(start)) {
            return Lista.Empty;
        } else {
            return Lista.of(start).concat(unfoldRecursivo(fn.apply(start), fn, p));
        }
    }

    // //UNFOLD  TAIL RECURSIVE
    private Lista<T> unfoldTailRecursivo(T current, Function<T, T> fn, Predicate<T> p, Lista<T> acc) {
        if (!p.test(current)) {
            return acc;
        } else {
            return unfoldTailRecursivo(fn.apply(current), fn, p, acc.append(current));
        }
    }





///                          DEBER EJECICIO DE RANGOS

    default Lista<Integer> rangeUnfolding(Integer start, Integer end) {
        return unfold(start, x -> x + 1, x -> x <= end);
    }



    default Lista<Integer> rangeUnfoldRecursivo(int start, int end) {

        return unfoldRecursivo(start, x -> x + 1, x -> x <= end);
    }

    default Lista<Integer> rangeTailRecursive(Integer current, Integer end, Lista<Integer> acc) {
        if (current > end) {
            return acc;
        } else {
            return rangeTailRecursive(current + 1, end, acc.append(current));
        }
    }



    default TailCall<Lista<T>> prependTailCall(T elemnt, TailCall<Lista<T>> tailCall) {
        return TailCall.tailReturn(Lista.of(elemnt, tailCall.eval()));
    }



    default Lista<T> prepend2(T elemnt) {
        return prependTailCall(elemnt, TailCall.tailReturn(this)).eval();
    }

///deber  tail call




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


    //invert
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

}
