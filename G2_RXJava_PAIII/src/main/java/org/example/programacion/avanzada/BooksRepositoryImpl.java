package org.example.programacion.avanzada;

import io.r2dbc.spi.ConnectionFactory;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import java.math.BigDecimal;

public class BooksRepositoryImpl implements BooksRepository {

    private final ConnectionFactory connectionFactory;

    public BooksRepositoryImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Single<Book> findById(Integer id) {
        return Flowable.fromPublisher(connectionFactory.create())
                .flatMap(connection -> Flowable.fromPublisher(connection
                        .createStatement("SELECT * FROM books WHERE id = $1")
                        .bind("$1", id)
                        .execute()))
                .flatMap(result -> Flowable.fromPublisher(result.map((row, metadata) ->
                        new Book(
                                row.get("id", Integer.class),
                                row.get("author", String.class),
                                row.get("isbn", String.class),
                                row.get("title", String.class),
                                row.get("price", BigDecimal.class)
                        )
                )))
                .firstOrError();
    }

    @Override
    public Flowable<Book> getdAll() {
        return Flowable.fromPublisher(connectionFactory.create())
                .flatMap(connection -> Flowable.fromPublisher(connection
                        .createStatement("SELECT * FROM books")
                        .execute()))
                .flatMap(result -> Flowable.fromPublisher(result.map((row, metadata) ->
                        new Book(
                                row.get("id", Integer.class),
                                row.get("author", String.class),
                                row.get("isbn", String.class),
                                row.get("title", String.class),
                                row.get("price", BigDecimal.class)
                        )
                )));
    }

    @Override
    public Single<Book> insert(Book book) {
        return Flowable.fromPublisher(connectionFactory.create())
                .doOnSubscribe(subscription -> System.out.println("Iniciando operación de inserción"))
                .flatMap(connection -> Flowable.fromPublisher(connection
                        .createStatement("INSERT INTO books (author, isbn, title, price) VALUES ($1, $2, $3, $4) RETURNING *")
                        .bind("$1", book.getAuthor())
                        .bind("$2", book.getIsbn())
                        .bind("$3", book.getTitle())
                        .bind("$4", book.getPrice())
                        .execute()))
                .doOnNext(result -> System.out.println("Resultados de la inserción: " + result))
                .flatMap(result -> Flowable.fromPublisher(result.map((row, metadata) ->
                        new Book(
                                row.get("id", Integer.class),
                                row.get("author", String.class),
                                row.get("isbn", String.class),
                                row.get("title", String.class),
                                row.get("price", BigDecimal.class)
                        )
                )))
                .firstOrError();
    }

    @Override
    public Completable deleteById(Integer id) {
        return Completable.create(emitter ->
                Flowable.fromPublisher(connectionFactory.create())
                        .flatMap(connection -> Flowable.fromPublisher(connection
                                .createStatement("DELETE FROM books WHERE id = $1")
                                .bind("$1", id)
                                .execute()))
                        .flatMap(result -> result.getRowsUpdated())
                        .flatMapSingle(rowsUpdated -> {
                            if (rowsUpdated == 0) {
                                return Single.error(new IllegalArgumentException("No se encontró un libro con el ID especificado"));
                            } else {
                                return Single.just(rowsUpdated);
                            }
                        })
                        .ignoreElements()
                        .blockingSubscribe(
                                () -> emitter.onComplete(),
                                error -> emitter.onError(error)
                        )
        );
    }

    @Override
    public Single<Book> update(Book book) {
        return Flowable.fromPublisher(connectionFactory.create())
                .flatMap(connection -> Flowable.fromPublisher(connection
                        .createStatement("UPDATE books SET author = $2, isbn = $3, title = $4, price = $5 WHERE id = $1 RETURNING *")
                        .bind("$1", book.getId())
                        .bind("$2", book.getAuthor())
                        .bind("$3", book.getIsbn())
                        .bind("$4", book.getTitle())
                        .bind("$5", book.getPrice())
                        .execute()))
                .flatMap(result -> Flowable.fromPublisher(result.map((row, metadata) ->
                        new Book(
                                row.get("id", Integer.class),
                                row.get("author", String.class),
                                row.get("isbn", String.class),
                                row.get("title", String.class),
                                row.get("price", BigDecimal.class)
                        )
                )))
                .firstOrError();
    }


}