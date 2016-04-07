package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Book {
    public final String title;
    public final String publisher;
    public final Long date;

    public Book(String title, String publisher, Long date) {
        this.title = title;
        this.publisher = publisher;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public Long getDate() {
        return date;
    }
}
