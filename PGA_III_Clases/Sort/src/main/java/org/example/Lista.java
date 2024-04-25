package org.example;


import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

//(1,2,3,4,5)
//(1,(2,(3,(4,(5,(Empty)))))


/*
Insertion Sort
Durante el proceso de ordenación, se crea una subsecuencia ordenada. A partir de la parte no
ordenada de la secuencia, se toma un valor a la vez en cada pasada y se lo inserta en la parte
ordenada de la secuencia, manteniendo el orden en la subsecuencia ordenada.
Cuando se inserta el último elemento en la subsecuencia ordenada se obtiene la secuencia
final ordenada.
Considere, a manera de ejemplo, la siguiente lista, se desea ordenar la lista de manera
ascendente:
Lista1: {12,10,16,11,9,7}
1.      En la primera pasada, se inicia con el valor 10. La subsecuencia inicial corresponde al
primer valor {12} la cual se encuentra ordenada.
Como el valor 10 es menor que 12, se debe insertar al inicio de la lista {12}, es decir
{10,12}
2.      En la segunda pasada, se analiza el valor 16, como no es menor que ningún valor de
{10,12} se inserta el final. Se obtiene la lista ordenada {10,12,16}
3.      En la tercera pasada, se analiza el valor 11, el cual debe ser insertado en la posición
correcta de la lista {10,12,16}. El elemento 11 se compara con 10, luego con 12. Como
11 es menor que 12, se debe insertar entre 10 y 12. Se obtiene la lista ordenada
{10,11,12,16}.

4.      El proceso se continúa con todos los elementos de la lista
Generar una implementación del algoritmo de ordenamiento por inserción utilizando
programación funcional. Se debe aplicar todas las reglas relacionadas con la programación
funcional.
NOTA: El algoritmo debe ser recursivo, adicionalmente los métodos auxiliares que se
implementen deben ser recursivos.

*/

public interface Lista<T > {

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

        var tmp = Lista.Empty;
        for (int i = elements.length - 1; i >= 0; i--) {
            tmp = new Const(elements[i], tmp);

        }
        return tmp;

    }

    static <T> Lista<T> of(T head, Lista<T> tail) {
        return new Const<>(head, tail);

    }


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



    // ... (resto de la interfaz)

    default Lista<T> insertionSort(Comparator<T> comparator) {
        return insertionSortRec(this, Lista.Empty, comparator);
    }

    private Lista<T> insertionSortRec(Lista<T> unsorted, Lista<T> sorted, Comparator<T> comparator) {
        if (unsorted.isEmpty()) {
            return sorted;
        } else {
            T current = unsorted.head();
            Lista<T> remainingUnsorted = unsorted.tail();
            Lista<T> updatedSorted = insertIntoSorted(current, sorted, comparator);
            return insertionSortRec(remainingUnsorted, updatedSorted, comparator);
        }
    }

    private Lista<T> insertIntoSorted(T element, Lista<T> sorted, Comparator<T> comparator) {
        if (sorted.isEmpty() || comparator.compare(element, sorted.head()) <= 0) {
            return Lista.of(element, sorted);
        } else {
            return Lista.of(sorted.head(), insertIntoSorted(element, sorted.tail(), comparator));
        }
    }

    // ... (resto de la interfaz)


}
