package com.dojoapp.Dojo.System.Application.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Payment {

    private String amount;
    private String date;

    public Payment() {}

    public Payment(String amount, String date) {
        this.amount = amount;
        this.date = date;
    }
}
