package org.example;


import java.util.function.Function;
import java.util.function.Predicate;

//(1,2,3,4,5)
//(1,(2,(3,(4,(5,(Empty)))))
public interface Lista<T> {
    Lista Empty = new Empty();

    T head();

    Lista<T> tail();

    boolean isEmpty();


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
    default Lista<T> unfoldTailRecursivo(T current, Function<T, T> fn, Predicate<T> p, Lista<T> acc) {
        if (!p.test(current)) {
            return acc;
        } else {
            return unfoldTailRecursivo(fn.apply(current), fn, p, acc.append(current));
        }
    }













}
