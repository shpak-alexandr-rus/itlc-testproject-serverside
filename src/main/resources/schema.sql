create table if not exists Book (
  book_id identity NOT NULL,
  book_author varchar(60) NOT NULL,
  book_title varchar(250) NOT NULL,
  book_publisher varchar(25) DEFAULT NULL,
  book_publisher_address varchar(100) DEFAULT NULL,
  book_publishing_date date DEFAULT NULL
);
