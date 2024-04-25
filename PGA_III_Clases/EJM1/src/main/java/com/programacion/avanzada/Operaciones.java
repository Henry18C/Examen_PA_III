package com.programacion.avanzada;

import java.util.Optional;

public class Operaciones {




    public static  int sumar(int x ,int y){

        while(y>0){
            x++;
                    y--;
        }
        return x;
    }

   public static Optional<Integer> divi(Integer x){
        if(x==0){
            return Optional.empty();

        }else{
            return Optional.of(1/x);
        }
   }





}
