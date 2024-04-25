package com.programacion.avanzada;

public class Principal {

    public static void main(String[] args) {

        //forma Tres
        int res = Operaciones.sumar(2, 5);
        System.out.println("La suma es: "+res);

        var ref= Operaciones.divi(0);

        if(ref.isPresent()){
            System.out.println("La divicion es: "+ ref.get());
        }else{
            System.out.println("No existe la divicion");
        }


        //forma 2
        //Function<Integer,Optional<Integer>>= x-






    }
}
