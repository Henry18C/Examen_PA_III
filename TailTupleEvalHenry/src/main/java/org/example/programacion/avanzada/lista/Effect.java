package org.example.programacion.avanzada.lista;

    @FunctionalInterface
    public interface Effect<T> {
        void apply(T t);
    }


