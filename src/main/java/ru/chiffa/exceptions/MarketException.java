package ru.chiffa.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class MarketException {
    private int status;
    private String message;
    private Date timestamp;

    public MarketException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
