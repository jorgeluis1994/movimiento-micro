package com.dev.bank.movimientos.services;

import org.springframework.stereotype.Service;

import com.dev.bank.movimientos.models.Cuenta;
import com.dev.bank.movimientos.repository.CuentaRepository;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerCuenta(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta actualizarCuenta(Long id, Cuenta cuenta) {
        Cuenta existente = obtenerCuenta(id);
        existente.setNumeroCuenta(cuenta.getNumeroCuenta());
        existente.setTipoCuenta(cuenta.getTipoCuenta());
        existente.setSaldo(cuenta.getSaldo());
        existente.setEstado(cuenta.getEstado());
        return cuentaRepository.save(existente);
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
}