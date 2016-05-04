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
    private List<T> items = null;

    public void initialItems(List list) {
        if (items ==null){
            items = new ArrayList<T>();
            items.addAll(list);
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> next() {
        if (firstIndex == 0 && lastIndex == 0 && pageSize < items.size()) {
            lastIndex += pageSize;
        } else if (hasNext() && lastIndex + pageSize < items.size()) {
            firstIndex += pageSize;
            lastIndex += pageSize;
        } else {
            return lastPage();
        }
        return items.subList(firstIndex, lastIndex);
    }

    public List<T> previous() {
        if (hasPrevious() && lastIndex > pageSize) {
            firstIndex = firstIndex - pageSize;
            lastIndex = firstIndex + pageSize;
        } else {
            return firstPage();
        }
        return items.subList(firstIndex, lastIndex);
    }

    public boolean hasNext() {
        return (lastIndex != items.size());
    }

    public boolean hasPrevious() {
        return (firstIndex != 0);
    }

    public List<T> firstPage() {
        firstIndex = 0;
        lastIndex = firstIndex + pageSize;
        return items.subList(firstIndex, lastIndex);
    }

    public List<T> lastPage() {
        lastIndex = items.size();
        int temp = (items.size()) % pageSize;
        firstIndex = lastIndex - temp;
        return items.subList(firstIndex, lastIndex);
    }

    public int getCurrentPageNumber() {
        return ((firstIndex + 1) / pageSize) + 1;
    }
}
