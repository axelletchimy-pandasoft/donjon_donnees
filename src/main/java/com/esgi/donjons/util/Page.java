package com.esgi.donjons.util;

import java.util.Collections;
import java.util.List;

public class Page<T> {

    private final List<T> elements;
    private final int page;
    private final int size;
    private final long totalElements;

    public Page(List<T> elements, int page, int size, long totalElements) {
        this.elements = elements != null ? Collections.unmodifiableList(elements) : List.of();
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        if (size <= 0) return 0;
        return (int) Math.ceil((double) totalElements / size);
    }

    public boolean hasNext() {
        return page + 1 < getTotalPages();
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + getTotalPages() +
                ", elements=" + elements.size() +
                '}';
    }
}
