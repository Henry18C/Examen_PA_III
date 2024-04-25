package com.programacion.avanzada;



    @FunctionalInterface
    public interface Effect<T> {
        void apply(T t);
    }


