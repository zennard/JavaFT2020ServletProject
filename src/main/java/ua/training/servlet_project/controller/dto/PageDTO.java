package ua.training.servlet_project.controller.dto;

public class PageDTO {
    private Integer limit;
    private Integer prevPage;
    private Integer nextPage;
    private Integer currentPage;
    private Integer totalPages;
    private boolean hasNext;
    private boolean hasPrev;
    private String url;

    public PageDTO() {
    }

    public PageDTO(Integer limit, Integer prevPage, Integer nextPage, Integer currentPage, Integer totalPages, boolean hasNext, boolean hasPrev, String url) {
        this.limit = limit;
        this.prevPage = prevPage;
        this.nextPage = nextPage;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrev = hasPrev;
        this.url = url;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(Integer prevPage) {
        this.prevPage = prevPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean hasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "limit=" + limit +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", hasNext=" + hasNext +
                ", hasPrev=" + hasPrev +
                ", url='" + url + '\'' +
                '}';
    }

    public static PageDTOBuilder builder() {
        return new PageDTOBuilder();
    }

    public static class PageDTOBuilder {
        private Integer limit;
        private Integer prevPage;
        private Integer nextPage;
        private Integer currentPage;
        private Integer totalPages;
        private boolean hasNext;
        private boolean hasPrev;
        private String url;

        PageDTOBuilder() {
        }

        public PageDTOBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public PageDTOBuilder prevPage(Integer prevPage) {
            this.prevPage = prevPage;
            return this;
        }

        public PageDTOBuilder nextPage(Integer nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        public PageDTOBuilder currentPage(Integer currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public PageDTOBuilder totalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PageDTOBuilder hasNext(boolean hasNext) {
            this.hasNext = hasNext;
            return this;
        }

        public PageDTOBuilder hasPrev(boolean hasPrev) {
            this.hasPrev = hasPrev;
            return this;
        }

        public PageDTOBuilder url(String url) {
            this.url = url;
            return this;
        }

        public PageDTO build() {
            return new PageDTO(limit, prevPage, nextPage, currentPage,
                    totalPages, hasNext, hasPrev, url);
        }
    }
}
