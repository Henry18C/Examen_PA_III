package org.example;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Lista<T> {
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

    default Lista<T> concat(Lista<T> ls) {
        return this.isEmpty()
                ? ls
                : Lista.of(this.head(), this.tail().concat(ls))
                ;
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
/*
    default <U> Lista<U> flatMap(Function<T, Lista<U>> fn) {
        return this.foldLeft(Lista.Empty, acc -> elem -> acc.concat(fn.apply(elem)));
    }
*/


    /*
    default Lista<T> filter(Predicate<T> condition) {
        return this.foldLeft(Lista.Empty, acc -> elem -> condition.test(elem) ? acc.concat(Lista.of(elem)) : acc);
    }

*/

}