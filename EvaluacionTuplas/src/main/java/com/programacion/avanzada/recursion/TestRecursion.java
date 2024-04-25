package com.programacion.avanzada.recursion;


public class TestRecursion {

    static Integer sumOriginal(Integer x, Integer y){
        return y==0
                ?x
                :sumOriginal(x+1,y-1);
    }

    static TailCall<Integer> sumRec(Integer x, Integer y){
        return y==0
                ? TailCall.tailReturn(x)
                : TailCall.tailSuspend(()->sumRec(x+1, y-1));
    }



    public static void main(String[] args) {


      /* var ret = tailReturn(10);


        var n1 = tailSuspend(ret);
        var n2 = tailSuspend(n1);
        var n3 = tailSuspend(n2);


        var res = n3.eval();
        System.out.println(res);*/

        var n3 = TailCall.tailSuspend(
                ()-> TailCall.tailSuspend(
                        ()-> TailCall.tailSuspend(
                                ()-> TailCall.tailReturn(10))));
        System.out.println(n3.eval());

        var ret = sumRec(3,500_000_000);
        var sum = ret.eval();
        System.out.println(sum);


    }
}
