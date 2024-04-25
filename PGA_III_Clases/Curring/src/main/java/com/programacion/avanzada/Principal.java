package com.programacion.avanzada;

import java.util.function.Function;

public class Principal {


    static int sumar(int x , int y){
    return x+ y;
        }


    public static void main(String[] args) {

        /*
        f(x,y)=x+y
        h(x)=f(x,y)=g(y)
        h(2)=f(2,y))=2+y=g(y)
        */

        //hay tantas flechas como argumentos
        Function<Integer, Function<Integer,Integer>> sumarFn= x->y->x+y;


        var r1= sumar(2,3);
        System.out.println("sumarR1= "+ r1);

        var r2= sumarFn.apply(2).apply(3);

        System.out.println("sumarR2= "+ r2);

        //g(y)=f(2,y)=2+y
        Function<Integer,Integer> fn2=  sumarFn.apply(2);
        System.out.println("sumarFn2= "+ fn2);

        System.out.println(fn2.apply(3));
        }







        }
