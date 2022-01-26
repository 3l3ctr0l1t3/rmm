package com.ninja.rmm.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillDetail {

    String detail;
    BigDecimal total;

    public BillDetail(String detail, BigDecimal total) {
        this.detail = detail;
        this.total = total;
    }

}
