package com.dev.bank.movimientos.dto;

import java.io.Serializable;

public class MovimientoDTO implements Serializable {
    private Long id;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    // Constructor vacío (necesario para deserialización)
    public MovimientoDTO() {
    }

    // Constructor con parámetros
    public MovimientoDTO(Long id, String tipoMovimiento, Double valor, Double saldo) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
    }

    public MovimientoDTO(Long id, String tipoMovimiento, Double valor) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}