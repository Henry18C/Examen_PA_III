package org.example.programacion.avanzada.lista;

class Empty implements Lista {


    @Override
    public Object head() {
        return null;
    }

    @Override
    public Lista tail() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "Empty";
    }


}
