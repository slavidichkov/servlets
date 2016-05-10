package com.clouway.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PageBeanImpl<T> implements PageBean {
  private int pageNumber;
  private int pageSize = 5;
  private List<T> items = null;

  public void initialItems(List list) {
    items = new ArrayList<T>();
    items.addAll(list);
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public List<T> next() {
    if (!hasNext()) {
      return lastPage();
    }
    pageNumber++;
    return goToPage(pageNumber);
  }

  public List<T> previous() {
    if (!hasPrevious()) {
      return firstPage();
    }
    pageNumber--;
    return goToPage(pageNumber);
  }

  private boolean hasNext() {
    return pageNumber < items.size() / pageSize - 1;
  }

  private boolean hasPrevious() {
    return (pageNumber > 0);
  }

  public List<T> firstPage() {
    pageNumber = 0;
    return goToPage(pageNumber);
  }

  public List<T> lastPage() {
    pageNumber = items.size() / pageSize;
    if (pageSize > items.size()) {
      return firstPage();
    }
    return goToPage(pageNumber-1);
  }

  private List<T> goToPage(int pageNum) {
    pageNumber = pageNum;
    if (pageNumber * pageSize <= items.size()) {
      return items.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize);
    }
    return items.subList(pageNumber * pageSize, items.size());
  }

  public int getCurrentPageNumber() {
    return pageNumber + 1;
  }
}
