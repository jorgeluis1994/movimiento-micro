package com.dev.bank.movimientos.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.bank.movimientos.models.Cuenta;
import com.dev.bank.movimientos.models.Movimiento;
import com.dev.bank.movimientos.repository.CuentaRepository;
import com.dev.bank.movimientos.repository.MovimientoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public Movimiento obtenerMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Transactional
    public Movimiento registrarMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Double nuevoSaldo = cuenta.getSaldo() + movimiento.getValor();

        if (nuevoSaldo < 0) {
            // throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);

        return movimientoRepository.save(movimiento);
    }

    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}