package org.example;


import org.example.arbol.BinTree;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.example.arbol.BinTree.EmptyTree;
import static org.example.arbol.BinTree.NodeTree;

//(1,2,3,4,5)
//(1,(2,(3,(4,(5,(Empty)))))
public interface Lista<T> {

    //por definicion es public static final


    ///variables
    Lista Empty = new Empty();


    ///Metodos ABSTRACT necesitan si o si  que la clase que
    // /los implementa hagan un override de ellos
    T head();

    Lista<T> tail();

    boolean isEmpty();


    static <T> Lista<T> of(T... elements) {


        var tmp = Lista.Empty;
        for (int i = elements.length - 1; i >= 0; i--) {
            tmp = new Const(elements[i], tmp);

        }
        return tmp;

    }

    static <T> Lista<T> of(T head, Lista<T> tail) {
        return new Const<>(head, tail);

    }


    default int count() {
        return 1 + tail().count();


    }


    default Lista<T> prepend(T elemnt) {
        return Lista.of(elemnt,this);
    }

    default Lista<T> append(T elem) {
        //esta iteracion comienza de izquierda a derecha

        if (this.isEmpty()) {
            return new Const<>(elem, Lista.Empty);
        } else {
            return new Const<>(
                    this.head(),
                    this.tail().append(elem)
            );

        }
    }

    default Lista<T> insert(int index, T element) {

        if (index == 0) {
            return new Const<>(element, this);
        } else {
            return Lista.of(
                    this.head(),
                    this.tail().insert(index - 1, element)
            );

        }

    }

    default T get(int index) {
        if (index == 0) {
            return this.head();

        } else {
            return this.tail().get(index - 1);
        }
    }


    default Lista<T> take(Lista<T> lista, int n) {
        if (n <= 0 || lista.isEmpty()) {
            return Lista.Empty;
        } else {
            return new Const<>(lista.head(), take(lista.tail(), n - 1));
        }
    }



    default Lista<T> drop(Lista<T> lista, int n) {
        if (n <= 0 || lista.isEmpty()) {
            return lista;
        } else {
            return drop(lista.tail(), n - 1);
        }
    }



    default BinTree<T> toBinTree() {
        if (isEmpty()) {
            return EmptyTree();
        } else {
            T head = head();
            Lista<T> tail = tail();
            return NodeTree(head, tail.take(tail, tail.count() / 2).toBinTree(), tail.drop(tail, tail.count() / 2).toBinTree());
        }
    }




}
