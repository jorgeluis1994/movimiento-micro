package com.dev.bank.movimientos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.bank.movimientos.models.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    // Ejemplo de query adicional si lo necesitas
    Cuenta findByNumeroCuenta(String numeroCuenta);
}

