package org.example.programacion.avanzada.tuple;

public record Tupla<T, U>(T first, U second) {
    @Override
    public String toString() {
        return String.format("(%s,%s)", first, second);
    }
}