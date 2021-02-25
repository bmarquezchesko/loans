package com.example.loans.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Paging {

    private Integer page;
    private Integer size;
    private Long total;

    public Paging(Integer page, Integer size, Long total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "page=" + page +
                ", size=" + size +
                ", total=" + total +
                '}';
    }
}
