package ru.itlc.testproject.serverside.dataobjects;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book implements Serializable {
    private long bookId;
    private String bookAuthor;
    private String bookTitle;
    private String bookPublisher;
    private String bookPublisherAddress;
    private String bookPublishingDate;
}
