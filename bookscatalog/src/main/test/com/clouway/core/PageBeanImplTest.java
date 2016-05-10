package com.clouway.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PageBeanImplTest {
  private PageBeanImpl<Book> pageBean;
  final List<Book> books = new ArrayList<Book>() {{
    add(new Book("Java","Anubis",1234567890L));
    add(new Book("C#", "Anubis", 9876543210L));
    add(new Book("C++", "Helikon", 9876543210L));
    add(new Book("PHP", "Anubis", 9876543210L));
  }};

  @Before
  public void setUp(){
    pageBean=new PageBeanImpl<Book>();
  }

  @Test
  public void goToFirstPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("Java","Anubis",1234567890L));
      add(new Book("C#", "Anubis", 9876543210L));
    }};

    List<Book> actual=pageBean.firstPage();
    assertThat(actual,is(equalTo(expected)));
  }

  @Test
  public void goToLastPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("C++", "Helikon", 9876543210L));
      add(new Book("PHP", "Anubis", 9876543210L));
    }};

    List<Book> actual=pageBean.lastPage();

    assertThat(actual,is(equalTo(expected)));
  }

  @Test
  public void goToNextPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("C++", "Helikon", 9876543210L));
      add(new Book("PHP", "Anubis", 9876543210L));
    }};

    pageBean.firstPage();

    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(1)));

    List<Book> actualAfterNext=pageBean.next();

    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(2)));
    assertThat(actualAfterNext,is(equalTo(expected)));
  }

  @Test
  public void invokeNextPageWhenOnLastPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("C++", "Helikon", 9876543210L));
      add(new Book("PHP", "Anubis", 9876543210L));
    }};

    pageBean.lastPage();

    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(2)));

    List<Book> actualAfterNext=pageBean.next();

    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(2)));
    assertThat(actualAfterNext,is(equalTo(expected)));
  }

  @Test
  public void goToPreviousPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("Java","Anubis",1234567890L));
      add(new Book("C#", "Anubis", 9876543210L));
    }};

    pageBean.lastPage();
    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(2)));

    List<Book> actualAfterPrevious=pageBean.previous();
    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(1)));
    assertThat(actualAfterPrevious,is(equalTo(expected)));
  }

  @Test
  public void invokePreviousPageWhenOnFirstPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("Java","Anubis",1234567890L));
      add(new Book("C#", "Anubis", 9876543210L));
    }};

    pageBean.firstPage();
    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(1)));

    List<Book> actualAfterPrevious=pageBean.previous();
    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(1)));
    assertThat(actualAfterPrevious,is(equalTo(expected)));
  }

  @Test
  public void goToPage() {
    pageBean.initialItems(books);
    pageBean.setPageSize(2);

    List<Book> expected = new ArrayList<Book>() {{
      add(new Book("C++", "Helikon", 9876543210L));
      add(new Book("PHP", "Anubis", 9876543210L));
    }};

    List<Book> actualAfterPrevious=pageBean.goToPage(1);
    assertThat(pageBean.getCurrentPageNumber(),is(equalTo(2)));
    assertThat(actualAfterPrevious,is(equalTo(expected)));
  }
}