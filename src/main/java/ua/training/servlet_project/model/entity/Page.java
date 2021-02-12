package ua.training.servlet_project.model.entity;

import java.util.List;

public class Page<T> {
    private List<T> content;
    private Pageable pageable;
    private int totalPages;

    public Page() {
    }

    public Page(List<T> content, Pageable pageable, int totalPages) {
        this.content = content;
        this.pageable = pageable;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean hasPrevious() {
        return pageable.getPageNumber() > 0;
    }

    public boolean hasNext() {
        return pageable.getPageNumber() < totalPages - 1;
    }

    @Override
    public String toString() {
        return "Page{" +
                "content=" + content +
                ", pageable=" + pageable +
                ", totalPages=" + totalPages +
                '}';
    }
}
