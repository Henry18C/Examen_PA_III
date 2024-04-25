package org.example.listas;

import java.util.Iterator;

class Empty implements Lista {
    @Override
    public String toString() {
        return "Empty";
    }

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
    public Iterator iterator() {
        return null;
    }

    public int count() {
        return 0;
    }
}
