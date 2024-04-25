package org.example;

import org.example.Lista;

public interface BinTree<T> {

    String toString();

    int size();

    static <T> BinTree<T> EmptyTree() {
        return new EmptyTree<>();
    }

    static <T> BinTree<T> NodeTree(T value, BinTree<T> left, BinTree<T> right) {
        return new NodeTree<>(value, left, right);
    }

    record EmptyTree<T>() implements BinTree<T> {
        public String toString() {
            return "EmptyTree";
        }

        public int size() {
            return 0;
        }
    }

    record NodeTree<T>(T value, BinTree<T> left, BinTree<T> right) implements BinTree<T> {
        public String toString() {
            return String.format("[%s, %s, %s]", value, left, right);
        }

        public int size() {
            return 1 + left.size() + right.size();
        }
    }

    default BinTree<T> buildTree(Lista<T> lista) {
        if (lista.isEmpty()) {
            return EmptyTree();
        } else {
            return NodeTree(
                    lista.head(),
                    buildTree(lista.tail().take(lista.tail(), lista.tail().count() / 2)),
                    buildTree(lista.tail().drop(lista.tail(), lista.tail().count() / 2))
            );
        }
    }
}