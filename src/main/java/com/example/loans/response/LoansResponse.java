package com.example.loans.response;

import com.example.loans.domain.Loan;

import java.util.List;

public class LoansResponse {

    private List<Loan> items;
    private Paging paging;

    public LoansResponse(List<Loan> items, Paging paging) {
        this.items = items;
        this.paging = paging;
    }

    public List<Loan> getItems() {
        return items;
    }

    public void setItems(List<Loan> items) {
        this.items = items;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "LoansResponse{" +
                "items=" + items +
                ", paging=" + paging +
                '}';
    }
}
