package com.dev.bank.movimientos.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.bank.movimientos.dto.RegistrarMovimientoDTO; // Tu nuevo DTO
import com.dev.bank.movimientos.dto.MovimientoDTO; // El DTO para RabbitMQ
import com.dev.bank.movimientos.exceptions.SaldoNoDisponibleException;
import com.dev.bank.movimientos.models.Cuenta;
import com.dev.bank.movimientos.models.Movimiento;
import com.dev.bank.movimientos.publisher.MovimientoPublisher;
import com.dev.bank.movimientos.repository.CuentaRepository;
import com.dev.bank.movimientos.repository.MovimientoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoPublisher movimientoPublisher;

    public MovimientoService(MovimientoRepository movimientoRepository,
            CuentaRepository cuentaRepository,
            MovimientoPublisher movimientoPublisher) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoPublisher = movimientoPublisher;
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public Movimiento obtenerMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Transactional
    public Movimiento registrarMovimiento(RegistrarMovimientoDTO dto) {
        // 1. Buscar la cuenta (QUITAMOS orElseThrow porque tu repo devuelve Cuenta, no
        // Optional)
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta());

        // 2. Validación manual de existencia
        if (cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada: " + dto.getNumeroCuenta());
        }

        Double valorMovimiento = dto.getValor();

        // 3. Lógica de signo: Si es Retiro, el valor debe restar
        if (dto.getTipoMovimiento().equalsIgnoreCase("Retiro")) {
            valorMovimiento = -Math.abs(valorMovimiento);
        }

        // 4. Calcular nuevo saldo
        Double nuevoSaldo = cuenta.getSaldo() + valorMovimiento;

        // 5. Validar saldo disponible
        if (nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        // 6. Actualizar saldo de la cuenta
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // 7. Crear y guardar el registro del Movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(valorMovimiento);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(LocalDate.now());
        movimiento.setCuenta(cuenta);

        Movimiento nuevo = movimientoRepository.save(movimiento);

        // 8. Publicar en RabbitMQ
        MovimientoDTO eventoDto = new MovimientoDTO(
                nuevo.getId(),
                nuevo.getTipoMovimiento(),
                nuevo.getValor());

        movimientoPublisher.publicarMovimiento(eventoDto);

        return nuevo;
    }

    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}
