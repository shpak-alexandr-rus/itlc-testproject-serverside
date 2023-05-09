package ru.itlc.testproject.serverside.repositories.interfaces;

import ru.itlc.testproject.serverside.dataobjects.Book;
import ru.itlc.testproject.serverside.responses.BookPaginationResponse;
import ru.itlc.testproject.serverside.responses.BooleanResponse;

import java.util.Optional;

public interface BookRepository {

  Optional<Book> save(Book book);

  BookPaginationResponse findAll(int page, int pageSize, String sortingColumn, String sortingDirection);
  
  Optional<Book> findById(long id);

  BooleanResponse updateById(long id, Book book);

  BooleanResponse deleteById(long id);
}
