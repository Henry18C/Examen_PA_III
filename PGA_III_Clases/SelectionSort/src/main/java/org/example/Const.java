package org.example;

public record Const<T> (T head, Lista<T> tail) implements Lista<T>{

    // las clases de tipo RECORD me sirven para crear clases inmutables, los tributo
    // de esta son todos de tipo final
    //la clase crea automáticamente métodos como equals, hashCode, y toString
    // lo que simplifica la creacion de estas


    /*private final T head;
    private final Lista<T> tail;

    Const(T head, Lista<T> tail){
        this.head=head;
        this.tail=tail;
    }

    public T getHead() {
        return head;
    }
    public Lista<T> getTail(){
        return tail;
    }*/

    public String toString(){
        return  String.format("[%s,%s]",head,tail.toString());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
