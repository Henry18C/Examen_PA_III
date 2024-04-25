package org.example;

public record Const<T> (T head, Lista<T> tail) implements Lista<T>{

    public String toString(){
        return  String.format("[%s,%s]",head,tail.toString());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
