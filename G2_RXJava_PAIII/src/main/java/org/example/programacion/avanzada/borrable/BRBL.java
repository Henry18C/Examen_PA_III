package org.example.programacion.avanzada.borrable;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;
import reactor.core.publisher.Flux;

public class BRBL {

    public static void main(String[] args) {
        List<Integer> v= List.of(1,2,3,4,5);
        System.out.println("--------MAP----------");
        Observable.just(v).map(x->x.get(0)).subscribe(
                i-> System.out.println("val: "+ i)
        );


        // Crear una lista de números
      List<Integer> numerosIterable = List.of(1, 2, 3, 4, 5);

        // Convertir la lista en un flujo reactivo usando Flux.fromIterable()
        Flux<Integer> flujoNumeros = Flux.fromIterable(numerosIterable);

        // Suscribirse al flujo y manejar los elementos
        flujoNumeros.subscribe(
                numero -> System.out.println("Número: " + numero),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo completado")
        );



        // Crear un iterable (en este caso, una lista)
        List<String> iterableNombres = Arrays.asList("Juan", "María", "Carlos");

        // Usar Flux.fromIterable con el objeto iterable
        Flux<String> fluxNombres = Flux.fromIterable(iterableNombres);

        // Suscribirse al flux y manejar los elementos
        fluxNombres.subscribe(
                nombre -> System.out.println("Nombre: " + nombre),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flujo completado")
        );




    }





}
