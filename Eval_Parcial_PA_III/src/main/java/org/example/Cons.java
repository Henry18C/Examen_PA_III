package org.example;

public record Cons<T>(T head, Lista<T> tail) implements Lista<T> {
    @Override
    public String toString() {
        return String.format("[%s,%s]", head, tail.toString());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
