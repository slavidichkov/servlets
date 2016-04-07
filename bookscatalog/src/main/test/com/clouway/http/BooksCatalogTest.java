package com.clouway.http;

import com.clouway.core.*;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class BooksCatalogTest {
    private BooksCatalog booksCatalog;
    private FakeRequest fakeRequest;
    private FakeResponse fakeResponse;

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    BooksRepository booksRepository;

    @Mock
    PageBean pageBean;

    @Before
    public void setUp() {
        DependencyManager.addDependencies(BooksRepositoryFactory.class, new BooksRepositoryFactory() {
            public BooksRepository getRepository() {
                return booksRepository;
            }
        });
        DependencyManager.addDependencies(PageBean.class, pageBean);
        fakeRequest = new FakeRequest();
        fakeResponse = new FakeResponse();
        booksCatalog = new BooksCatalog();
    }

    @Test
    public void firstPage() throws ServletException, IOException {
        final List<Book> books = new ArrayList<Book>() {{
            add(new Book("Java","Anubis",1234567890L));
            add(new Book("C#", "Anubis", 9876543210L));
            add(new Book("C++", "Helikon", 9876543210L));
            add(new Book("PHP", "Anubis", 9876543210L));
        }};

        final List<Book> booksPage = books.subList(0, 2);
        fakeRequest.setParameter("pagePointer", "firstPage");

        ByteArrayOutputStream out=new ByteArrayOutputStream();

        fakeResponse.setOutputStream(out);

        context.checking(new Expectations() {{
            oneOf(booksRepository).get();
            will(returnValue(books));
            oneOf(pageBean).setList(books);
            oneOf(pageBean).firstPage();
            will(returnValue(booksPage));
            oneOf(pageBean).getCurrentPageNumber();
        }});

        booksCatalog.doPost(fakeRequest,fakeResponse);
        String expected=out.toString();
        assertThat(expected,containsString("Java"));
        assertThat(expected,containsString("Anubis"));
        assertThat(expected,containsString("C#"));
        assertThat(expected,containsString("Anubis"));
    }


    @Test
    public void lastPage() throws ServletException, IOException {
        final List<Book> books = new ArrayList<Book>() {{
            add(new Book("Java","Anubis",1234567890L));
            add(new Book("C#", "Anubis", 9876543210L));
            add(new Book("C++", "Helikon", 9876543210L));
            add(new Book("PHP", "Anubis", 9876543210L));
        }};

        final List<Book> booksPage = books.subList(2, books.size());

        fakeRequest.setParameter("pagePointer", "lastPage");

        ByteArrayOutputStream out=new ByteArrayOutputStream();

        fakeResponse.setOutputStream(out);

        context.checking(new Expectations() {{
            oneOf(booksRepository).get();
            will(returnValue(books));
            oneOf(pageBean).setList(books);
            oneOf(pageBean).lastPage();
            will(returnValue(booksPage));
            oneOf(pageBean).getCurrentPageNumber();
        }});

        booksCatalog.doPost(fakeRequest,fakeResponse);
        String expected=out.toString();
        assertThat(expected,containsString("C++"));
        assertThat(expected,containsString("Helikon"));
        assertThat(expected,containsString("PHP"));
        assertThat(expected,containsString("Anubis"));
    }
    @Test
    public void nextPage() throws ServletException, IOException {
        final List<Book> books = new ArrayList<Book>() {{
            add(new Book("Java","Anubis",1234567890L));
            add(new Book("C#", "Anubis", 9876543210L));
            add(new Book("C++", "Helikon", 9876543210L));
            add(new Book("PHP", "Anubis", 9876543210L));
        }};

        final List<Book> booksPage = books.subList(2, books.size());

        fakeRequest.setParameter("pagePointer", "nextPage");

        ByteArrayOutputStream out=new ByteArrayOutputStream();

        fakeResponse.setOutputStream(out);

        context.checking(new Expectations() {{
            oneOf(booksRepository).get();
            will(returnValue(books));
            oneOf(pageBean).setList(books);
            oneOf(pageBean).next();
            will(returnValue(booksPage));
            oneOf(pageBean).getCurrentPageNumber();
        }});

        booksCatalog.doPost(fakeRequest,fakeResponse);
        String expected=out.toString();
        assertThat(expected,containsString("C++"));
        assertThat(expected,containsString("Helikon"));
        assertThat(expected,containsString("PHP"));
        assertThat(expected,containsString("Anubis"));
    }

    @Test
    public void previousPage() throws ServletException, IOException {
        final List<Book> books = new ArrayList<Book>() {{
            add(new Book("Java","Anubis",1234567890L));
            add(new Book("C#", "Anubis", 9876543210L));
            add(new Book("C++", "Helikon", 9876543210L));
            add(new Book("PHP", "Anubis", 9876543210L));
        }};

        final List<Book> booksPage = books.subList(2, books.size());

        fakeRequest.setParameter("pagePointer", "previousPage");

        ByteArrayOutputStream out=new ByteArrayOutputStream();

        fakeResponse.setOutputStream(out);

        context.checking(new Expectations() {{
            oneOf(booksRepository).get();
            will(returnValue(books));
            oneOf(pageBean).setList(books);
            oneOf(pageBean).previous();
            will(returnValue(booksPage));
            oneOf(pageBean).getCurrentPageNumber();
        }});

        booksCatalog.doPost(fakeRequest,fakeResponse);
        String expected=out.toString();
        assertThat(expected,containsString("C++"));
        assertThat(expected,containsString("Helikon"));
        assertThat(expected,containsString("PHP"));
        assertThat(expected,containsString("Anubis"));
    }


}