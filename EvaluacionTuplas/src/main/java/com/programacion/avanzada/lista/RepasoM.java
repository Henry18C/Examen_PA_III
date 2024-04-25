package com.programacion.avanzada.lista;


public class RepasoM {

    public static void main(String[] args) {

        //Const<Integer> n5 = new Const<>(5,Lista.Empty);
        var n6= Lista.Empty;
        var n5= new Const<>(5,n6);
        var n4= new Const<>(4,n5);
       var n3= new Const<>(3,n4);
       var n2= new Const<>(2,n3);
       var n1= new Const<>(1,n2);
      System.out.println(n1);
      System.out.println("empty: "+n1.isEmpty());
      System.out.println("empty "+Lista.Empty.isEmpty());
      //Lista<Integer> ls=
        var tmp= n1;
        /*while (!tmp.isEmpty()){
            System.out.println(tmp.head());
            //tmp=tmp.tail();
        }*/
        //
        Lista<Integer> ls2 = new Const<>(1,
                new Const<>(2,
                        new Const<>(3,
                                new Const<>(4,
                                        new Const<>(5,Lista.Empty)))));
        System.out.println(ls2);
    //////////





        }
}
