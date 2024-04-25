package org.example;


import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

//(1,2,3,4,5)
//(1,(2,(3,(4,(5,(Empty)))))
/*
Considere, a manera de ejemplo, la siguiente lista:
Lista1: {4,6,2,3,8}
1. (4 puntos) Generar un método que permita encontrar el máximo del conjunto de datos.
- Realizar un versión recursiva
- Realizar una versión utilizando un folding de izquierda a derecha
Salida: 8
2. (4 puntos) Generar un método que permita encontrar el mínimo del conjunto de datos.
- Realizar un versión recursiva
- Realizar una versión utilizando un folding de derecha a izquierda
Salida: 2
3. (5 puntos) Generar un método que permita encontrar una sublista que no contenga el
valor máximo
Salida: {4,6,2,3}
4. (7 puntos) Con los métodos anteriores, ordenar la lista mediante el método de la burbuja.
- Tomar el mínimo de la lista
- Agregar el mínimo al inicio de la lista formada por el ordenamiento de la lista auxiliar
(sin el mínimo)
Salida: {2,3,4,6,8}

 */
public interface Lista<T >   {



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

//Maximo

    default Optional<T> maxFL(Comparator<T> comparator) {
        return foldLeft(Optional.empty(), option -> current ->
                Optional.ofNullable(option.map(existingMax ->
                                comparator.compare(current, existingMax) > 0 ? current : existingMax)
                        .orElse(current))
        );
    }


    default Optional<T> max(Comparator<T> comparator) {
        if (isEmpty()) {
            return Optional.empty();
        }

        T max = head();
        Lista<T> rest = tail();

        while (!rest.isEmpty()) {
            T current = rest.head();
            if (comparator.compare(current, max) > 0) {
                max = current;
            }
            rest = rest.tail();
        }

        return Optional.of(max);
    }
///Minimo

    default Optional<T> min(Comparator<T> comparator) {
        if (isEmpty()) {
            return Optional.empty();
        }

        T min = head();
        Lista<T> rest = tail();

        while (!rest.isEmpty()) {
            T current = rest.head();
            if (comparator.compare(current, min) < 0) {
                min = current;
            }
            rest = rest.tail();
        }

        return Optional.of(min);
    }


    default Optional<T> minFR(Comparator<T> comparator) {
        return foldRight(Optional.empty(), current ->
                acc -> acc.map(min -> comparator.compare(current, min) < 0 ? current : min)
        );
    }




    /// sin max


    default Lista<T> subListWithoutMax(Comparator<T> comparator) {
        Optional<T> max = max(comparator);

        if (max.isPresent()) {
            T maxValue = max.get();

            Predicate<T> notMaxPredicate = value -> comparator.compare(value, maxValue) != 0;

            return filter(notMaxPredicate);
        } else {
            // Si la lista está vacía, simplemente devolvemos una lista vacía
            return Lista.Empty;
        }
    }

    default Lista<T> filter(Predicate<T> predicate) {
        if (isEmpty()) {
            return Lista.Empty;
        } else {
            if (predicate.test(head())) {
                return tail().filter(predicate).prepend(head());
            } else {
                return tail().filter(predicate);
            }
        }
    }




///4


}
