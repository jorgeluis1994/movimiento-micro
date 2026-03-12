package com.dev.bank.movimientos.exceptions;

public class SaldoNoDisponibleException extends RuntimeException {
    public SaldoNoDisponibleException(String message) {
        super(message);
    }
    
}
