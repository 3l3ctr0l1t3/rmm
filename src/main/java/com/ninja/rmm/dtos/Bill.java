package com.ninja.rmm.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Bill {

    private BigDecimal total;
    private List<BillDetail> details = new ArrayList<>();

    public void addDetail(String detail, BigDecimal cost){
       details.add(new BillDetail(detail,cost));
    }
}
