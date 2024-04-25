package org.example.programacion.avanzada;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BooksRepository {

    Single<Book> findById(Integer id);

    Flowable<Book> getdAll();

    Single<Book> insert(Book book);

    public Completable deleteById(Integer id);

    public Single<Book> update(Book book);



}
