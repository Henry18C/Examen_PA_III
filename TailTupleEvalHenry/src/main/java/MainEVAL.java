import org.example.programacion.avanzada.lista.Lista;
import org.example.programacion.avanzada.tailcall.TailCall;
import org.example.programacion.avanzada.tuple.Tupla;

public class MainEVAL {



    record Tupla(String t1, String t2){}
    public static void main(String[] args) {
        System.out.println("******* PARTE 1 ******");
        Lista<org.example.programacion.avanzada.tuple.Tupla<String, String>> ls1 = Lista.of(
                new org.example.programacion.avanzada.tuple.Tupla<>("m", "n"),
                new org.example.programacion.avanzada.tuple.Tupla<>("m", "p"),
                new org.example.programacion.avanzada.tuple.Tupla<>("m", "o"),
                new org.example.programacion.avanzada.tuple.Tupla<>("n", "q"),
                new org.example.programacion.avanzada.tuple.Tupla<>("q", "s"),
                new org.example.programacion.avanzada.tuple.Tupla<>("q", "r"),
                new org.example.programacion.avanzada.tuple.Tupla<>("p", "q"),
                new org.example.programacion.avanzada.tuple.Tupla<>("o", "r")
        );






    }







}
