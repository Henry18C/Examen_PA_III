package org.example.listas;

import java.util.Iterator;

public record Cons<T>(T head, Lista<T> tail) implements Lista<T> {
    @Override
    public String toString() {
        return String.format("[%s,%s]", head, tail.toString());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Lista<T> current = Cons.this;

            @Override
            public boolean hasNext() {
                return !current.isEmpty();
            }

            @Override
            public T next() {
                T head = current.head();
                current = current.tail();
                return head;
            }
        };
    }


}
