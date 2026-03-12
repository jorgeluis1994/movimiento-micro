package com.dev.bank.movimientos.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CrearCuentaDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String nombreCliente; // Aquí recibes "Jose Lema" o "Marianela Montalvo"
}