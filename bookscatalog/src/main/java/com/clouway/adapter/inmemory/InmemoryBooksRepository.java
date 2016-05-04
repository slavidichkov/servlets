package com.clouway.adapter.inmemory;

import com.clouway.core.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemoryBooksRepository implements com.clouway.core.BooksRepository{
    private List<Book> books=new ArrayList<Book>(){{
       add(new Book("Hityr petyr", "Helikon", 1213243535L));
        add(new Book("Malkiq princ", "Prosveta", 1213243535L));
        add(new Book("Snejanka", "Kolibri", 1213243535L));
        add(new Book("Java", "Anubis", 1213243535L));
        add(new Book("C#", "Helikon", 1213243535L));
        add(new Book("C++", "Milenium", 1213243535L));
        add(new Book("HTML", "Helikon", 1213243535L));
        add(new Book("Javascript", "Helikon", 1213243535L));
        add(new Book("database", "Helikon", 1213243535L));
        add(new Book("PHP", "Arhimed", 1213243535L));
        add(new Book("AJAX", "Anubis", 1213243535L));
        add(new Book("CSS", "Helikon", 1213243535L));
        add(new Book("Go", "Miranda", 1213243535L));
        add(new Book("Goos", "Helikon", 1213243535L));
        add(new Book("Java clean code", "Bullvest", 1213243535L));
        add(new Book("Design paterns", "Helikon", 1213243535L));
    }};
    public List<Book> getAllBooks() {
        return books;
    }
}
