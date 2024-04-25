package com.programacion.avanzada;

final class Return <T> implements TailCall<T>{
    private T value;

    public Return(T value) {
        this.value = value;
    }

    @Override
    public T eval() {
        return value;
    }

    @Override
    public TailCall<T> resume() {
        return null;
    }

    @Override
    public boolean isSuspend() {
        return false;
    }
}
