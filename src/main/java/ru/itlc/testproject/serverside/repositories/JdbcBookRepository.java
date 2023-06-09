package ru.itlc.testproject.serverside.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.itlc.testproject.serverside.dataobjects.Book;
import ru.itlc.testproject.serverside.repositories.interfaces.BookRepository;
import ru.itlc.testproject.serverside.responses.BookPaginationResponse;
import ru.itlc.testproject.serverside.responses.BooleanResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String orderByFormat = " ORDER BY %s %s";
    private final String limitOffsetFormat = " LIMIT %d OFFSET %d";
    @Autowired
    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Book> save(Book book) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            "INSERT INTO Book " +
                    "(book_author, book_title, book_publisher," +
                    " book_publisher_address, book_publishing_date) VALUES (?, ?, ?, ?, ?)",
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
            Arrays.asList(
                book.getBookAuthor(),
                book.getBookTitle(),
                book.getBookPublisher(),
                book.getBookPublisherAddress(),
                book.getBookPublishingDate()
            )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        long index = (long)keyHolder.getKeyList().get(0).get("book_id");
        return this.findById(index);
    }

    @Override
    public BookPaginationResponse findAll(int page, int pageSize, String sortingColumn, String sortingDirection) {
        String query = "SELECT * FROM Book";
        if (sortingColumn != null && !sortingColumn.isEmpty()) {
            query += String.format(orderByFormat, sortingColumn, sortingDirection);
        }
        query += String.format(limitOffsetFormat, pageSize,
                pageSize * (page - 1));

        Integer totalBooksCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) AS Count FROM Book",
                Integer.class);
        List<Book> books = jdbcTemplate.query(
                query,
                this::mapRowToBook);

        BookPaginationResponse result = new BookPaginationResponse(books,
                pageSize, page, (int)Math.ceil((double)(totalBooksCount)/pageSize), totalBooksCount);
        return result;
    }

    @Override
    public Optional<Book> findById(long id) {
        List<Book> results = jdbcTemplate.query(
            "SELECT * FROM Book WHERE book_id = ?",
            this::mapRowToBook,
            id);
        return results.size() == 0
                ? Optional.empty()
                : Optional.of(results.get(0));
    }

    @Override
    public BooleanResponse updateById(long id, Book book) {
        int recordsUpdatedNumber = jdbcTemplate.update(
            "UPDATE Book SET book_author = ?, " +
                "book_title = ?, " +
                "book_publisher = ?, " +
                "book_publisher_address = ?, " +
                "book_publishing_date = ? WHERE book_id = ?",
            book.getBookAuthor(),
            book.getBookTitle(),
            book.getBookPublisher(),
            book.getBookPublisherAddress(),
            book.getBookPublishingDate(),
            id
        );

        return new BooleanResponse(recordsUpdatedNumber != 0);
    }

    @Override
    public BooleanResponse deleteById(long id) {
        String sql = "DELETE FROM Book WHERE book_id = ?";
        Object[] args = new Object[] {id};

        return new BooleanResponse(jdbcTemplate.update(sql, args) != 0);
    }

    private Book mapRowToBook(ResultSet row, int rowNum) throws SQLException {
        return new Book(
            row.getLong("book_id"),
            row.getString("book_author"),
            row.getString("book_title"),
            row.getString("book_publisher"),
            row.getString("book_publisher_address"),
            row.getString("book_publishing_date")
        );
    }
}
