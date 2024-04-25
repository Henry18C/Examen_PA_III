package com.programacion.avanzada;

import java.util.function.Supplier;

public interface TailCall <T>{
    public  T eval();
    public TailCall<T> resume();
    public boolean isSuspend();

    static <T> TailCall<T> tailReturn(T value){
        return new Return<>(value);
    }

    static <T> TailCall<T> tailSuspend(Supplier<TailCall<T>> next){
        return new Suspend<>(next);
    }
}
