package ru.itlc.testproject.serverside.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itlc.testproject.serverside.dataobjects.Book;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class BookPaginationResponse {
    private final List<Book> books;
    private final int bookCount;
    private final int pageNumber;
    private final int totalPagesCount;
    private final int totalBooksCount;
}
