package com.programacion.avanzada;

import java.util.function.Function;

public class Main1 {
    public static void main(String[] args) {
        var ls = Lista.of(1,2,3,4,5);
        System.out.println(ls);
        System.out.println("prepend"  +ls.prepend(99));
        System.out.println("append"  +ls.append(12));
        System.out.println("insert"  +ls.insert(2,108));
        System.out.println("get "  +ls.get(3));
        System.out.println("take(2) "  +ls.take(2));
        System.out.println("take(20) "  +ls.take(20));
        System.out.println("take(2) "  +Lista.Empty.take(2));
        System.out.println("drop(2) "  +ls.drop(2));
        System.out.println("drop(20) "  +ls.drop(20));
        System.out.println("drop(20) "  +ls.concat(Lista.of(6,7,8)));
      ;
        System.out.println();
        var ls3 = Lista.of("aa","bb","cc");
        System.out.println(ls3);
       // System.out.println(ls3.count());
        System.out.println( ls3.append("xyz"));

        System.out.printf("MAP: "+ls.map(x-> x*2));
        System.out.println();
        System.out.println("fold-Left suma= "+ls.foldLeft( 0, x->y-> x+y));
        System.out.println("fold-Left max=  "+ls.foldLeft( 0, x->y-> Math.max(x,y)));
        System.out.println("fold-Left concat: "+ls.foldLeft( "", s->x->s+String.valueOf(x)));
        System.out.println("invertir: " +ls.invertFold());

        System.out.println("fold-Map: "+ ls.map(t-> "x"+t));
        System.out.println("fold-Map2: "+ ls.map(t-> "x"+t));

        System.out.println("fold-Count: "+ ls.countFold());
        System.out.println("fold-append: "+ ls.appendFold(6)); //de iaquierda a derecha esto no es posible porque el append es la operacion comprementaria
        System.out.println("fold-reduce: "+ ls.reduce(0,x->y->x+y));
        System.out.println("fold-reduce1: "+ ls.reduce1(x->y->x+y)); //sin parametros

        System.out.println("fold-take: "+ ls.takeFold(2));
        System.out.println("fold-drop: "+ ls.dropFold(2));

        ;



//////IMPRESION SIN EFECTOS SECUNDARIOS

        Effect<Integer> printInteger= x->{  ///Efecto
            System.out.println(x);
        };
        //Executable 1
        Function<Executable, Function<Integer, Executable>> fn= ex-> element->()->{
            ex.exec();
            printInteger.apply(element);
        };




        //Executable 2
        Function<Executable, Function<Integer,Executable>> fn1=
                ex->elem->{
                    ex.exec();
                    return () -> printInteger.apply(elem);
                };


        Executable neutro = ()-> {};  //definicion el neutro



        System.out.println("Executable 1");
        var ex11=fn.apply(neutro).apply(1);
        var ex22=fn.apply(ex11).apply(2);
        var ex33=fn.apply(ex22).apply(3);
        var ex44=fn.apply(ex33).apply(4);
        var ex55=fn.apply(ex44).apply(5);
        ex55.exec();

        System.out.println("Executable 2 (Retorno)");
        var ex1=fn1.apply(neutro).apply(1);
        var ex2=fn1.apply(ex1).apply(2);
        var ex3=fn1.apply(ex2).apply(3);
        var ex4=fn1.apply(ex3).apply(4);
        var ex5=fn1.apply(ex4).apply(5);
        fn1.apply(ex5).apply(6);




        ///                          DEBER EJECICIO DE RANGOS


        //System.out.println("RANGE"+ls.rangeRecursive(1,5));

        System.out.println("RANGE-Unfolding Imperativo "+ls.rangeUnfolding(2,4));
        System.out.println("RANGE-UnfoldRecursivo "+ls.rangeUnfoldRecursivo(2,4));
        System.out.println("RANGE-TailRecursivo "+ls.rangeTailRecursive (2,4,Lista.Empty));




    }
}
