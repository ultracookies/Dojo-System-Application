package com.dojoapp.Dojo.System.Application.model;

import lombok.Data;

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
