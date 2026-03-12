package com.dev.bank.movimientos.dto;

import lombok.Data;

@Data
public class RegistrarMovimientoDTO {
    private String numeroCuenta; 
    private String tipoMovimiento; 
    private Double valor; 
}