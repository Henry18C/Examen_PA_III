package org.example;


public class Main {
    public static void main(String[] args) {
        System.out.println("Parte 1");

        var ls = Lista.of(1,2,3,4,5,6,7,8);

        var takeLs = ls.take(ls,4);
        System.out.println(takeLs);

        System.out.println("Parte 2");

        var dropLs = ls.drop(ls, 4);
        System.out.println(dropLs);


        System.out.println("Parte 3");
        BinTree<Integer> arbol = ls.toBinTree();
        System.out.println(arbol);



        System.out.println("Parte 4");
        int tamano = arbol.size();
        System.out.println("Tamaño del árbol: " + tamano);

        System.out.println("Parte 5");

        BinTree<Integer> arbol1 = BinTree.<Integer>EmptyTree().buildTree(ls);
        System.out.println(arbol1);




    }
}