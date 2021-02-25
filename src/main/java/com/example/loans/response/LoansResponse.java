package com.example.loans.response;

import com.example.loans.domain.Loan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
public class LoansResponse {

    private List<Loan> items;
    private Paging paging;

    public LoansResponse(List<Loan> items, Paging paging) {
        this.items = items;
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
