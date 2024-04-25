package org.example.programacion.avanzada;
import org.example.programacion.avanzada.lista.Lista;
import org.example.programacion.avanzada.tailcall.TailCall;

public class MainTailCall {


    public static void main(String[] args) {
        System.out.println("TAIL CALL DEBER");
        //Lista1
        Lista<Integer> ls1 = Lista.of(1, 2, 3, 4, 5);
        System.out.println(ls1);


        System.out.println("Append-TailCall: " + Lista.appendTailCall(99, ls1));
        System.out.println("Prepend-TailCall: " + Lista.preppendTailCall(99, ls1));
        System.out.println("Get-TailCall: " + ls1.getTailCall(3));
        System.out.println("Insert-TailCall: " + Lista.insertTailCall(ls1, 99, 2));
        System.out.println("Invert-TailCall: " + Lista.invertTailCall(ls1));
        System.out.println("Take-TailCall: " + Lista.takeTailCall(ls1, 3));
        System.out.println("Drop-TailCall: " + Lista.dropTailCall(ls1, 3));

        //Lista2

        Lista<Integer> ls2 = Lista.of(6, 7, 8, 9);

        System.out.println("Concat-TailCall: " + Lista.concatTailCall(ls1, ls2));
        System.out.println("Map-TailCall: " + Lista.mapTailCall(ls1, (x) -> x * x));
//Range Tail Call
        TailCall<Lista<Integer>> lsRangeBig = Lista.rangeTailCall(1, 500_001, Lista.Empty);
        Lista<Integer> lsRangeTailCallBig = lsRangeBig.eval();
        System.out.println("Get- TailCall: "+lsRangeTailCallBig.getTailCall(500));
        System.out.println("Count-TailCall: " + Lista.countTailCall(lsRangeTailCallBig));


    }
}
