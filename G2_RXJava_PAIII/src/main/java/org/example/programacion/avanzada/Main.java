package org.example.programacion.avanzada;

import io.r2dbc.spi.ConnectionFactory;
import io.reactivex.rxjava3.core.Single;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //Grupo 2: RXJava
        //Integrantes: Andrango Alex, Coloma Dillan, Coyago Henry


        ConnectionFactory connectionFactory = DataBaseConfig.createConnection();

        BooksRepository bookRepository = new BooksRepositoryImpl(connectionFactory);
        Book newBook = new Book(null, "HP Lovecraft", "ISBN123", "La llamada de Cthulhu", new BigDecimal(19.0));

        CountDownLatch latch = new CountDownLatch(4);

        //OPERACIONES CON EL CRUD

        // CREATE
        bookRepository.insert(newBook)
                .subscribe(
                        createdBook -> {
                            System.out.println("Libro creado: " + createdBook);
                            latch.countDown();
                        },
                        error -> {
                            System.err.println("Error al crear libro: " + error.getMessage());
                            latch.countDown();
                        }
                );

        // READ
        var book =bookRepository.findById(1)
                .subscribe(
                        retrievedBook -> {
                            System.out.println("Libro recuperado: " + retrievedBook);
                            latch.countDown();
                        },
                        error -> {
                            System.err.println("Error al recuperar libro: " + error.getMessage());
                            latch.countDown();
                        }
                );


        // UPDATE

        Integer bookIdToRetrieve = 1;
        Single<Book> retrievedBookSingle = bookRepository.findById(bookIdToRetrieve);
        Book retrievedBook = retrievedBookSingle .blockingGet();

        retrievedBook.setAuthor("Henry");
        bookRepository.update(retrievedBook)
                .subscribe(
                        updatedBook -> {
                            System.out.println("Libro actualizado: " + updatedBook);
                            latch.countDown();
                        },
                        error -> {
                            System.err.println("Error al actualizar libro: " + error.getMessage());
                            latch.countDown();
                        }
                );

        // DELETE
        bookRepository.deleteById(13)
                .subscribe(
                        () -> {
                            System.out.println("Libro eliminado con éxito.");
                        },
                        error -> {
                            System.err.println("Error al eliminar libro: " + error.getMessage());
                        }
                );


        latch.await();
    }
}
