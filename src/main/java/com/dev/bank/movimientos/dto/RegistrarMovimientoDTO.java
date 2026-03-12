package com.dev.bank.movimientos.dto;

import lombok.Data;

@Data
public class RegistrarMovimientoDTO {
    private String numeroCuenta; // "478758"
    private String tipoMovimiento; // "Retiro" o "Deposito"
    private Double valor; // 575.0
}