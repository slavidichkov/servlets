package com.clouway.core;

import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface PageBean<T> {
    void setList(List<T> list);
    void setPageSize(int pageSize);
    List<T> next();
    List<T> previous();
    List<T> firstPage();
    List<T> lastPage();
    int getCurrentPageNumber();
}
