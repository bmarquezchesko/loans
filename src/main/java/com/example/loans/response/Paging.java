package com.example.loans.response;

public class Paging {

    private Long page;
    private Long size;
    private Long total;

    public Paging(Long page, Long size, Long total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
