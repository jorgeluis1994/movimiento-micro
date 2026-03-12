package com.dev.bank.movimientos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.bank.movimientos.models.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    // Buscar movimientos por cuenta
    List<Movimiento> findByCuentaId(Long cuentaId);

    // Buscar movimientos por rango de fechas
    List<Movimiento> findByFechaBetween(LocalDate inicio, LocalDate fin);

    
}
