package com.clouway.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PageBeanImpl<T> implements PageBean{
    private int firstIndex;
    private int lastIndex;
    private int pageSize = 10;
    private List<T> rows = new ArrayList<T>();

    public PageBeanImpl(List<T> list) {
        this.rows=list;
    }

    public PageBeanImpl(List<T> list, int pageSize) {
        this.rows=list;
        this.pageSize = pageSize;
    }

    public List<T> next() {
        if (firstIndex == 0 && lastIndex == 0 && pageSize < rows.size()) {
            lastIndex += pageSize;
        } else if (hasNext() && lastIndex + pageSize < rows.size()) {
            firstIndex += pageSize;
            lastIndex += pageSize;
        } else {
            return lastPage();
        }
        return rows.subList(firstIndex, lastIndex);
    }

    public List<T> previous() {
        if (hasPrevious() && lastIndex > pageSize) {
            firstIndex = firstIndex - pageSize;
            lastIndex = firstIndex + pageSize;
        } else {
            return firstPage();
        }
        return rows.subList(firstIndex, lastIndex);
    }

    public boolean hasNext() {
        return (lastIndex != rows.size());
    }

    public boolean hasPrevious() {
        return (firstIndex != 0);
    }

    public List<T> firstPage() {
        firstIndex = 0;
        lastIndex = firstIndex + pageSize;
        return rows.subList(firstIndex, lastIndex);
    }

    public List<T> lastPage() {
        lastIndex = rows.size();
        int temp = (rows.size()) % pageSize;
        firstIndex = lastIndex - temp;
        return rows.subList(firstIndex, lastIndex);
    }

    public int getCurrentPageNumber() {
        return ((firstIndex + 1) / pageSize) + 1;
    }
}
