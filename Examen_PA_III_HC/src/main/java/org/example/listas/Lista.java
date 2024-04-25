package org.example.listas;

import java.util.Iterator;
import java.util.function.Function;

public interface Lista<T>  extends Iterable<T>{
    Lista Empty = new Empty();

    T head();

    Lista<T> tail();

    boolean isEmpty();

    static <T> Lista<T> of(T head, Lista<T> tail) {
        return new Cons<>(head, tail);
    }

    static <T> Lista<T> of(T... elems) {
        var tmp = Lista.Empty;

        for (int i = elems.length - 1; i >= 0; i--) {
            tmp = Lista.of(elems[i], tmp);
        }

        return tmp;
    }

    default Lista<T> prepend(T elem) {
        return Lista.of(elem, this);
    }

    default <U> Lista<U> map(Function<T,U> fn) {
        return this.isEmpty()
                ? Lista.Empty
                : Lista.of( fn.apply(this.head()), this.tail().map(fn) )
                ;
    }

    default <U> U foldLeft(U identity, Function<U, Function<T,U>> fn) {
        U ret = identity;

        var tmp = this;
        while(!tmp.isEmpty()) {
            ret = fn.apply(ret).apply(tmp.head());
            tmp = tmp.tail();
        }

        return ret;
    }

    default Lista<T> invert() {
        return foldLeft(Lista.Empty, ls->t->ls.prepend(t));
    }



    Iterator<T> iterator();


}